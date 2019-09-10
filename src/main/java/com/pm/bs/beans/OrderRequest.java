package com.pm.bs.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequest {
	
	private long itemId;
	private double totalAmt;
	private double tax;
	private int qty;
	private double discount;
	private double deliveryFee;
	boolean coupon = false;
	@JsonIgnore
	private String status;
	private String deliveryAddress;
	private Long userId;

}
