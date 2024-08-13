package com.formacionbdi.microservicios.app.cursos.models.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;



@Entity
@Table(name="cursos")
@Data
public class Curso {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@Column(name="create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@PrePersist
	public void prePersist() {
		this.createAt=new Date();
	}

}
