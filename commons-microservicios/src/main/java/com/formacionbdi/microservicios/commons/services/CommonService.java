package com.formacionbdi.microservicios.commons.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;




public interface CommonService<E> {
	
	public Iterable<E> finAll();
	
	
	
	public Optional<E> findById(Long id);
	
	public E save(E entity);
	
	public void deleteById(Long id);
	
	public Page<E> findAll(Pageable pageable);
	
	

}
