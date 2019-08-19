package com.pm.bs.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pm.bs.beans.OrderRequest;
import com.pm.bs.beans.OrderWrapper;
import com.pm.bs.order.service.OrderService;

@RestController
public class OrdersController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/orders")
	public void save(@RequestBody() String order) {
		
	}
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderWrapper> getOrder(@PathVariable("orderId") Long orderId) {
		//System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
		OrderWrapper order = orderService.getOrder(orderId);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderWrapper>> getOrders(@RequestParam("userId") Long userId) {
		List<OrderWrapper> orders = orderService.getOrders(userId);
		return ResponseEntity.ok(orders);
	}
	
	@DeleteMapping("/orders/{orderId}")
	public void delete(@PathVariable("orderId") String orderId) {
		
	}
	
	@PutMapping("/orders/{itemId}")
	public ResponseEntity<Void> updateOrderByprodcutId(@RequestBody() OrderRequest order) {
		orderService.updateOrder(order);
		return ResponseEntity.ok().build();
	}
}
