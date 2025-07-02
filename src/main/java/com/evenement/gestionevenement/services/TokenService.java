package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.exception.JwtError;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface TokenService {
    String generateToken(Authentication authentication);

    Map<String , String> authenticate(
            String grantType, boolean withRefreshToken,
            String username, String password, String refreshToken, String otpCode) throws JwtError;
}
