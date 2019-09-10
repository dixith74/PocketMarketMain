package com.pm.bs.order.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.pm.bs.beans.Track;
import com.pm.bs.beans.TrackDetails;
import com.pm.bs.product.repo.OrderRepository;
import com.pm.bs.product.repo.OrderTrackerRepository;
import com.pm.common.entities.PmOrderTracker;
import com.pm.common.entities.PmOrders;
import com.pm.common.exception.BussinessExection;

@Service
public class OrderTrackerServiceImpl implements OrderTrackerService {

	private OrderTrackerRepository orderTrackerRepository;

	private OrderRepository orderRepository;

	public OrderTrackerServiceImpl(OrderTrackerRepository orderTrackerRepository, OrderRepository orderRepository) {
		this.orderTrackerRepository = orderTrackerRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public String updateOrderAndTracker(Long orderId, Map<String, String> track) {
		PmOrders pmOrder = orderRepository.findById(orderId).orElseThrow(() -> new BussinessExection("Order id not found", 404));
		if (StringUtils.equalsIgnoreCase(pmOrder.getOrderStatus(), track.get("status"))) {
			throw new BussinessExection("Order status is same");
		}
		String trackId = pmOrder.getTrackId();
		if (StringUtils.isNotEmpty(trackId)) {
			// create track id and set
			trackId = "PM-"+orderTrackerRepository.getNextSeriesId();
			pmOrder.setTrackId(trackId);
		}
		pmOrder.setOrderStatus(track.get("status"));
		pmOrder.setMessage(track.get("desc"));
		pmOrder.setUpdatedTime(new Date());
		orderRepository.save(pmOrder);
		
		// Insert row in Order Tracker
		PmOrderTracker ordTrckr = new PmOrderTracker();
		ordTrckr.setEvent(track.get("event"));
		ordTrckr.setSoruce(track.get("source"));
		ordTrckr.setTrackDesc(track.get("desc"));
		ordTrckr.setTrackId(pmOrder.getTrackId());
		ordTrckr.setStatus(track.get("status"));
		ordTrckr.setUpdatedTime(new Date());
		orderTrackerRepository.save(ordTrckr);
		return trackId;
	}

	@Override
	public TrackDetails getDetails(String trackId) {
		TrackDetails td = new TrackDetails();
		List<PmOrderTracker> ordTrackList = orderTrackerRepository.findByTrackId(trackId);
		td.setOrderHeader(orderTrackerRepository.getOrderByTrackId(trackId));
		td.setTrack(ordTrackList.stream().map(track -> getTrack(track)).collect(Collectors.toList()));
		return td;
	}

	private Track getTrack(PmOrderTracker track) {
		return Track.builder().soruce(track.getSoruce())
				.event(track.getEvent()).trackDesc(track.getTrackDesc())
				.status(track.getStatus()).trackId(track.getTrackId())
				.updatedTime(track.getUpdatedTime()).build();
	}
}
