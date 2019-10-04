package com.pm.bs.product.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.pm.bs.beans.ProductMaster;
import com.pm.bs.beans.ProductRequest;
import com.pm.bs.beans.ProductResponse;
import com.pm.common.beans.Address;

public interface ProductService {

	Long addProduct(ProductRequest product);

	List<ProductResponse> getProducts();
	
	List<ProductResponse> getOpenOrders(String location, Long userId);

	ProductResponse getProduct(long productId);

	void updateProduct(ProductRequest product);

	void deleteProduct(long product);

	void store(MultipartFile file, Long genId);
	
	Map<Long, String> getCategories();

	List<ProductMaster> getPredefinedProducts();

}
