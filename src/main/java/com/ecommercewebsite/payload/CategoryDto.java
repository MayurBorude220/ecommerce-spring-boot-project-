package com.ecommercewebsite.payload;

public class CategoryDto {
	public int categoryId;
	public String categoryTitle;

	public CategoryDto() {
		super();

	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

}
