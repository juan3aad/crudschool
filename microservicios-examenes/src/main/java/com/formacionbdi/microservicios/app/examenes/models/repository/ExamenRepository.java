package com.formacionbdi.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.commons.examenes.models.dto.ExamenDTO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CustomPagingAndSortingRepository;

public interface ExamenRepository extends CustomPagingAndSortingRepository<Examen,Long> {
	
 @Query("select e from Examen e where e.nombre like %?1%")
 public List<Examen> findByNombre(String term);
 
 /*
 @Query("SELECT new com.formacionbdi.microservicios.commons.examenes.models.dto.ExamenDTO(e.id, e.nombre, e.createAt, e.respondido) FROM Examen e")
 List<ExamenDTO> findAllExamenes();
 */
 
/* @Query("SELECT new com.formacionbdi.microservicios.commons.examenes.models.dto.ExamenDTO(e.id, e.nombre, e.createAt) FROM Examen e")
 List<ExamenDTO> findAllExamenes();
*/
 
 @Query("SELECT e FROM Examen e")
 Iterable<Examen> findAll();


}