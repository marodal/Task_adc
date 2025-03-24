package adc.task.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoRestController {
	
	@GetMapping("/saludo/{nombreUsuario}")
	public String saludo(@PathVariable String nombreUsuario) {
		return "Hola " + nombreUsuario+ ". Bienvenido a Spring Boot";
	}

}
