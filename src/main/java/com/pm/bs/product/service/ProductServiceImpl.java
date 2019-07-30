package com.pm.bs.product.service;

import java.nio.channels.IllegalSelectorException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pm.bs.beans.Product;
import com.pm.bs.beans.Product.ProductBuilder;
import com.pm.bs.order.service.OrderService;
import com.pm.bs.product.repo.AddressRepository;
import com.pm.bs.product.repo.CategoryRepository;
import com.pm.bs.product.repo.OrderProductsRepository;
import com.pm.bs.product.repo.ProductRepository;
import com.pm.bs.product.repo.UserRepository;
import com.pm.common.beans.Address;
import com.pm.common.entities.PmAddress;
import com.pm.common.entities.PmCategories;
import com.pm.common.entities.PmOrderProdcuts;
import com.pm.common.entities.PmOrders;
import com.pm.common.entities.PmProducts;
import com.pm.common.exception.BussinessExection;

@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderProductsRepository orderProductsRepository;

	@Autowired
	private AddressRepository addressRepository;

	private StorageService storageService;

	public ProductServiceImpl(StorageService storageService) {
		this.storageService = storageService;
	}

	@Override
	@Transactional
	public Long addProduct(Product product) {

		PmProducts pmPrd = new PmProducts();
		pmPrd.setItemName(product.getItemName());
		pmPrd.setPmCategories(
				categoryRepository.findById(product.getCategoryId()).orElseThrow(IllegalSelectorException::new));
		pmPrd.setCreatedTime(new Date());
		pmPrd.setUpdatedTime(new Date());
		pmPrd.setAvailability(true);
		pmPrd.setGrade(product.getGrade());
		pmPrd.setItemDesc(product.getItemDesc());
		pmPrd.setUnits(product.getUnits());
		pmPrd.setQty(product.getQty());
		pmPrd.setLocation(product.getLocation());
		pmPrd.setPrice(product.getPrice());
		pmPrd.setPmUsers(userRepository.findById(product.getUserId()).orElseThrow(IllegalSelectorException::new));
		pmPrd.setImagePath(product.getImage());
		pmPrd = productRepository.save(pmPrd);
		LOGGER.info("Product info after persist {}", pmPrd);
		makeOrder(pmPrd);
		return pmPrd.getItemId();
	}

	private void makeOrder(PmProducts pmPrd) {

		PmOrders pmOrd = new PmOrders();
		pmOrd.setOrderStatus("OPEN");
		pmOrd.setOrdTrckStts("NA");
		pmOrd.setPaymentStatus("NA");
		pmOrd.setPaymentType("NA");
		pmOrd.setPlacedByCustmrId(pmPrd.getPmUsers().getUserId());
		pmOrd.setPmUsers(pmPrd.getPmUsers());
		pmOrd.setTotalPrice(pmPrd.getPrice());
		pmOrd.setCreatedTime(new Date());
		pmOrd.setUpdatedTime(new Date());
		pmOrd = orderService.addOrder(pmOrd);
		LOGGER.info("Order info after persist {}", pmOrd);

		PmOrderProdcuts pmOrdPrdcts = new PmOrderProdcuts();
		pmOrdPrdcts.setPmOrders(pmOrd);
		pmOrdPrdcts.setPmProducts(pmPrd);
		pmOrdPrdcts.setPmUsers(pmPrd.getPmUsers());
		pmOrdPrdcts.setPrice(pmOrd.getTotalPrice());
		pmOrdPrdcts.setQuantity(pmPrd.getQty());
		orderProductsRepository.save(pmOrdPrdcts);
		LOGGER.info("Product Order info after persist {}", pmOrdPrdcts);
	}

	@Override
	public List<Product> getProducts() {
		return getOpenOrders(null);
		/*
		List<PmProducts> products = productRepository.getProductsOfOpenState();
		return products.stream().map((product) -> {
			ProductBuilder prod = Product.builder().prductId(product.getItemId()).itemName(product.getItemName())
					.itemDesc(product.getItemDesc()).grade(product.getGrade()).units(product.getUnits())
					.categoryId(product.getPmCategories().getCategoryId())
					.categoryName(product.getPmCategories().getCategoryName()).availability(product.getAvailability())
					.price(product.getPrice()).qty(product.getQty()).image(product.getImagePath())
					.location(product.getLocation()).userId(product.getPmUsers().getUserId())
					.userName(product.getPmUsers().getEmail()).rating(product.getPmUsers().getRating());
			return prod.build();
		}).collect(Collectors.toList());
		*/
	}

	@Override
	public Product getProduct(long productId) {
		Optional<PmProducts> findById = productRepository.findById(productId);
		ProductBuilder prod = Product.builder();
		findById.ifPresent((product) -> {
			prod.prductId(product.getItemId()).itemName(product.getItemName()).itemDesc(product.getItemDesc())
					.grade(product.getGrade()).units(product.getUnits())
					.categoryId(product.getPmCategories().getCategoryId())
					.categoryName(product.getPmCategories().getCategoryName()).availability(product.getAvailability())
					.price(product.getPrice()).qty(product.getQty()).image(product.getImagePath())
					.location(product.getLocation()).userId(product.getPmUsers().getUserId())
					.userName(product.getPmUsers().getEmail()).rating(product.getPmUsers().getRating());
		});
		return prod.build();
	}

	@Override
	public void updateProduct(Product product) {
		PmProducts pmPrd = productRepository.findById(product.getPrductId()).orElseThrow(RuntimeException::new);
		pmPrd.setItemName(product.getItemName());
		pmPrd.setPmCategories(null);
		pmPrd.setCreatedTime(new Date());
		pmPrd.setAvailability(true);
		pmPrd.setLocation("Mandya");
		pmPrd.setPrice(9468.56);
		pmPrd.setPmUsers(null);
		productRepository.save(pmPrd);
	}

	@Override
	public void deleteProduct(long productId) {
		PmOrderProdcuts ordPrdcts = orderProductsRepository.findById(productId)
				.orElseThrow(IllegalArgumentException::new);
		orderProductsRepository.delete(ordPrdcts);
		productRepository.deleteById(productId);
		orderService.deleteOrder(ordPrdcts.getPmOrders().getOrderId());
	}

	@Override
	public void store(MultipartFile file, Long userId, Long itemId) {
		String relPath = storageService.store(file, userId, itemId);
		PmProducts prdct = productRepository.findById(itemId).orElseThrow(IllegalArgumentException::new);
		prdct.setImagePath(relPath);
		productRepository.save(prdct);
	}

	@Override
	public void addAddress(Address address) {
		PmAddress pmAddress = PmAddress.builder()
				.pmUsers(userRepository.findById(address.getUserId())
						.orElseThrow(() -> new BussinessExection("User not found")))
				.firstName(address.getFirstName()).lastName(address.getLastName()).city(address.getCity())
				.state(address.getState()).street(address.getStreet()).addressOne(address.getAddressOne())
				.addressTwo(address.getAddressTwo()).pincode(address.getPincode()).canDeliver(address.isDeliverByMe())
				.build();
		addressRepository.save(pmAddress);
	}

	@Override
	public Map<Long, String> getCategories() {
		return categoryRepository.findAll().stream().collect(Collectors.toMap(PmCategories::getCategoryId, PmCategories::getCategoryName));
	}

	@Override
	public List<Product> getOpenOrders(String location) {
		List<PmProducts> products = null;
		if (location !=null) {
			products = productRepository.getProductsOfOpenState(location);
		} else {
			products = productRepository.getProductsOfOpenState();
		}
		return products.stream().map((product) -> {
			ProductBuilder prod = Product.builder().prductId(product.getItemId()).itemName(product.getItemName())
					.itemDesc(product.getItemDesc()).grade(product.getGrade()).units(product.getUnits())
					.categoryId(product.getPmCategories().getCategoryId())
					.categoryName(product.getPmCategories().getCategoryName()).availability(product.getAvailability())
					.price(product.getPrice()).qty(product.getQty()).image(product.getImagePath())
					.location(product.getLocation()).userId(product.getPmUsers().getUserId())
					.userName(product.getPmUsers().getEmail()).rating(product.getPmUsers().getRating());
			return prod.build();
		}).collect(Collectors.toList());
	}
}
