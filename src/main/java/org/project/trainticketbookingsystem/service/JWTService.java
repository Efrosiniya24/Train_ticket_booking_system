package org.project.trainticketbookingsystem.service;

import io.jsonwebtoken.Claims;
import org.project.trainticketbookingsystem.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JWTService {
    String generateAccessToken(UserEntity user);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Claims extractAllClaims(String token);
    String extractUsername(String jwt);
    boolean isTokenValid(String token, UserDetails userDetails);
}
