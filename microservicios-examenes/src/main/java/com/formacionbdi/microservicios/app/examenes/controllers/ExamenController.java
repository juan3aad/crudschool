package com.formacionbdi.microservicios.app.examenes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService>{

	@PutMapping("/{id}")
	public ResponseEntity<?> editar (@Validated @RequestBody Examen examen,BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validar(result);
		}
		Optional<Examen> exam =service.findById(id);
		if(!exam.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Examen examenDb=exam.get();
		examenDb.setNombre(examen.getNombre());
		
		List<Pregunta> preguntasEliminadas = new ArrayList<>();
		
		examenDb.getPreguntas().forEach(preg->{
			if(!examen.getPreguntas().contains(preg)) {
				preguntasEliminadas.add(preg);
			}
		});
		
		preguntasEliminadas.forEach(pregunta->{
			examenDb.removePregunta(pregunta);
		});
		
		//Desde la linea 31 se puede eliminar la List hasta la 41 para tener el siguiente codigo optimizado
		/*
		examenDb.getPreguntas()
		.stream()
		.filter(preg->!examen.getPreguntas().contains(preg))
		.forEach(examenDb::removePregunta);
		*/
		
		//Optimizacion One
		//preguntasEliminadas.forEach(examenDb::removePregunta);
		
		examenDb.setPreguntas(examen.getPreguntas());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
	}
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?>filtrar(@PathVariable String term){
		
		return ResponseEntity.ok(service.findByNombre(term));	
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
}
