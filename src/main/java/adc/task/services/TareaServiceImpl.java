package adc.task.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import adc.task.persistence.entities.Tarea;
import adc.task.persistence.repositories.TareaRepository;

@Lazy
@Service
public class TareaServiceImpl implements TareaService{
	
	@Autowired
	private TareaRepository tareaRepository;
	
	public List<Tarea> getTareas(){
		List<Tarea> tareas = tareaRepository.findAll();
		
		return tareas;
	}

	@Override
	public List<Tarea> getTareas(LocalDate fecha) {
		List<Tarea> tareas = tareaRepository.findAllByFecha(fecha);
		return tareas;
	}

	@Override
	public List<Tarea> getTareas(boolean realizada) {
		List<Tarea> tareas = tareaRepository.findAllByRealizada(realizada);
		return tareas;
	}

}
