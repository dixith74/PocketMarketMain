package com.pm.bs.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class ProductResponse {

	private long productId;
	private long categoryId;
	private String categoryName;
	private String userName;
	private long userId;
	private String itemName;
	private String itemDesc;
	private String units;
	private Integer grade;
	private Double price;
	private Boolean availability;
	private String location;
	private String image;
	private Integer qty;
	private Double rating;
	private long orderId;
	@JsonIgnore
	private Date createdTime;
	@JsonIgnore
	private Date updatedTime;
}