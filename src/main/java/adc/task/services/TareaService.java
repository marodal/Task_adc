package adc.task.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import adc.task.persistence.entities.Tarea;

public interface TareaService {

	public List<Tarea> getTareas();
	public List<Tarea> getTareas(LocalDate fecha);
	public List<Tarea> getTareas(boolean realizada);
	
}
