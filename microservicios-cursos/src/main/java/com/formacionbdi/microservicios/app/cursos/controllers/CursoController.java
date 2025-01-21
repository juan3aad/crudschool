package com.formacionbdi.microservicios.app.cursos.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.formacionbdi.microservicios.app.cursos.dto.CursoDTO;
import com.formacionbdi.microservicios.app.cursos.models.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.models.entity.CursoAlumno;
import com.formacionbdi.microservicios.app.cursos.models.entity.CursoExamen;
import com.formacionbdi.microservicios.app.cursos.services.CursoService;
import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.alumnos.models.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;



@RestController
@RequestMapping("/curso")
public class CursoController extends CommonController<Curso, CursoService> {

    private final ExamenService examenService;
    
    
    @Value("${config.balanceador.test}")
    private String balanceadorTest;
    
    public CursoController(ExamenService examenService) {
    	this.examenService=examenService;
    }
    
    
    @GetMapping("/test")
    public ResponseEntity<Map<String,String>>testEndPoint(){
    	Map<String,String> response=Map.of("message","El endpoint esta funcionando correctamente.");
    	return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<Void>eliminarCursoAlumnoPorId(@PathVariable Long id){
    	service.eliminarCursoAlumnoPorId(id);
    	return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/pagina")
    @Override
    public ResponseEntity<Page<Curso>>listar(Pageable pageable){
    	Page<Curso> cursos=service.findAll(pageable)
    			.map(this::mapearAlumnosEnCurso);
    	return ResponseEntity.ok(cursos);
    }
    
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Curso> ver (@PathVariable Long id){
    	return service.findById(id)
    			.map(this::mapearAlumnosPorIds)
    			.map(ResponseEntity::ok)
    			.orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/balanceador-test")
    public ResponseEntity<Map<String,Object>>balanceadorTest(){
    	Map<String,Object>response= new HashMap<>();
    	response.put("balanceador",balanceadorTest);
    	response.put("cursos", service.finAll());
    	return ResponseEntity.ok(response);
    	
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?>editar(@Validated @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
    	if(result.hasErrors()) return validarErrores(result);
    	
    	return service.findById(id)
    			.map(cursoDb->{
    				cursoDb.setNombre(curso.getNombre());
    				return ResponseEntity.ok(service.save(cursoDb));
    			})
    			.orElse(ResponseEntity.notFound().build());
    	
    }
    
    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?>asignarAlumnos(@RequestBody List<Alumno> alumnos, @PathVariable Long id){
    	return actualizarCurso(id,curso->{
    		alumnos.forEach(alumno->{
    			
    			boolean yaAsignado= curso.getCursoAlumnos().stream()
    					.anyMatch(cursoAlumno->cursoAlumno.getAlumnoId().equals(alumno.getId()));
    			
    			if(!yaAsignado) {
    				CursoAlumno cursoAlumno= new CursoAlumno();
        			cursoAlumno.setAlumnoId(alumno.getId());
        			cursoAlumno.setCurso(curso);
        			curso.addCursoAlumno(cursoAlumno);
    				
    			}
    			
    		});
    	});
    }
    
    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?>eliminarAlumno(@RequestBody Alumno alumno, @PathVariable Long id){
    	return actualizarCurso(id, curso->{
    		CursoAlumno cursoAlumno= new CursoAlumno();
    		cursoAlumno.setAlumnoId(alumno.getId());
    		curso.removeCursoAlumno(cursoAlumno);
    	});
    }
    
    @GetMapping("/alumno/{id}")
    public ResponseEntity<?>buscarPorAlumnoId(@PathVariable Long id){
    	Curso curso = service.findCursoByAlumnoId(id);
    	if(curso==null) return ResponseEntity.notFound().build();
    	CursoDTO cursoDTO = new CursoDTO();
    	cursoDTO.setId(curso.getId());
    	cursoDTO.setNombre(curso.getNombre());
    	return ResponseEntity.ok(cursoDTO);
    }
    
    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?>asignarExamenes(@RequestBody List<Examen>examenes, @PathVariable Long id){
    	return actualizarCurso(id,curso ->{
    		examenes.forEach(examen->{
    			
    			boolean examenYaAsignado= curso.getCursoExamenes()
    					.stream()
    					.anyMatch(cursoExamen -> cursoExamen.getExamenId().equals(examen.getId()));
    			
    			if(examenYaAsignado) {
    				throw new IllegalArgumentException("El examen con ID "+ examen.getId()+ " ya está registrado el curso." );
    			}
    			
    			CursoExamen cursoExamen= new CursoExamen();
    			cursoExamen.setExamenId(examen.getId());
    			cursoExamen.setCurso(curso);
    			curso.addCursoExamen(cursoExamen);
    		});
    	});
    }
    
    @PutMapping("/{id}/eliminar-examen")
    public ResponseEntity<?>eliminarExamen(@RequestBody Examen examen, @PathVariable Long id){
    	return actualizarCurso(id, curso->curso.removeExamen(examen));
    }
    
    //Métodos privados para evitar duplicacion de lógica
    
    private Curso mapearAlumnosPorIds(Curso curso) {
    	if(!curso.getCursoAlumnos().isEmpty()) {
    		List<Long> ids =curso.getCursoAlumnos().stream()
    				.map(CursoAlumno::getAlumnoId)
    				.collect(Collectors.toList());
    		List<Alumno> alumnos=(List<Alumno>) service.obtenerAlumnosPorCurso(ids);
    		curso.setAlumnos(alumnos);
    	}
    	return curso;
    }
    
    private Curso mapearAlumnosEnCurso(Curso curso) {
    	curso.getCursoAlumnos().forEach(cursoAlumno->{
    		Alumno alumno= new Alumno();
    		alumno.setId(cursoAlumno.getAlumnoId());
    		curso.addAlumno(alumno);
    	});
    	return curso;
    }
    
    private ResponseEntity<?>actualizarCurso(Long id, java.util.function.Consumer<Curso> actualizacion) {
    	Optional<Curso> optionalCurso=service.findById(id);
    	if(optionalCurso.isEmpty()) return ResponseEntity.notFound().build();
    	
    	Curso curso = optionalCurso.get();
    	actualizacion.accept(curso);
    	return ResponseEntity.ok(service.save(curso));
    }
    
    private ResponseEntity<Map<String,String>>validarErrores(BindingResult result){
    	Map<String,String> errores = new HashMap<>();
    	result.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
    	return ResponseEntity.badRequest().body(errores);
    }
    
    
    
    
    
}