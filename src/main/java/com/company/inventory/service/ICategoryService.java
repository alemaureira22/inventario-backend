package com.company.inventory.service;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.category;
import com.company.inventory.response.CategoryResponseRest;

public interface ICategoryService {
	
	public ResponseEntity<CategoryResponseRest> search();
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
	public ResponseEntity<CategoryResponseRest> save(category category);
	public ResponseEntity<CategoryResponseRest> update(category category, Long id);
	public ResponseEntity<CategoryResponseRest> deleteById( Long id);
	
	

}
