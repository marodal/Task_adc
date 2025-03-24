package adc.task.services;

import java.util.List;

import adc.task.persistence.entities.Usuario;

public interface UsuarioService {
	
	public List<Usuario> getUsuarios();
	public Usuario getUsuario(String username);
	public Usuario addUsuario(Usuario usuario);
	public Usuario updateUsuario(Usuario usuario);
	public Usuario deleteUsuario(String username);

}
