package com.formacionbdi.microservicios.commons.examenes.models.dto;

import java.util.List;


public class AsignaturaDTO {
	private Long id;
	private String nombre;
	private Long padreId;
	private List<Long>hijosIds;
	
	public AsignaturaDTO() {
		
	}
	
	public AsignaturaDTO(Long id, String nombre, Long padreId, List<Long> hijosIds) {
		this.id=id;
		this.nombre=nombre;
		this.padreId=padreId;
		this.hijosIds=hijosIds;
		
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getPadreId() {
		return padreId;
	}

	public void setPadreId(Long padreId) {
		this.padreId = padreId;
	}

	public List<Long> getHijosIds() {
		return hijosIds;
	}

	public void setHijosIds(List<Long> hijosIds) {
		this.hijosIds = hijosIds;
	}
	
	

}
