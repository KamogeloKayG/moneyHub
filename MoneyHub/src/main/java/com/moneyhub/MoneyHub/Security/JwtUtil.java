package com.moneyhub.MoneyHub.Security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.moneyhub.MoneyHub.Entity.MyUserDetails;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	 @Value("${jwt.expiration}") 
	    private long jwtExpiration;
	 
	 private String createToken(Map<String, Object> claims, String subject) {
	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(subject)
	                .setIssuedAt(new Date(System.currentTimeMillis()))
	                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
	                .signWith(SignatureAlgorithm.HS256, secretKey)
	                .compact();
	    }
	 
	 public String generateToken(UserDetails userDetails) {
		    Map<String, Object> claims = new HashMap<>();
		    if (userDetails instanceof MyUserDetails myUser) {
		        claims.put("role", myUser.getRole());
		        claims.put("email", myUser.getUsername());
		    }
		    return createToken(claims, userDetails.getUsername());
		}
	 
	 private Claims extractAllClaims(String token) {
	        return Jwts.parser()
	                .setSigningKey(secretKey)
	                .parseClaimsJws(token)
	                .getBody();
	    }
	 
	 public String extractRole(String token) {
		    return extractAllClaims(token).get("role", String.class);
		}
	 
	 public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    // Validate token
	    public Boolean validateToken(String token, UserDetails userDetails) {
	        final String username = extractUsername(token);
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	    }
	 
	 public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	 
	 public String extractUsername(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }
	 
	 
}
