package adc.task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import adc.task.dto.LoginRequest;
import adc.task.dto.RegisterRequest;
import adc.task.dto.TokenResponse;
import adc.task.services.AuthService;




@RestController
@RequestMapping("/auth")
public class AuthRestController {
	
	@Autowired
	@Lazy
	private AuthService authService;
	
	@PostMapping("/registro")
	public ResponseEntity<?> registro(@RequestBody RegisterRequest request) throws Exception{
		TokenResponse token = authService.register(request);
		return ResponseEntity.ok(token);
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) throws Exception{
		TokenResponse token = authService.login(request);
		return ResponseEntity.ok(token);
	}
	
	@PostMapping("/refresh")
	public TokenResponse refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){
		return authService.refresh(authHeader);

	}
	
}
