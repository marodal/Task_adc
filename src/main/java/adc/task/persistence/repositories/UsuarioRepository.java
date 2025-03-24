package adc.task.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import adc.task.persistence.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	
	Usuario findByUsername(String username);
	Usuario findByUsernameAndPassword(String username, String password);
	

}
