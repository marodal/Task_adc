package adc.task.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import adc.task.persistence.entities.Usuario;
import adc.task.persistence.repositories.UsuarioRepository;

@Lazy
@Service
public class UsuarioServiceImpl implements UsuarioService{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	public List<Usuario> getUsuarios(){
		return usuarioRepository.findAll();
		
		
	}
	
	public Usuario getUsuario(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username); 
		if ( usuario != null) {
			return usuario;
			
		}
		
		return null;
	}

	public Usuario addUsuario(Usuario usuario) {
		return usuarioRepository.save(usuario);
		
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		Usuario usu = usuarioRepository.findByUsername(usuario.getUsername()); 
		if (usu!=null) {
			usu.setNombre(usuario.getNombre());
			usu.setPassword(usuario.getPassword());
			return usuarioRepository.save(usu);
			
		}
		else {
			return null;
		}
		
	}

	@Override
	public Usuario deleteUsuario(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username);
		if (usuario != null) {
			usuarioRepository.deleteById(usuario.getId());
			return usuario;
		}
		return null;
	}

	
	
	

}
