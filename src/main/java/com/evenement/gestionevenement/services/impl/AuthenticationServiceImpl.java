package com.evenement.gestionevenement.services.impl;

import com.evenement.gestionevenement.exception.JwtError;
import com.evenement.gestionevenement.services.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Map<String , String> authenticate(
            String granType,boolean withRefreshToken,
            String username,String password , String refreshToken) throws JwtError {
        Authentication authentication = null;
        String scope = null;
        String subject = null;
        if (granType.equals("password")){
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
            subject = authentication.getName();
            scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        } else if (granType.equals("refreshToken")) {
            if (refreshToken == null){
                throw new JwtError("refresh Token is required");
            }
            Jwt decode = null;
            try {
                decode = jwtDecoder.decode(refreshToken);
            } catch (JwtException e) {
                throw new JwtError("refresh Token is required");
            }
            subject = decode.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
            scope = authorities.stream().map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(" "));
        }
        Map<String , String> idToken = new HashMap<>();
        Instant instant = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .subject(subject)
                .issuedAt(instant)
                .expiresAt(instant.plus(Duration.ofMinutes(withRefreshToken?1:3)))
                .issuer("gestions_evenements")
                .claim("scope",scope)
                .build();
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("accessToken",jwtAccessToken);
        if (withRefreshToken){
            JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(Duration.ofMinutes(30)))
                    .issuer("gestions_evenements")
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
            idToken.put("refreshToken",jwtRefreshToken);
        }
        return idToken;
    }
}
