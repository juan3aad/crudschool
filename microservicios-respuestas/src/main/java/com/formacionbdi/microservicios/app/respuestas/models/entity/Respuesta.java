package com.formacionbdi.microservicios.app.respuestas.models.entity;



import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name="respuestas")
public class Respuesta {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String texto;
	
	//@ManyToOne(fetch = FetchType.LAZY)
	@Transient
	private Alumno alumno;
	
	@Column(name="alumno_id")
	private Long alumnoId;
	
	

	@OneToOne(fetch = FetchType.LAZY)
	private Pregunta pregunta;

	
}
