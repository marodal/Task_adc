package adc.task.services;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import adc.task.dto.LoginRequest;
import adc.task.dto.RegisterRequest;
import adc.task.dto.TokenResponse;
import adc.task.persistence.entities.Usuario;
import adc.task.persistence.entities.Token;
import adc.task.persistence.repositories.TokenRepository;
import adc.task.persistence.repositories.UsuarioRepository;

@Lazy
@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	public TokenResponse register(RegisterRequest request){
		Usuario usuario = new Usuario(request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getNombre());//(request.getUsername(),passwordEncoder.encode(request.getPassword()),request.getNombre()));
		usuarioRepository.save(usuario);
		String jwtToken = jwtService.generateToken(usuario);
		String refreshToken = jwtService.generateRefreshToken(usuario);
		saveUserToken(usuario,jwtToken);
		return new TokenResponse(jwtToken,refreshToken);
	}
	
	private void saveUserToken(Usuario usuario, String jwtToken) {
		Token token = new Token(jwtToken,false,false,usuario);
		tokenRepository.save(token);
	}

	@Override
	public TokenResponse login(LoginRequest request){
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						request.getUsername(),
						request.getPassword())
				);
		Usuario usuario = usuarioRepository.findByUsername(request.getUsername());
		
		String jwtToken = jwtService.generateToken(usuario);
		String refreshToken = jwtService.generateRefreshToken(usuario);
		revokeAllUserToken(usuario);
		saveUserToken(usuario,jwtToken);
		return new TokenResponse(jwtToken,refreshToken);
		
	}
	
	private void revokeAllUserToken(Usuario usuario) {
		List<Token> validUserTokens = tokenRepository.findByExpiredAndRevokedAndUsuario_id(false, false, usuario.getId());
		
		if (!validUserTokens.isEmpty()) {
			for(Token token:validUserTokens) {
				token.setExpired(true);
				token.setRevoked(true);
			}
		}
		tokenRepository.saveAll(validUserTokens);
	}
	
	
	public TokenResponse refresh(String authHeader) {
		if (authHeader == null || !authHeader.startsWith("Bearer")) {
			throw new IllegalArgumentException("Invalid Bearer Token");
		}
		String refreshToken = authHeader.substring(7);
		String username = jwtService.extractUsername(refreshToken);
		
		if (username == null) {
			throw new IllegalArgumentException("Invalid Refresh Token");
		}
		
		Usuario usuario = usuarioRepository.findByUsername(username);
		
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		
		if (!jwtService.isTokenValid(refreshToken,usuario)) {
			throw new IllegalArgumentException("Invalid Refresh Token");
		}
		
		String accessToken = jwtService.generateToken(usuario);
		revokeAllUserToken(usuario);
		saveUserToken(usuario,accessToken);
		
		return new TokenResponse(accessToken, refreshToken);
	}
	

}
