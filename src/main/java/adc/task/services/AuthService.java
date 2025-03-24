package adc.task.services;

import adc.task.dto.LoginRequest;
import adc.task.dto.RegisterRequest;
import adc.task.dto.TokenResponse;

public interface AuthService {
	
	public TokenResponse register(RegisterRequest request);
	public TokenResponse login(LoginRequest request);
	public TokenResponse refresh(String authHeader);

}
