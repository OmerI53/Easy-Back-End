package com.example.Easy.dao;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtDao {
    public String extractUserName(String token);
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction);
    public String generateToken(UserDetails user);
    public String generateToken(Map<String, Object> extraClaims, UserDetails user);
    public boolean isTokenValid(String token, UserDetails userDetails);

}
