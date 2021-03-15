package br.com.alura.forum.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")
	Long expiration;
	
	@Value("${forum.jwt.secret}")
	String secret;

	public String gerarToken(Authentication authentication) {
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		return Jwts.builder()
				.setIssuer("API Forum")
				.setSubject(String.valueOf(usuario.getId()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS256, secret)
				.compact();
	}
	
}
