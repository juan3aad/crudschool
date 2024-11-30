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
    private List<Alumno> alumnosIds; // Solo IDs para evitar problemas de serialización
    private List<Examen> examenesIds; // Solo IDs para evitar problemas de serialización
}