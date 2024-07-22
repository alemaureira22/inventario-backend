package com.company.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.category;
import com.company.inventory.response.CategoryResponseRest;

import jdk.jfr.Category;

@Service
public class CategoryServiceImpl implements ICategoryService{
	
	@Autowired
	private ICategoryDao categoryDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
		
		CategoryResponseRest response = new CategoryResponseRest();
		
		try {
			List<category> category = (List<category>) categoryDao.findAll();
			
			response.getCategoryResponse().setCategory(category);
			response.setMetadata("Respuesta ok","00","Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta nok","-1","Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
      return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		
         CategoryResponseRest response = new CategoryResponseRest();
         List<category> list = new ArrayList<>();
		
		try {
			Optional<category> category = categoryDao.findById(id);
			
			if(category.isPresent()) {
				list.add(category.get());
	    		response.getCategoryResponse().setCategory(list);
	    		response.setMetadata("Respuesta ok","00","Categoria encontrada");
	    		
			}else {
				response.setMetadata("Respuesta nok","-1","Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta nok","-1","Error al consultar por id");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
      return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
    
	@Transactional
	@Override
	public ResponseEntity<CategoryResponseRest> save(category category) {
		 CategoryResponseRest response = new CategoryResponseRest();
         List<category> list = new ArrayList<>();
		
		try {
			
			category categorySaved = categoryDao.save(category);
			
			if(categorySaved != null) {
				list.add(categorySaved);
	    		response.getCategoryResponse().setCategory(list);
	    		response.setMetadata("Respuesta ok","00","Categoria guardada");
	    		
	    		
			}else {
				response.setMetadata("Respuesta nok","-1","Categoria no guardada");
				return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta nok","-1","Error al grabar categoria");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
      return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
	
	@Transactional
	@Override
	public ResponseEntity<CategoryResponseRest> update(category category, Long id) {
		 CategoryResponseRest response = new CategoryResponseRest();
         List<category> list = new ArrayList<>();
		
		try {
			 
			Optional<category> categorySearch = categoryDao.findById(id);
			if(categorySearch.isPresent()) {
			// se procedera a actualizar el registro
				categorySearch.get().setName(category.getName());
				categorySearch.get().setDescription(category.getDescription());
				
				category categoryToUpdate = categoryDao.save(categorySearch.get());
				
				if(categoryToUpdate != null) {
					list.add(categoryToUpdate);
		    		response.getCategoryResponse().setCategory(list);
		    		response.setMetadata("Respuesta ok","00","Categoria actualizada");
					
				}else {
					response.setMetadata("Respuesta nok","-1","Categoria no actualizada");
					return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.BAD_REQUEST);
				
			}
			}else {
				response.setMetadata("Respuesta nok","-1","Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
			
		} catch (Exception e) {
			response.setMetadata("Respuesta nok","-1","Error actualizar categoria");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
	}
      return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
	
	@Transactional
	@Override
	public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
          
		 CategoryResponseRest response = new CategoryResponseRest();
		
		try {

            categoryDao.deleteById(id);
			response.setMetadata("Respuesta ok","00","Registro eliminado");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta ok","00","Error al eliminar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		 return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}
}
	

