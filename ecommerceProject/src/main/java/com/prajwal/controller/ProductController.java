package com.prajwal.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prajwal.entity.Product;
import com.prajwal.service.ProductService;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@GetMapping(value= "/products",produces="application/json")
	public ResponseEntity<List<Product>> getAllProducts(){
		return new ResponseEntity<List<Product>>(service.getAll(),HttpStatus.OK);
	}
	
	@PostMapping
	public void addProduct(@RequestBody Product product) {
		service.addP(product);
	}
	
	 
	
	@GetMapping(value="/product/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id")int id) {
		Product product = service.getById(id);
		if(product != null) {
			return new ResponseEntity<Product>(service.getById(id),HttpStatus.OK);			
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// NOTE:- @RequestBody accepts the json as a whole object
	// but @RequestPart accepts the data in parts
	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile){
		try {
			Product product1 = service.addProduct(product,imageFile);
			return new ResponseEntity<>(product1,HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/product/{id}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable("id")int id){
		Product product = service.getById(id);
		byte[] imageFile = product.getImageData();
		// here we have to specify the content types also
		// and this is also the way in which we can send the ReponseEntity
		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
	}
	
	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProduct(@RequestPart Product product,@RequestPart MultipartFile imageFile,@PathVariable("id")int id) throws IOException{
		Product p = service.updateProduct(id,product,imageFile);
		if(p != null) {
			return new ResponseEntity<String>("Updated",HttpStatus.OK);
		}else {
			return ResponseEntity.badRequest().body("Failed to update");
		}
	}
	
	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable("id")int id){
		Product p = service.getById(id);
		if(p != null) {
			service.deleteProduct(id);
			return new ResponseEntity<String>("Deleted",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Product not found",HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/products/search")
	public ResponseEntity<List<Product>> searchP(@RequestParam String keyword){
		List<Product> lprod = service.searcProduct(keyword);
		return new ResponseEntity<List<Product>>(lprod,HttpStatus.OK);
	}
}
