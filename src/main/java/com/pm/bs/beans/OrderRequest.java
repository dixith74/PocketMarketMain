package com.pm.bs.beans;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderRequest {
	
	@NotNull
	private Long itemId;
	
	@NotNull
	private Double totalAmt;
	
	@NotNull
	private Integer qty;
	
	private Double tax;
	private Double discount;
	private Double deliveryFee;
	private boolean coupon = false;
	
	@NotNull
	private Long userId;
	
	@NotNull
	private Long deliveryAddrId;
	
	@JsonIgnore
	private String status;
	
	private String deliveryAddress;
}
