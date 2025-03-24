package adc.task.configurations;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import adc.task.persistence.entities.Token;
import adc.task.persistence.entities.Usuario;
import adc.task.persistence.repositories.TokenRepository;
import adc.task.persistence.repositories.UsuarioRepository;
import adc.task.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{

	@Autowired
	private JwtService jwtService;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Autowired
	private UserDetailsService userDetailsService;
	
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
		
		if (request.getServletPath().contains("/auth")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwtToken = authHeader.substring(7);
		String username = jwtService.extractUsername(jwtToken);
		
		if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
			return;
		}
		
		Token token = tokenRepository.findByToken(jwtToken);
		
		if (token == null || token.isExpired() || token.isRevoked()) {
			filterChain.doFilter(request, response);
			return;
		}
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
		Usuario usuario = usuarioRepository.findByUsername(username);
		
		if (usuario == null) {
			filterChain.doFilter(request, response);
			return;
		}
		
		boolean isTokenValid = jwtService.isTokenValid(jwtToken, usuario);
		if (!isTokenValid) {
			return;
		}
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				userDetails,
				null,
				userDetails.getAuthorities()
				);
		authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);
		
		filterChain.doFilter(request, response);
	}
	
}
