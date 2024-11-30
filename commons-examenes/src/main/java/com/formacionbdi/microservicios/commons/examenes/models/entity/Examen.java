package com.formacionbdi.microservicios.commons.examenes.models.entity;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name="examenes")

public class Examen {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Size(min= 4, max=30)
	private String nombre;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_at")
	private Date createAt;
	
	@JsonIgnoreProperties(value= {"examen"},allowSetters = true)
	@OneToMany(mappedBy="examen",fetch =FetchType.LAZY,cascade=CascadeType.ALL,orphanRemoval = true)
	private List<Pregunta> preguntas;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JsonIgnore 
	@NotNull
	private Asignatura asignatura;
	
	@Transient
	private boolean respondido;
	

	public Examen() {
		this.preguntas=new ArrayList<>();
	}
	
	@PrePersist
	public void prePersist() {
		this.createAt=new Date();
	}
	
	
	public void addPregunta(Pregunta pregunta) {
		this.preguntas.add(pregunta);
		pregunta.setExamen(this);
	}
	
	public void removePregunta(Pregunta pregunta ) {
		this.preguntas.remove(pregunta);
		pregunta.setExamen(null);
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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas.clear();
		preguntas.forEach(p->{
			this.addPregunta(p);
		});
		
		//optimizacion one
		//preguntas.forEach(p->this.addPregunta(p));
		
		//optimizacion two
		//preguntas.forEach(this::addPregunta);
	}

	@Override
	public boolean equals(Object obj) {
		if(this==obj) {
			return true;
		}
		
		if(!(obj instanceof Examen)) {
			return false;
		}
		
		Examen exam= (Examen) obj;
		return this.id != null && this.id.equals(exam.getId());
		
	}

	public Asignatura getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(Asignatura asignatura) {
		this.asignatura = asignatura;
	}

	public boolean isRespondido() {
		return respondido;
	}

	public void setRespondido(boolean respondido) {
		this.respondido = respondido;
	}
	
	
	

}
