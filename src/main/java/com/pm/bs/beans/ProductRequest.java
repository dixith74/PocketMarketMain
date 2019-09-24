package com.pm.bs.beans;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductRequest {

	private long categoryId;
	private String categoryName;
	private long userId;
	private long configItemId;
	private String itemName;
	private String itemDesc;
	private String units;
	private Integer grade;
	private Double price;
	private Boolean availability;
	//private String location;
	private String image;
	private Integer qty;
	private Double rating;
	@JsonIgnore
	private Date createdTime;
	@JsonIgnore
	private Date updatedTime;
}