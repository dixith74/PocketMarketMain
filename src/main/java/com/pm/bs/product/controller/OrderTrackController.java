package com.pm.bs.product.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pm.bs.beans.TrackDetails;
import com.pm.bs.order.service.OrderTrackerService;

@RestController
public class OrderTrackController {

	@Autowired
	private OrderTrackerService orderTrackerService;
	
	@PutMapping("/orders/{order_id}/status")
	public ResponseEntity<String> moveOrderStatus(@PathVariable("order_id") Long orderId, 
			@RequestBody() Map<String, String> track) {
		String trackId = orderTrackerService.updateOrderAndTracker(orderId, track);
		return ResponseEntity.ok(trackId);
	}
	
	@GetMapping("/track/{track_id}")
	public ResponseEntity<TrackDetails> getTrackDetails(@PathVariable("track_id") String trackid) {
		TrackDetails track = orderTrackerService.getDetails(trackid);
		return ResponseEntity.ok(track);
	}
}
