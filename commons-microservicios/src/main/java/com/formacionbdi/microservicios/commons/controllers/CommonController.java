package com.formacionbdi.microservicios.commons.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;


import com.formacionbdi.microservicios.commons.services.CommonService;




public class CommonController<E,S extends CommonService<E>> {
	
	@Autowired
	protected S service;
	
	
	@GetMapping
	public ResponseEntity<?>listar(){
		return ResponseEntity.ok().body(service.finAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> ver(@PathVariable Long id){
		Optional<E> o = service.findById(id);
		
		if(o.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		//code 200
		return ResponseEntity.ok(o.get());
		
		
	}
	
	@PostMapping
	public ResponseEntity<?> crear (@RequestBody E entity){
		E entityDb = service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(entityDb);
		
	}
	/*
	@PutMapping("/{id}")
	public ResponseEntity<?> editar(@RequestBody Alumno alumno, @PathVariable Long id){
		Optional<Alumno> optionalAlumno= alumnoService.findById(id);
		
		if(optionalAlumno.isEmpty()) {
			//code 404
			return ResponseEntity.notFound().build();
		}
		
		Alumno alumnoDb= optionalAlumno.get();
		alumnoDb.setNombre(alumno.getNombre());
		alumnoDb.setApellido(alumno.getApellido());
		alumnoDb.setEmail(alumno.getEmail());
		
		//code 201
		return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.save(alumnoDb));
		
		
	}*/
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id){
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
	

}
