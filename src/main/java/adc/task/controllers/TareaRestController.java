package adc.task.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import adc.task.persistence.entities.Tarea;
import adc.task.services.TareaService;


@RestController
@RequestMapping("/tareas")
public class TareaRestController {
	
	@Autowired
	@Lazy	//Hacemos que Spring solo inicialice tareasService cuando lo necesite el endpoint
			//y no al iniciar la app, para no malgastar espacio en memoria
	private TareaService tareasService;

	
	@GetMapping
	public ResponseEntity<?> getTareas(){
		
		List<Tarea> tareas = tareasService.getTareas();
		return ResponseEntity.ok(tareas);
	}
	
	@GetMapping("/por-fecha")
	public ResponseEntity<?> getTareasPorFecha(@RequestParam 
	@DateTimeFormat(pattern = "yyyy-mm-dd") LocalDate fecha){
		List<Tarea> tareas = tareasService.getTareas(fecha);
		
		return ResponseEntity.ok(tareas);
	}
	
	@GetMapping("/por-realizadas")
	public ResponseEntity<?> getTareasPorRealizadas(@RequestParam boolean realizada){
		List<Tarea> tareas = tareasService.getTareas(realizada);
		
		return ResponseEntity.ok(tareas);
	}
	
	/*@PostMapping
	public ResponseEntity<?> getTareas(@RequestParam("date") String fecha){
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.parse(fecha, dtf); 		
		List<Tarea> tareas = tareasService.getTareas(localDate);
		
		return ResponseEntity.ok(tareas);
	}*/

}
