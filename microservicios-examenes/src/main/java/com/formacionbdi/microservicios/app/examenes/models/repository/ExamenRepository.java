package com.formacionbdi.microservicios.app.examenes.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CustomPagingAndSortingRepository;

public interface ExamenRepository extends CustomPagingAndSortingRepository<Examen,Long> {
	
 @Query("select e from Examen e where e.nombre like %?1%")
 public List<Examen> findByNombre(String term);
}
