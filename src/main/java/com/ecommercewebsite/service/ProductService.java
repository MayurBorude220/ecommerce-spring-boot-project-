package com.ecommercewebsite.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.ecommercewebsite.entity.Product;
import com.ecommercewebsite.exception.ResourseNotFoundException;
import com.ecommercewebsite.model.Category;
import com.ecommercewebsite.payload.CategoryDto;
import com.ecommercewebsite.payload.ProductDto;
import com.ecommercewebsite.payload.ProductResponse;
import com.ecommercewebsite.repository.CategoryRepository;
import com.ecommercewebsite.repository.Productrepository;

@Service
public class ProductService {

	@Autowired
	private Productrepository productrepo;
	@Autowired
	private CategoryRepository categoryrepo;

	public ProductDto saveProduct(ProductDto productdto, int categoryId) {
		
		// fetch category is available
		Category cat = this.categoryrepo.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException("category ID is not found"));
		
		// productDto to product
		Product entity=toEntity(productdto);
		entity.setCategory(cat);
		
		// product save to database 
		Product pr = this.productrepo.save(entity);
		
	
		// product to productDto
		ProductDto dto = toDto(pr);
		return dto;
	}

	public ProductResponse viewAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
		
		Sort sort =null;
		if(sortDir.trim().toLowerCase().equals("asc")) {
			sort=sort.by(sortBy).ascending();
			System.out.println(sort);
		}else {
			sort=Sort.by(sortBy).descending();
			System.out.println(sort);
		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page = this.productrepo.findAll(pageable);
		List<Product> pageProduct = page.getContent();
		List<Product> collect1 = pageProduct.stream().filter(p ->p.isLive()).collect(Collectors.toList());
		
		// convert into dto
		List<ProductDto> pdto = collect1.stream().map(p->this.toDto(p)).collect(Collectors.toList());
		
		
		ProductResponse pr = new ProductResponse();
		pr.setContent(pdto);
		pr.setPageNumber(page.getNumber());
		pr.setPageSize(page.getSize());
		pr.setTotalPages(page.getTotalPages());
		pr.setLastPage(page.isLast());
		// productDto to product
//		List<Product> findall = productrepo.findAll();
//		List<ProductDto> findalldto = findall.stream().map(product->this.toDto(product)).collect(Collectors.toList());
		return pr;
	}

	public ProductDto viewProductById(int id) {
	 Product FindById = productrepo.findById(id).orElseThrow(()->new ResourseNotFoundException(+id+ "  Product Not Found to this id"));
	 ProductDto productdto = this.toDto(FindById);
		 return productdto;
		//Product product = product2.get();
		
	}
	public void deleteProductById(int id) {
		Product byid = productrepo.findById(id).orElseThrow(()->new ResourseNotFoundException(+id+ "  Product Not Found to this id"));
		productrepo.delete(byid);
	}
	
	public ProductDto updateProduct(int id, ProductDto newp)
	{
		Product oldp = productrepo.findById(id).orElseThrow(()->new ResourseNotFoundException(+id+"  Product Not Found"));
		oldp.setProduct_name(newp.getProduct_name());
		oldp.setProduct_imageName(newp.getProduct_imageName());
		oldp.setLive(newp.isLive());
		oldp.setProduct_desc(newp.getProduct_desc());
		oldp.setProduct_price(newp.getProduct_price());
		oldp.setProductQuantity(newp.getProductQuantity());
		oldp.setStock(newp.isStock());
		Product newpro = productrepo.save(oldp);
		ProductDto dto = toDto(newpro);
		return dto;
		}
	
	// find product by category
	
	public List<ProductDto> findProductByCategory(int categoryId)
	{
		Category findbyid = this.categoryrepo.findById(categoryId).orElseThrow(()-> new ResourseNotFoundException(categoryId + " this id not found"));
		List<Product> pro = this.productrepo.findByCategory(findbyid);
		List<ProductDto> productdto = pro.stream().map(product-> toDto(product)).collect(Collectors.toList());
		return productdto;
	}
		
	//conversion productdto to products
	public Product toEntity(ProductDto pdto)
	{
		Product prod = new Product();
		prod.setId(pdto.getId());
		prod.setProduct_name(pdto.getProduct_name());
		prod.setProduct_desc(pdto.getProduct_desc());
		prod.setProduct_imageName(pdto.getProduct_imageName());
		prod.setLive(pdto.isLive());
		prod.setProduct_price(pdto.getProduct_price());
		prod.setProductQuantity(pdto.getProductQuantity());
		prod.setStock(pdto.isStock());
		
		return prod;
	}
	
	
	// conversion product to productdto
	
	public ProductDto toDto(Product produ)
	{
		ProductDto pt = new ProductDto();
		pt.setId(produ.getId());
		pt.setProduct_name(produ.getProduct_name());
		pt.setProduct_desc(produ.getProduct_desc());
		pt.setProduct_imageName(produ.getProduct_imageName());
		pt.setLive(produ.isLive());
		pt.setProduct_price(produ.getProduct_price());
		pt.setProductQuantity(produ.getProductQuantity());
		pt.setStock(produ.isStock());
		
		// change category to categorydto
		CategoryDto catDto = new CategoryDto();
		catDto.setCategoryId(produ.getCategory().getCategoryId());
		catDto.setCategoryTitle(produ.getCategory().getCategoryTitle());
		
		// then set category dto into productDto
		pt.setCategorydto(catDto);
		return pt;
		
	}
}
