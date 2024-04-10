package com.alexpages.ordermanager.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	//TODO migrate to env and provide prod key
	private static final String KEY = "javax.crypto.spec.SecretKeySpec@5881a61";
	public String generateToken(String userName) 
	{ 
	   Map<String, Object> claims = new HashMap<>(); 
        return createToken(claims, userName); 
    } 

    public String extractUsername(String token) 
    { 
        return extractClaim(token, Claims::getSubject); 
    } 
    
    public Date extractExpiration(String token) 
    { 
        return extractClaim(token, Claims::getExpiration); 
    } 
  
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) 
    { 
        final Claims claims = extractAllClaims(token); 
        return claimsResolver.apply(claims); 
    } 
    
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
        byte[] keyBytes = this.KEY.getBytes(StandardCharsets.UTF_8);
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
    
//    //TODO remove
//    public static void main(String[] args) {
//        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        System.out.println("Generated Key (For development only, remove before production): " + key);
//    }
}
  
