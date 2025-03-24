package adc.task.persistence.repositories;

import org.springframework.stereotype.Repository;

import adc.task.persistence.entities.Tarea;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea,Integer>{
	
	List<Tarea> findAllByFecha(LocalDate fecha);
	List<Tarea> findAllByRealizada(boolean realizada);

}
