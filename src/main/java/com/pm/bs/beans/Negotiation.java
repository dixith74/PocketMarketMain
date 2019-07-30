package com.pm.bs.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Negotiation {
	private long ngtnId;
	private long orderId;
	private long prodId;
	private long ngtnByCstrId;
	private long orderPlacedCstrId;
	private Double amount;
	private String orderCurrency;
}
