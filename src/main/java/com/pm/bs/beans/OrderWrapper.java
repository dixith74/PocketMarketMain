package com.pm.bs.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderWrapper {
	
		private long prductId;
		private String itemName;
		private String itemDesc;
		private String units;
		private Integer grade;
		private Double price;
		private String location;
		private String image;
		private Integer qty;
		private long orderId;
		private String categoryName;
		private long userId;
		private String orderStatus;
}
