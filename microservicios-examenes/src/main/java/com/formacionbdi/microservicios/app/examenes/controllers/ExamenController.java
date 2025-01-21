package com.formacionbdi.microservicios.app.examenes.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.commons.examenes.mappers.ExamenMapper;
import com.formacionbdi.microservicios.commons.examenes.models.dto.AsignaturaDTO;
import com.formacionbdi.microservicios.commons.examenes.models.dto.ExamenDTO;
import com.formacionbdi.microservicios.commons.examenes.models.dto.PreguntaDTO;
import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService>{
	
 
    
    @Autowired
    private ExamenService examenService;
	
	@PostMapping("/crear")
	public ResponseEntity<?> crearExamen(@Validated @RequestBody ExamenDTO examenDTO, BindingResult result) {
	    if (result.hasErrors()) {
	        return this.validar(result);
	    }

	    Examen examen = service.crearExamen(examenDTO);

	    return ResponseEntity.status(HttpStatus.CREATED).body(examen);
	}

	
	
	
	
    // Verifica si existe un método PUT para actualizar un examen
    @PutMapping("/{id}")
    public ResponseEntity<ExamenDTO> actualizarExamen(@PathVariable Long id, @RequestBody ExamenDTO examenDTO) {
        ExamenDTO examenActualizado = examenService.editar(id, examenDTO);
        return ResponseEntity.ok(examenActualizado);
    }
	
	
	 // Método para listar exámenes con transformación a DTO
    @GetMapping("/listar")
    public ResponseEntity<?> listarExamenes() {
        // Obtener los exámenes desde el servicio
        List<Examen> examenes = (List<Examen>) service.finAll();

        // Convertir la lista de Examen a una lista de ExamenDTO
        List<ExamenDTO> examenesDTO = examenes.stream()
                .map(examen -> {
                    // Crear el DTO de Examen
                    ExamenDTO dto = new ExamenDTO();
                    dto.setId(examen.getId());
                    dto.setNombre(examen.getNombre());
                    dto.setCreateAt(examen.getCreateAt());
                   
                    //Convertir asignatura a AsignaturaDTO
                    if(examen.getAsignatura()!=null) {
                    	Asignatura asignatura= examen.getAsignatura();
                    	AsignaturaDTO asignaturaDTO = new AsignaturaDTO(
                    			asignatura.getId(),
                    			asignatura.getNombre(),
                    			asignatura.getPadre()!=null? asignatura.getPadre().getId():null,
                    			asignatura.getHijos().stream().map(Asignatura::getId).toList()
                    					
                    			);
                    }
                    
                    dto.setRespondido(examen.isRespondido());

                    // Convertir las preguntas a PreguntaDTO
                    List<PreguntaDTO> preguntasDTO = examen.getPreguntas().stream()
                    		.map(pregunta -> new PreguntaDTO(pregunta.getId(), pregunta.getTexto()))
                    		.toList();
                    dto.setPreguntas(preguntasDTO);

                    return dto; // Retornar el DTO
                })
                .toList();

        // Retornar la lista de ExamenDTO como respuesta
        return ResponseEntity.ok(examenesDTO);
    }
	
	
	@GetMapping("/filtrar/{term}")
	public ResponseEntity<?>filtrar(@PathVariable String term){
		
		return ResponseEntity.ok(service.findByNombre(term));	
	}
	
	@GetMapping("/asignaturas")
	public ResponseEntity<?> listarAsignaturas(){
		return ResponseEntity.ok(service.findAllAsignaturas());
	}
	
	
}
