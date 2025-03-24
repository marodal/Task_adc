package adc.task.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import adc.task.persistence.entities.Token;
import adc.task.persistence.repositories.TokenRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
	@Autowired
	private AuthenticationProvider authenticationProvider;
	@Autowired
	private TokenRepository tokenRepository;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(req ->
					req.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/tareas/**").permitAll()
						.anyRequest().authenticated()
					)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			.logout(logout ->
					logout.logoutUrl("/auth/logout")
					.addLogoutHandler((request,response,authentication)	-> {
						var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
						logout(authHeader);
					})
					.logoutSuccessHandler((request,response,authentication) ->
						SecurityContextHolder.clearContext())
				);
		return http.build();
	}
	
	private void logout(String token) {
		
		if (token == null || !token.startsWith("Bearer ")) {
			throw new IllegalArgumentException("Invalid token");
		}
		
		String jwToken = token.substring(7);
		Token foundToken = tokenRepository.findByToken(jwToken);
		foundToken.setExpired(true);
		foundToken.setRevoked(true);
		tokenRepository.save(foundToken);
		
	}

}
