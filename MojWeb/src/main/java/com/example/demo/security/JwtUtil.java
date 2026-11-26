package com.example.demo.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration-ms}")
	private long expirationMs;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes());
	}

	public String generisiToken(String korisnickoIme, Long korisnikId, String uloga) {
		return Jwts.builder()
				.setSubject(korisnickoIme)
				.claim("id", korisnikId)
				.claim("uloga", uloga)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationMs))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	public String izvuciKorisnickoIme(String token) {
		return izvuciClaim(token, Claims::getSubject);
	}

	public Long izvuciKorisnikId(String token) {
		Claims claims = izvuciSveClaimove(token);
		return claims.get("id", Long.class);
	}

	public String izvuciUlogu(String token) {
		return izvuciClaim(token, claims -> claims.get("uloga", String.class));
	}

	private <T> T izvuciClaim(String token, Function<Claims, T> resolver) {
		Claims claims = izvuciSveClaimove(token);
		return resolver.apply(claims);
	}

	private Claims izvuciSveClaimove(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}

	public boolean jeTokenValidan(String token) {
		try {
			izvuciSveClaimove(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
