package com.formacionbdi.microservicios.app.examenes.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.formacionbdi.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen,ExamenRepository> implements ExamenService {

	@Autowired
	private AsignaturaRepository asignaturaRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Examen> findByNombre(String term) {
		
		return repository.findByNombre(term);
	}
/*
	@Override
	@Transactional(readOnly = true)
	public List<Asignatura> findAllAsignaturas() {
		// TODO Auto-generated method stub
		return (List<Asignatura>) asignaturaRepository.findAll();
	}
*/
	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignaturas(){
		return asignaturaRepository.findAll();
	}
	/*
	@Override
	public Iterable<Examen> finAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Examen> findById(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public Examen save(Examen entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub

	}
*/
}
