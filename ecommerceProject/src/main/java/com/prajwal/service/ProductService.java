package com.prajwal.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.prajwal.entity.Product;
import com.prajwal.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository repo;

	public List<Product> getAll() {
		return repo.findAll();
		
	}
	
	public void addP(Product product) {
		repo.save(product);
	}

	public void deleteP(int id) {
		repo.deleteById(id);
	}
	
	public Product getById(int id) {
		return repo.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return repo.save(product);
	}
	
	public Product updateProduct(int id,Product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return repo.save(product);
	}

	public void deleteProduct(int id) {
		repo.deleteById(id);
	}

	public List<Product> searcProduct(String keyword) {
		return repo.searchProducts(keyword);
	}
	
	 
	
	 
}
