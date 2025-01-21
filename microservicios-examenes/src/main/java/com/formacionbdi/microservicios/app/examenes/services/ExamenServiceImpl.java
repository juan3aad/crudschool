package com.formacionbdi.microservicios.app.examenes.services;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.formacionbdi.microservicios.app.examenes.models.repository.AsignaturaRepository;
import com.formacionbdi.microservicios.app.examenes.models.repository.ExamenRepository;
import com.formacionbdi.microservicios.commons.examenes.mappers.ExamenMapper;
import com.formacionbdi.microservicios.commons.examenes.models.dto.AsignaturaDTO;
import com.formacionbdi.microservicios.commons.examenes.models.dto.ExamenDTO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;
import com.formacionbdi.microservicios.commons.services.CommonServiceImpl;

@Service
public class ExamenServiceImpl extends CommonServiceImpl<Examen,ExamenRepository> implements ExamenService {

	@Autowired
	private AsignaturaRepository asignaturaRepository;
	
	@Autowired
	private ExamenRepository examenRepository;
	
	@Autowired
    private ExamenMapper examenMapper;  // Inyectamos el mapper
	
	
	
	@Override
	public List<ExamenDTO> findByNombre(String nombre) {
	    List<Examen> examenes = examenRepository.findByNombre(nombre);
	    return examenes.stream()
	    		 .map(examen -> examenMapper.toDTO(examen)) // Usamos el mapper inyectado
                 .collect(Collectors.toList());
	}

	
	@Override
	@Transactional(readOnly = true)
	public Iterable<Asignatura> findAllAsignaturas(){
		return asignaturaRepository.findAll();
	}
	
	
	@Override
	@Transactional
	public Examen crearExamen(ExamenDTO examenDTO) {
	    Examen examen = new Examen();
	    examen.setNombre(examenDTO.getNombre());
	    examen.setCreateAt(examenDTO.getCreateAt());

	    // Validar y asignar la asignatura si corresponde
	    if (examenDTO.getAsignatura() != null) {
	        AsignaturaDTO asignaturaDTO = examenDTO.getAsignatura();
	        Asignatura asignatura = asignaturaRepository.findById(asignaturaDTO.getId())
	                .orElseThrow(() -> new IllegalArgumentException("Asignatura no encontrada para el ID proporcionado JA"));
	        examen.setAsignatura(asignatura);
	    }
	    
	    
	    //Manejar Preguntas
	    
	    if(examenDTO.getPreguntas()!=null) {
	    	List<Pregunta> preguntas=examenDTO.getPreguntas().stream()
	    			.map(preguntaDTO->{
	    				Pregunta pregunta = new Pregunta();
	    				pregunta.setId(preguntaDTO.getId());
	    				pregunta.setTexto(preguntaDTO.getTexto());
	    				return pregunta;
	    			})
	    			.toList();
	    	examen.setPreguntas(preguntas);
	    }

	    return examenRepository.save(examen);
	}
	
	@Override
	@Transactional
	public ExamenDTO editar(Long id, ExamenDTO examenDTO) {
	    // Buscar el examen existente
	    Examen examenDb = examenRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Examen no encontrado con id: " + id));

	    // Actualizar el nombre del examen
	    examenDb.setNombre(examenDTO.getNombre());

	    // Actualizar la asignatura si se proporciona
	    if (examenDTO.getAsignatura() != null) {
	    	AsignaturaDTO asignaturaDTO= examenDTO.getAsignatura();
	    	Asignatura asignatura = asignaturaRepository.findById(asignaturaDTO.getId())
	                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada para el ID: " + examenDTO.getAsignatura()));
	        examenDb.setAsignatura(asignatura);
	    }

	    // Actualizar preguntas
	    if (examenDTO.getPreguntas() != null) {
	        List<Pregunta> preguntasNuevas = examenDTO.getPreguntas().stream()
	                .map(p -> {
	                    Pregunta pregunta = new Pregunta();
	                    pregunta.setId(p.getId());
	                    pregunta.setTexto(p.getTexto());
	                    return pregunta;
	                }).collect(Collectors.toList());

	        // Eliminar preguntas que ya no están
	        examenDb.getPreguntas().removeIf(p -> !preguntasNuevas.contains(p));

	        // Agregar nuevas preguntas
	        preguntasNuevas.forEach(p -> {
	            if (!examenDb.getPreguntas().contains(p)) {
	                examenDb.addPregunta(p);
	            }
	        });
	    }

	    // Guardar los cambios en la base de datos
	    Examen examenActualizado = examenRepository.save(examenDb);

	    // Devolver el ExamenDTO actualizado
	    return examenMapper.toDTO(examenActualizado);
	}


	
}
