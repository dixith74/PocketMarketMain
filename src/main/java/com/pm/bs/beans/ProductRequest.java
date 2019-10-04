package com.pm.bs.beans;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	
	@Min(value = 1, message = "userId property is mandatory or should be more than 0")
	@NotNull
	private Long userId;
	
	@Min(value = 1, message = "configItemId property is mandatory or should be more than 0")
	@NotNull
	private Long configItemId;
	private String itemName;
	private String itemDesc;
	private String units;
	private Integer grade;
	private Double price;
	private Boolean availability;
	//private String location;
	private String image;
	
	@Min(value = 1, message = "qty property is mandatory or should be more than 0")
	@NotNull
	private Integer qty;
	private Double rating;
	@JsonIgnore
	private Date createdTime;
	@JsonIgnore
	private Date updatedTime;
}