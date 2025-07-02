package com.evenement.gestionevenement.services;

import com.evenement.gestionevenement.exception.JwtError;

import java.util.Map;

public interface AuthenticationService {
    Map<String , String> authenticate(
            String granType, boolean withRefreshToken,
            String username, String password, String refreshToken) throws JwtError;
}
