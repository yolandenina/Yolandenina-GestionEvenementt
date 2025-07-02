package com.evenement.gestionevenement.services.impl;

import com.evenement.gestionevenement.exception.JwtError;
import com.evenement.gestionevenement.services.OtpService;
import com.evenement.gestionevenement.services.TokenService;
import com.evenement.gestionevenement.services.UserAppDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service @Slf4j
public class TokenServiceImpl implements TokenService {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final UserAppDetailsService userAppDetailsService;
    private final OtpService otpService;
    private final AuthenticationManager authenticationManager;

    public TokenServiceImpl(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, UserAppDetailsService userAppDetailsService, OtpService otpService, AuthenticationManager authenticationManager) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.userAppDetailsService = userAppDetailsService;
        this.otpService = otpService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String generateToken(Authentication authentication){
        Instant instant = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));;
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("gestion_evenements")
                .subject(authentication.getName())
                .issuedAt(instant)
                .expiresAt(instant.plus(Duration.ofDays(90)))
                .audience(List.of("Web-App","Mobile-App","StandAlone-App"))
                .claim("scope",scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    public Map<String , String> authenticate(
            String grantType, boolean withRefreshToken,
            String username, String password, String refreshToken, String otpCode) throws JwtError {
        Authentication authentication = null;
        String scope = null;
        String subject = null;
        if (grantType.equals("password")){
            log.info("Trying to authenticate user: {}", username);
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username,password)
            );
            if (otpCode != null && !otpCode.isEmpty()) { //verification de otp fourni
                boolean isOtpValid = otpService.verifyOtp(username, otpCode);
                if (!isOtpValid) {
                    throw new JwtError("Invalid OTP code");
                }
            }else {
                otpService.generateAndSendOtp(username);
                log.error("Authentication failed for user: {}", username);
                throw new JwtError("OTP required. Please check your email.");
            }
            subject = authentication.getName();
            scope = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        } else if (grantType.equals("refreshToken")) {
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
            UserDetails userDetails = userAppDetailsService.loadUserByUsername(subject);
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
                .issuer("start-spring-security")
                .claim("scope",scope)
                .build();
        String jwtAccessToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
        idToken.put("accessToken",jwtAccessToken);
        if (withRefreshToken){
            JwtClaimsSet jwtClaimsSetRefresh = JwtClaimsSet.builder()
                    .subject(subject)
                    .issuedAt(instant)
                    .expiresAt(instant.plus(Duration.ofMinutes(30)))
                    .issuer("start-security")
                    .audience(List.of("Web-App","Mobile-App","StandAlone-App"))
                    .build();
            String jwtRefreshToken = jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSetRefresh)).getTokenValue();
            idToken.put("refreshToken",jwtRefreshToken);
        }
        return idToken;
    }
}
