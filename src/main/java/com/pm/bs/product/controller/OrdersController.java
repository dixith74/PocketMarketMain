package com.pm.bs.product.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrdersController {

	
	@PostMapping("/orders")
	public void save(@RequestBody() String order) {
		
	}
	
	@GetMapping("/orders/{orderId}")
	public void getOrder(@PathVariable("orderId") String orderId) {
		
	}
	
	@GetMapping("/orders")
	public void getOrders(String orderId) {
		
	}
	
	@DeleteMapping("/orders/{orderId}")
	public void delete(@PathVariable("orderId") String orderId) {
		
	}
	
	@PutMapping("/orders/{orderId}")
	public void update(@PathVariable("orderId") String orderId, @RequestBody() String order) {
		
	}
}
