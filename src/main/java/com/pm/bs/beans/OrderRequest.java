package com.pm.bs.beans;

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
	private String deliveryAddress;

}
