package com.formacionbdi.microservicios.app.respuestas.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.respuestas.models.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.services.RespuestaService;

import jakarta.ws.rs.Path;

@RestController
public class RespuestaController {
	
	@Autowired
	private RespuestaService respuestaService;
	
	
	@PostMapping
	public ResponseEntity<?> crear (@RequestBody Iterable<Respuesta> respuestas){
		respuestas=((List<Respuesta>)respuestas).stream().map(r->{
			r.setAlumnoId(r.getAlumno().getId());
			return r;
		}).collect(Collectors.toList());
		Iterable<Respuesta> respuestaDb=respuestaService.saveAll(respuestas);
		return ResponseEntity.status(HttpStatus.CREATED).body(respuestaDb);
	}

	@GetMapping("/alumno/{alumnoId}/examen/{examenId}")
	public ResponseEntity<?> obtenerRespuestaPorAlumnoPorExamen(@PathVariable Long alumnoId, @PathVariable Long examenId){
		Iterable<Respuesta> respuestas= respuestaService.findRespuestaByAlumnoByExamen(alumnoId, examenId);
		return ResponseEntity.ok(respuestas);
	}
	
	@GetMapping("/alumno/{alumnoId}/examenes-respondidos")
	public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId){
		Iterable<Long> examenesIds= respuestaService.findExamenesIdsConRespuestasByAlumno(alumnoId);
		return ResponseEntity.ok(examenesIds);
		
	}
	
	

}
