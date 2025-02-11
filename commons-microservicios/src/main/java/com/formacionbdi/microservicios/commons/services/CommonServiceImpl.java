package com.formacionbdi.microservicios.commons.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.transaction.annotation.Transactional;




public class CommonServiceImpl<E, R  extends CustomPagingAndSortingRepository<E, Long>> implements CommonService<E> {
	
	
	@Autowired
	protected R  repository; 

	@Override
	@Transactional(readOnly = true)
	public Iterable<E> finAll() {
		return repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<E> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@Transactional
	public E save(E entity) {
		return repository.save(entity);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
	
		repository.deleteById(id);

	}

	@Override
	@Transactional(readOnly = true)
	public Page<E> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

}
