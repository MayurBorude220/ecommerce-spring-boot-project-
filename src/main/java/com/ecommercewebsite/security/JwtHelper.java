package com.ecommercewebsite.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Component
public class JwtHelper {
	
	public static final long JWT_TOKEN_VALIDITY=5*60*60;//milliseconds by default
	
	private String secret = "jwtTokenkey";
	
	//retrive username  from jwt token09
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	// retrive expiration date from from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	
	public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver){
		final Claims claims = getAllClaimsForToken(token);
		
		return claimsResolver.apply(claims);
	}
	
	// for retriving any information from token  we will need the secret key
	private Claims getAllClaimsForToken(String token) {
		
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	// check if token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//generate token for user
	public String generateToken(UserDetails userdetails) {
		Map<String, Object> claims = new HashMap<>();
		return doGenerateToken(claims, userdetails.getUsername());
		
	}
	
	//while creating the token
	// 1. Defien the claims of the token, like issuer, Expiration, Subject, and the Id
	// 2. Sign the Jwt using the HSS12 algorithm and the secret key.
	// 3. According to Jws compact Serilization(
	// 4. compaction the Jwt to URL-safe string

	private String doGenerateToken(Map<String, Object> claims, String subject) {
	
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	
	// validate token 
	
	public Boolean validateToken(String token, UserDetails userdetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userdetails.getUsername())&& !isTokenExpired(token));
		
	}
	
	
	
	
}
