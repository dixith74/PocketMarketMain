package com.pm.bs.product.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pm.bs.beans.Negotiation;
import com.pm.bs.order.service.NegotiationrService;

@RestController
public class NegotiationController {
	
	private NegotiationrService orderNegotiationrService;
	
	public NegotiationController(NegotiationrService orderNegotiationrService) {
		this.orderNegotiationrService = orderNegotiationrService;
	}

	@PostMapping("/negotiate")
	public ResponseEntity<String> negotiate(@RequestBody() Negotiation prdNgtn) {
		orderNegotiationrService.negotiate(prdNgtn);
		return ResponseEntity.ok().header("ng_id", "123").body("Negotiation sent");
	}
}
