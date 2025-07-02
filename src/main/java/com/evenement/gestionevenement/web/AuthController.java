package com.evenement.gestionevenement.web;

import com.evenement.gestionevenement.dto.LoginRequest;
import com.evenement.gestionevenement.exception.JwtError;
import com.evenement.gestionevenement.services.AuthenticationService;
import com.evenement.gestionevenement.services.TokenService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController @Slf4j
@RequestMapping(path = "/api/v1/auth")
public class AuthController {
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService,  AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/token")
    public ResponseEntity<Map<String,String>> jwtToken(
            @RequestParam String grantType,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String otpCode,
            @RequestParam(required = false) boolean withRefreshToken,
            @RequestParam(required = false) String refreshToken) throws JwtError {
        Map<String, String> authenticate = tokenService.authenticate(grantType, withRefreshToken,
                username, password,refreshToken,otpCode);
        return ResponseEntity.ok().body(authenticate);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(Map.of("accessToken", token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    /*@PostMapping("/changed")
    public ResponseEntity<?> changedPassword(@RequestBody ChangePasswordRequest passwordRequest){
        try {
            String authenticatedEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            userService.changedPassword(authenticatedEmail, passwordRequest.getPassword(), passwordRequest.getNewPassword());
            return ResponseEntity.ok(Map.of("message", "Password changed successfully"));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Something went wrong"));
        }
    }*/
}