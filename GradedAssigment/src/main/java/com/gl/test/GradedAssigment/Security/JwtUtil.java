package com.gl.test.GradedAssigment.Security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.gl.test.GradedAssigment.model.AppUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	private static String secret = "Z9ORnVaAQ3UuYu3oKX1tAlg4XuJFQyIfvnpuqUO2u2mGyfP1iyoDKhG2ODJu0bZC\r\n";

	public Key getSignKey() {
	byte[] keySecret = Decoders.BASE64.decode(secret);
	return Keys.hmacShaKeyFor(keySecret);
	}

	public String generateJwt(AppUser user) {
	Date issedAt = new Date();

	Claims claims = Jwts.claims().setIssuer(String.valueOf(user.getId())).setIssuedAt(issedAt);

	return Jwts.builder().addClaims(claims).setSubject(user.getName())
	.setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 30))
	.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public Claims verify(String authorizaton) throws Exception {

	try {
	Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorizaton).getBody();
	return claims;

	} catch (Exception e) {
	// throw the new AccessDeniedException("Access Denied")
	return null;
	}
	}
	}

