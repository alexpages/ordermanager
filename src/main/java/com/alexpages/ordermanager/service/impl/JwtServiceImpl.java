package com.alexpages.ordermanager.service.impl;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.alexpages.ordermanager.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtServiceImpl implements JwtService{

	@Value("${thirdparties.jwt.key}")
	private String key;
	
	@Override
	public String generateToken(String username) 
	{
		Map<String, Object> claims = new HashMap<>(); 
        return createToken(claims, username); 
    } 

	@Override
    public String extractUsername(String token) 
    { 
        return extractClaim(token, Claims::getSubject); 
    } 
    
	@Override
    public Date extractExpiration(String token) 
    { 
        return extractClaim(token, Claims::getExpiration); 
    } 
  
	@Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
    { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 
    
	@Override
    public Boolean validateToken(String token, UserDetails userDetails) 
    { 
        final String username = extractUsername(token); 
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)); 
    } 
    
    private String createToken(Map<String, Object> claims, String username) 
	{
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
    private Key getSigningKey() {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    
    private Claims extractAllClaims(String token) 
    { 
        return Jwts 
                .parserBuilder() 
                .setSigningKey(getSigningKey()) 
                .build() 
                .parseClaimsJws(token) 
                .getBody(); 
    }
    
    private Boolean isTokenExpired(String token) 
    { 
        return extractExpiration(token).before(new Date()); 
    } 

}