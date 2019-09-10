package com.pm.bs.beans;

import java.util.Date;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class Track {

	private String status;
	private String trackDesc;
	private String soruce;
	private String event;
	private Date updatedTime;
	private String trackId;
	
}
