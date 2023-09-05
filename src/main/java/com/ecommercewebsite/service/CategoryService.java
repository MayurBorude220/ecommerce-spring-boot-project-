package com.ecommercewebsite.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommercewebsite.exception.ResourseNotFoundException;
import com.ecommercewebsite.model.Category;
import com.ecommercewebsite.payload.CategoryDto;
import com.ecommercewebsite.repository.CategoryRepository;

@Service

public class CategoryService 
{
	@Autowired
	private CategoryRepository catrepo;
	
	@Autowired
	private ModelMapper mapper;
	
	
	public CategoryDto create(CategoryDto cdto) {
		// categoryDto to category
		Category map = this.mapper.map(cdto, Category.class);
		Category save = this.catrepo.save(map);
		return this.mapper.map(save, CategoryDto.class);
	}
	
	public CategoryDto updateCategory(CategoryDto cdto, int categoryId)
	{
		Category oldcat=this.catrepo.findById(categoryId).orElseThrow(()->new ResourseNotFoundException("this category Not found"));
		
		oldcat.setCategoryTitle(cdto.getCategoryTitle());
		Category save = this.catrepo.save(oldcat);
		return this.mapper.map(save, CategoryDto.class);
	}
	
	public void deleteCategory(int categoryId)
	{
		Category cat = this.catrepo.findById(categoryId).orElseThrow(()->new ResourseNotFoundException("this category Not found"));
	    this.catrepo.delete(cat);
	}
	public CategoryDto getCategoryById(int categoryId)
	{
		Category getbyid = this.catrepo.findById(categoryId).orElseThrow(()->new ResourseNotFoundException("this category Not found"));
		return this.mapper.map(getbyid, CategoryDto.class);
	}
	public List<CategoryDto> getCategoryAll()
	{
		List<Category> findAll = this.catrepo.findAll();
		List<CategoryDto> alldto = findAll.stream().map(cat-> this.mapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return alldto;
	}
}
