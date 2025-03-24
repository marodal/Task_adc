package adc.task.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import adc.task.exceptions.ResourceNotFoundException;
import adc.task.persistence.entities.Usuario;
import adc.task.services.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/usuarios")
@Tag(name="Users resource")
public class UsuarioRestController {
	
	
	
	@Autowired
	@Lazy
	private UsuarioService usuarioService;
	
	@PostMapping
	private ResponseEntity<?> addUsuario(@RequestBody Usuario usuario) {
		usuarioService.addUsuario(usuario);
		return ResponseEntity.ok(usuario);
	}
	
	/*@PostMapping("/login")
	private ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("passwd") String passwd ){
		
		String token = getJWTToken(username);
		
	}*/
	
	@GetMapping
	private ResponseEntity<?> getUsuarios(){
		List<Usuario> usuarios = usuarioService.getUsuarios();
		return ResponseEntity.ok(usuarios);
	}
	
	@GetMapping("/{username}")
	private ResponseEntity<?> getUsuario(@PathVariable String username) {
		
		Usuario usu = usuarioService.getUsuario(username);
		
		return ResponseEntity.ok(usu);
		
	}
	
	@PutMapping
	private ResponseEntity<?> modifyUsuario(@RequestBody Usuario usuario) {
		
		Usuario usu = usuarioService.updateUsuario(usuario);
		
		
		return ResponseEntity.ok(usu);
		
		
		//throw new ResourceNotFoundException("Usuario no encontrado");
	}
	
	@DeleteMapping("/{username}")
	private ResponseEntity<?> deleteUsuario(@PathVariable String username) {
		Usuario usu = usuarioService.deleteUsuario(username);
		//if (usu != null) {
			return ResponseEntity.ok(usu);
		//}
		
		//throw new ResourceNotFoundException("Usuario no encontrado");
	}

}
