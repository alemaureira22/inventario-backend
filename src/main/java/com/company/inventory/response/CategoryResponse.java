package com.company.inventory.response;

import java.util.List;

import com.company.inventory.model.category;

import lombok.Data;

@Data
public class CategoryResponse {
	
	private List<category> category;

}
