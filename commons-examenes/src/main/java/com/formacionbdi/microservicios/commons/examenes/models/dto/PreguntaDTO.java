package com.formacionbdi.microservicios.commons.examenes.models.dto;

public class PreguntaDTO {

	private Long id;
	private String texto;

	public PreguntaDTO(Long id2, String texto2) {
		this.id=id2;
		this.texto=texto2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
