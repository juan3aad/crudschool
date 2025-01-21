package com.formacionbdi.microservicios.app.examenes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formacionbdi.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.examenes.models.dto.ExamenDTO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CommonService;


public interface ExamenService extends CommonService<Examen> {
	
	
	//Metodos genericos del ser
	
	List<ExamenDTO> findByNombre(String term);
	public Iterable<Asignatura> findAllAsignaturas();
	Examen crearExamen(ExamenDTO examenDTO);
	ExamenDTO editar(Long id, ExamenDTO examenDTO);
	
	
	
	

	
}
