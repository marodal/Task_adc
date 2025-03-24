package adc.task.services;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import adc.task.persistence.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

@Lazy
@Service
public class JwtServiceImpl implements JwtService{
	
	@Value("${application.security.jwt.secret-key}")
	private String secretKey;
	@Value("${application.security.jwt.expiration}")
	private Long jwtExpiration;
	@Value("${application.security.jwt.refresh-token.expiration}")
	private Long refreshExpiration;
	

	public String generateToken(Usuario usuario) {
		// TODO Auto-generated method stub
		return buildToken(usuario,jwtExpiration);
	}

	
	public String generateRefreshToken(Usuario usuario) {
		// TODO Auto-generated method stub
		return buildToken(usuario,refreshExpiration);
	}


	public String buildToken(Usuario usuario, long expiration) {
		// TODO Auto-generated method stub
		return Jwts.builder()
				.setId(Integer.valueOf(usuario.getId()).toString())
				.setClaims(Map.of("name",usuario.getNombre()))
				.setSubject(usuario.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+jwtExpiration))
				.signWith(getSignInKey())
				.compact();
	}
	
	private SecretKey getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}


	@Override
	public String extractUsername(String token) {
		Claims jwtToken = Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
				
		return jwtToken.getSubject();
	}
	
	public boolean isTokenValid(String token, Usuario usuario) {
		String username = extractUsername(token);
		
		return username.equals(usuario.getUsername()) && !isTokenExpired(token);
	}
	
	private boolean isTokenExpired(String token) {
		
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		Claims jwtToken = Jwts.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
				
		return jwtToken.getExpiration();
	}
		
	
}
