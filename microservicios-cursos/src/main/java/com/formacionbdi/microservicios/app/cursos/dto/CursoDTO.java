package com.formacionbdi.microservicios.app.cursos.dto;

import java.util.Date;
import java.util.List;

import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;

import lombok.Data;

@Data
public class CursoDTO {
    private Long id;
    private String nombre;
    private Date createAt;
    private List<Long> alumnosIds; // Solo IDs para evitar problemas de serialización
    private List<Long> examenesIds; // Solo IDs para evitar problemas de serialización
    
    public CursoDTO() {
    	
    }
    
    public CursoDTO(Long id, String nombre, Date createAt,List<Long> alumnoIds, List<Long> examenesIds) {
    	this.id=id;
    	this.nombre=nombre;
    	this.createAt=createAt;
    	this.alumnosIds=alumnosIds;
    	this.examenesIds=examenesIds;
    }
    
    

}