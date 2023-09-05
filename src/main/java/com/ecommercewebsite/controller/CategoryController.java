package com.ecommercewebsite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercewebsite.model.Category;
import com.ecommercewebsite.payload.ApiResponse;
import com.ecommercewebsite.payload.CategoryDto;
import com.ecommercewebsite.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	@Autowired
	private CategoryService categoryservice;
	
	// create category 
	@PostMapping("/save")
	public ResponseEntity<CategoryDto>create(@RequestBody CategoryDto cdto)
	{
		CategoryDto create = this.categoryservice.create(cdto);
		return new ResponseEntity<CategoryDto>(create, HttpStatus.ACCEPTED);
	}
	
	
	// update category
	
	@PutMapping("update/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto cdto, @PathVariable int categoryId)
	{
		CategoryDto update = this.categoryservice.updateCategory(cdto, categoryId);
		return new ResponseEntity<CategoryDto>(update, HttpStatus.OK);
	}
	
	// delete category
	@DeleteMapping("delete/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable int categoryId)
	{
		this.categoryservice.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted", true),HttpStatus.OK);
	}
	
	// get category by id
	@GetMapping("/getbyid")
	public ResponseEntity<CategoryDto> getById(@RequestParam int CategoryId)
	{
		CategoryDto categoryById = this.categoryservice.getCategoryById(CategoryId);
		return new ResponseEntity<CategoryDto>(categoryById, HttpStatus.OK);
		
	}
	
	// get category All
	@GetMapping("/getAll")
	public ResponseEntity<List<CategoryDto>> getAll()
	{
		List<CategoryDto> all = this.categoryservice.getCategoryAll();
		return new ResponseEntity<List<CategoryDto>>(all, HttpStatus.OK);
	}
}
