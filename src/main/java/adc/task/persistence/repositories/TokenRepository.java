package adc.task.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import adc.task.persistence.entities.Token;

public interface TokenRepository extends JpaRepository<Token,Integer>{
	Token findByToken(String token);
	List<Token> findByExpiredAndRevokedAndUsuario_id(boolean expired, boolean revoked, int usuario_id);
	
}
