package com.pm.bs.product.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

import com.pm.bs.beans.Product;
import com.pm.bs.product.service.ProductService;
import com.pm.bs.product.service.StorageService;
import com.pm.common.beans.Address;

@RestController
public class ProductsController {

	@Autowired
	private ProductService productService;

	@Autowired
	private StorageService storageService;

	@Autowired
	@Qualifier("producttypes")
	private Map<String, String> prodMap;

	@GetMapping("/categories")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Map<Long, String>> getCatogiries() {
		return ResponseEntity.ok().body(productService.getCategories());
	}

	@GetMapping("/products/names")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Map<String, String>> getProdcutTypes() {
		return ResponseEntity.ok().body(prodMap);
	}

	@PostMapping("/products")
	public ResponseEntity<?> saveProduct(@RequestBody() Product product) {
		Long itemId = productService.addProduct(product);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(itemId).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/products/{prodId}")
	public Product getProduct(@PathVariable("prodId") long prodId) {
		return productService.getProduct(prodId);
	}

	@GetMapping("/products")
	public List<Product> getProducts(@RequestParam(value = "location", required = false) String location,
			@RequestParam(value = "user_id", required = false) Long userId) {
		return productService.getOpenOrders(location, userId);
	}

	@DeleteMapping("/products/{prodId}")
	public void delete(@PathVariable("prodId") long prodId) {
		productService.deleteProduct(prodId);
	}

	@PutMapping("/products/{prodId}")
	public void update(@PathVariable("prodId") long prodId, @RequestBody() Product product) {
		productService.updateProduct(product);
	}

	@PostMapping("/products/{productId}/upload")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
			@PathVariable("productId") Long productId) {
		productService.store(file, productId);
		// URI location =
		// ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand("122").toUri();
		// ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path("123").build().toUri());
		return ResponseEntity.ok().build();
	}

	@PostMapping("address")
	public void addDeliveryAddress(@RequestBody() Address adress) {
		productService.addAddress(adress);
	}

	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request,
			@RequestParam("userId") Long userId) {
		// Load file as Resource
		Resource resource = storageService.loadAsResource(fileName, userId);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			// logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@ExceptionHandler(StorageException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageException exc) {
		return ResponseEntity.notFound().build();
	}
}
