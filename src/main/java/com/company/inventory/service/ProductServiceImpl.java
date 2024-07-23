package com.company.inventory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.dao.IProductDao;
import com.company.inventory.model.Product;
import com.company.inventory.model.category;
import com.company.inventory.response.ProductResponseRest;

@Service
public class ProductServiceImpl implements IProductService {
	
	private ICategoryDao categoryDao;
	private IProductDao productDao;

	public ProductServiceImpl(ICategoryDao categoryDao,IProductDao productDao) {
		super();
		this.categoryDao = categoryDao;
		this.productDao = productDao;
	}
    
	@Override
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			// search category to set in the product object
		
			Optional<category> category = categoryDao.findById(categoryId);
			
			if(category.isPresent()) {
				product.setCategory(category.get());
				
			}else {
				response.setMetadata("respuesta nok","-1","categoria no encontrada");
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.NOT_FOUND);
			}
			
			// save the product
			Product productSaved = productDao.save(product);
			
			if(productSaved != null) {
				list.add(productSaved);
				response.getProduct().setProducts(list);
				response.setMetadata("respuesta ok","00","Producto guardado");
			}else {
				response.setMetadata("respuesta nok","-1","Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response,HttpStatus.BAD_REQUEST);		
			}
					
		}catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("respuesta nok","-1","Error al guardar producto");
			return new ResponseEntity<ProductResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response,HttpStatus.OK);
	}
}
		
	


