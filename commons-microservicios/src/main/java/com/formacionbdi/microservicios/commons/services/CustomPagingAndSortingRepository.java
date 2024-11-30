package com.formacionbdi.microservicios.commons.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomPagingAndSortingRepository<T, ID> extends CrudRepository<T,ID>, PagingAndSortingRepository<T,ID>  {
	

}
