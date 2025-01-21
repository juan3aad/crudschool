package com.formacionbdi.microservicios.commons.examenes.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.formacionbdi.microservicios.commons.examenes.models.dto.ExamenDTO;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Examen;
import com.formacionbdi.microservicios.commons.examenes.models.entity.Pregunta;

@Mapper(componentModel = "spring") 
public interface ExamenMapper {
	
	// MapStruct generará la implementación de este método
    ExamenDTO toDTO(Examen examen);
    
    // MapStruct generará la implementación de este método
    Examen toEntity(ExamenDTO dto);
    
    
}