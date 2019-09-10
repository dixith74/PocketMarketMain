package com.pm.bs.order.service;

import java.util.Map;

import com.pm.bs.beans.TrackDetails;

public interface OrderTrackerService {

	String updateOrderAndTracker(Long orderId, Map<String, String> track);

	TrackDetails getDetails(String trackid);
}
