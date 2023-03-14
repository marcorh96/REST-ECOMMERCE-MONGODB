package com.marcorh96.springboot.rest.ecommerce.app.config.jwt;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtProvider {
    private static final Key SECRET_KEY= new SecretKeySpec("5A7134743777397A24432646294A404E635266556A586E3272357538782F4125".getBytes(), SignatureAlgorithm.HS512.getJcaName());
	
	private static final long EXPIRATION_TIME = 3600_000;

	public static String generarTokenJWT(String username) {
		return Jwts.builder().setSubject(username)
                .setExpiration(Date.from(Instant.now().plusMillis(EXPIRATION_TIME))).signWith(SECRET_KEY)
                .compact();
	}

	public static boolean validarTokenJWT(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
	        return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static String getUsername(String token) {
		JwtParser parser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build();
	    return parser.parseClaimsJws(token).getBody().getSubject();
	}
}
