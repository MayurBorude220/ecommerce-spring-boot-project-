package com.ecommercewebsite.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommercewebsite.entity.Product;
import com.ecommercewebsite.payload.AppConstants;
import com.ecommercewebsite.payload.ProductDto;
import com.ecommercewebsite.payload.ProductResponse;
import com.ecommercewebsite.service.ProductService;


import jakarta.websocket.server.PathParam;

@RestController

public class ProductController {

	@Autowired
	private ProductService productservice;

	// to save product
	@PostMapping("/save/{categoryId}")
	public ProductDto saveProduct(@RequestBody ProductDto productdto, @PathVariable int categoryId) {
		ProductDto p2 = productservice.saveProduct(productdto, categoryId);
		return p2;
	}

	// all products details view
	@GetMapping("/viewall")
	public ProductResponse viewAllProduct(@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_STRING,required = false)int pageNumber, 
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false)String sortDir) {
		
		
		    ProductResponse viewAll = productservice.viewAllProduct(pageNumber, pageSize, sortBy, sortDir);
		
		return viewAll;
	}

	// view product by id
	@GetMapping("view/{id}")
	public ProductDto viewProductById(@PathVariable int id) {
		ProductDto viewid = productservice.viewProductById(id);
		return viewid;
	}

	// delete product by id 
	@DeleteMapping("delete/{id}")
	public String deleteProduct(@PathVariable int id) {
		productservice.deleteProductById(id);
		return "product is deleted";
	}
	
	// update product by id
	@PutMapping("update/{id}")
	public ProductDto updateProduct(@PathVariable int id, @RequestBody ProductDto newp)
	{
		ProductDto updateProduct = productservice.updateProduct(id, newp);
		return updateProduct;
	}
	
	
	// find product by  category  wise
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<ProductDto>> getProductByCategory(@PathVariable int categoryId)
	{
		List<ProductDto> product = this.productservice.findProductByCategory(categoryId);
		return new ResponseEntity<List<ProductDto>> (product, HttpStatus.ACCEPTED);
	}
}