package com.pm.bs.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

import brave.propagation.TraceContext;

@RestController
public class OrdersController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/orders")
	public void save(@RequestBody() String order) {
		
	}
	
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<OrderWrapper> getOrder(@PathVariable("orderId") Long orderId, HttpServletRequest httpServletRequest) {
		//System.out.println(SecurityContextHolder.getContext().getAuthentication().toString());
		TraceContext context = (TraceContext) httpServletRequest.getAttribute(TraceContext.class.getName());
		System.out.println(context.traceIdString()+" "+context.parentId());
		OrderWrapper order = orderService.getOrder(orderId);
		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/orders")
	public ResponseEntity<List<OrderWrapper>> getOrders(@RequestParam(value = "userId", required = false) Long userId) {
		List<OrderWrapper> orders = orderService.getOrders(userId, null);
		return ResponseEntity.ok(orders);
	}
	
	@DeleteMapping("/orders/{orderId}")
	public void delete(@PathVariable("orderId") String orderId) {
		
	}
	
	@PutMapping("/orders")
	public ResponseEntity<Void> purchaseOrder(@RequestBody() OrderRequest order) {
		orderService.purchaseOrder(order);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/orders/{orderId}")
	public ResponseEntity<String> updateOrder(@PathVariable("orderId") Long ordId, @RequestParam("status") String status,
			@RequestParam("message") String message) {
		String resp = orderService.updateOrder(ordId, message, status);
		return ResponseEntity.ok(resp);
	}
}
