package com.ecommercewebsite.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommercewebsite.entity.Product;
import com.ecommercewebsite.model.Category;

@Repository
public interface Productrepository extends JpaRepository<Product, Integer> {

	List<Product> findByCategory(Category category);

}
