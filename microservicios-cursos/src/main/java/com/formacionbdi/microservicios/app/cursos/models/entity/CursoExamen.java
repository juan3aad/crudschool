package com.formacionbdi.microservicios.app.cursos.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity

@Table(name="cursos_examenes")
public class CursoExamen {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="examen_id", unique =true)
	private Long examenId;
	
	@JsonIgnoreProperties(value = {"cursoExamenes"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="curso_id")
	private Curso curso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getExamenId() {
		return examenId;
	}

	public void setExamenId(Long examenId) {
		this.examenId = examenId;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if(this==obj) {
			return true;
		}
		
		if(!(obj instanceof CursoExamen)) {
			return false;
		}
		
		CursoExamen a= (CursoExamen) obj;
		
		return this.examenId != null && this.examenId.equals(a.getExamenId());
		
	}

}
