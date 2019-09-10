package com.pm.bs.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.pm.bs.beans.Product;
import com.pm.common.beans.Address;

public interface ProductService {

	Long addProduct(Product product);

	List<Product> getProducts();
	
	List<Product> getOpenOrders(String location, Long userId);

	Product getProduct(long productId);

	void updateProduct(Product product);

	void deleteProduct(long product);

	void store(MultipartFile file, Long genId);

	void addAddress(Address adress);
	
	Map<Long, String> getCategories();

}
