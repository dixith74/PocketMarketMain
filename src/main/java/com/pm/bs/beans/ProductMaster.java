package com.pm.bs.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMaster {
	
	private Long configId;
	private String configName;
	private String configDesc;
	private Boolean availability;
	private String image;
	private Long categoryId;
	private String categoryName;

}
