package com.pm.bs.beans;

import java.util.List;

import lombok.Data;

@Data
public class TrackDetails {

	private OrderWrapper orderHeader;
	private List<Track> track;
}
