package com.ecommercewebsite.payload;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommercewebsite.model.Category;

public class ProductDto {
	private int id;
	private String product_name;
	private double product_price;
	private boolean stock;
	private int productQuantity;
	private boolean live;
	private String product_imageName;
	private String product_desc;
	
	@Autowired
	private CategoryDto categorydto;

	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProductDto(int id, String product_name, double product_price, boolean stock, int productQuantity,
			boolean live, String product_imageName, String product_desc) {
		super();
		this.id = id;
		this.product_name = product_name;
		this.product_price = product_price;
		this.stock = stock;
		this.productQuantity = productQuantity;
		this.live = live;
		this.product_imageName = product_imageName;
		this.product_desc = product_desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public double getProduct_price() {
		return product_price;
	}

	public void setProduct_price(double product_price) {
		this.product_price = product_price;
	}

	public boolean isStock() {
		return stock;
	}

	public void setStock(boolean stock) {
		this.stock = stock;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public String getProduct_imageName() {
		return product_imageName;
	}

	public void setProduct_imageName(String product_imageName) {
		this.product_imageName = product_imageName;
	}

	public String getProduct_desc() {
		return product_desc;
	}

	public void setProduct_desc(String product_desc) {
		this.product_desc = product_desc;
	}

	public CategoryDto getCategorydto() {
		return categorydto;
	}

	public void setCategorydto(CategoryDto categorydto) {
		this.categorydto = categorydto;
	}

	

	

}
