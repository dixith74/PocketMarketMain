package com.pm.bs.product.controller;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pm.bs.beans.ProductMaster;
import com.pm.bs.beans.ProductRequest;
import com.pm.bs.beans.ProductResponse;
import com.pm.bs.product.service.ProductService;
import com.pm.common.exception.BussinessExection;

@RestController
public class ProductsController {

	@Autowired
	private ProductService productService;

	@GetMapping("/categories")
	@ResponseStatus(code = HttpStatus.OK)
	public Map<Long, String> getCatogiries() {
		return productService.getCategories();
	}

	@GetMapping("/products/master")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProductMaster> getProdcutTypes() {
		return productService.getPredefinedProducts();
	}

	@PostMapping("/products")
	public ResponseEntity<?> saveProduct(@RequestBody() @Valid ProductRequest product, BindingResult result) {
		if (result.hasErrors()) {
			throw new BussinessExection(result.toString(), 400);
		}
		Long itemId = productService.addProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(itemId).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/products/{prodId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ProductResponse getProduct(@PathVariable("prodId") long prodId) {
		return productService.getProduct(prodId);
	}

	@GetMapping("/products")
	@ResponseStatus(code = HttpStatus.OK)
	public List<ProductResponse> getProducts(@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "user_id", required = false) Long userId) {
		return productService.getOpenOrders(location, userId);
	}

	@DeleteMapping("/products/{prodId}")
	@ResponseStatus(code = HttpStatus.OK)
	public void delete(@PathVariable("prodId") long prodId) {
		productService.deleteProduct(prodId);
	}

	@PutMapping("/products/{prodId}")
	@ResponseStatus(code = HttpStatus.OK)
	public void update(@PathVariable("prodId") long prodId, @RequestBody() ProductRequest product) {
		productService.updateProduct(product);
	}

	@PostMapping("/products/{productId}/upload")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<?> uploadProductImage(@RequestParam("file") MultipartFile file,
			@PathVariable("productId") Long productId) {
		productService.store(file, productId);
		// URI location =
		// ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("122").toUri();
		// ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path("123").build().toUri());
		return ResponseEntity.ok().build();
	}

	/*
	@PostMapping("sendSms")
	public ResponseEntity<String> sendRequest(@RequestParam("apiKey") String apiKey,@RequestParam("message") String message,
			@RequestParam("numbers") String numbers,@RequestParam("sender") String sender) {
		System.out.println(apiKey+" "+message+" "+numbers+" "+sender);
		return ResponseEntity.ok("{status:success}");
	}*/
}
