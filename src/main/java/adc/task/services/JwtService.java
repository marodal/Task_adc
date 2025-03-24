package adc.task.services;

import adc.task.persistence.entities.Usuario;

public interface JwtService {

	public String generateToken(Usuario usuario);
	public String generateRefreshToken(Usuario usuario);
	public String buildToken(Usuario usuario, long expiration);
	public String extractUsername(String token);
	public boolean isTokenValid(String token, Usuario usuario);

}

