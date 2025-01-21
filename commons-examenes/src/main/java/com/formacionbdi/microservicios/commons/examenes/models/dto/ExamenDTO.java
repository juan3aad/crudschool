package com.formacionbdi.microservicios.commons.examenes.models.dto;

import java.util.Date;
import java.util.List;



import jakarta.validation.constraints.NotNull;

public class ExamenDTO {

    private Long id;
    private String nombre;
    private Date createAt;
    private List<PreguntaDTO> preguntas; // IDs de las preguntas asociadas
    @NotNull(message = "El ID de la asignatura no puede ser nulo")
    private AsignaturaDTO asignatura; // ID de la asignatura asociada
    private boolean respondido;

    public ExamenDTO() {
    }

    public ExamenDTO(Long id, String nombre, Date createAt, List<PreguntaDTO> preguntas, AsignaturaDTO asignatura, boolean respondido) {
        this.id = id;
        this.nombre = nombre;
        this.createAt = createAt;
        this.preguntas = preguntas;
        this.asignatura = asignatura;
        this.respondido = respondido;
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

	public List<PreguntaDTO> getPreguntas() {
		return preguntas;
	}



	public void setPreguntas(List<PreguntaDTO> list) {
		this.preguntas = list;
	}

	

	public AsignaturaDTO getAsignatura() {
		return asignatura;
	}

	public void setAsignatura(AsignaturaDTO asignatura) {
		this.asignatura = asignatura;
	}

	public boolean isRespondido() {
		return respondido;
	}

	public void setRespondido(boolean respondido) {
		this.respondido = respondido;
	}

   
}