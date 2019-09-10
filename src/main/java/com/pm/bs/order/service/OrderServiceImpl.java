package com.pm.bs.order.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.bs.beans.OrderRequest;
import com.pm.bs.beans.OrderWrapper;
import com.pm.bs.product.repo.OrderProductsRepository;
import com.pm.bs.product.repo.OrderRepository;
import com.pm.common.entities.PmOrderProdcuts;
import com.pm.common.entities.PmOrders;
import com.pm.common.exception.BussinessExection;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductsRepository orderProductsRepository;
	
	@Autowired
	private OrderTrackerService orderTrackerService;

	@Override
	public PmOrders addOrder(PmOrders pmOrd) {
		return orderRepository.save(pmOrd);
	}

	@Override
	public List<OrderWrapper> getOrders(Long userId, String type) {
		if (userId !=null) {
			if ("BUYER".equalsIgnoreCase(type)) {
				return orderRepository.findByOrderCmpltdByCustmrId(userId);
			} else {
				return orderRepository.findByOrderPlacedByCustmrId(userId);
			}
		}
		return orderRepository.findByOrderCmpltdByCustmrId(userId);
	}

	@Override
	public PmOrders getOrderById(long ordId) {
		return null;
	}

	@Override
	public void purchaseOrder(OrderRequest order) {
		PmOrderProdcuts ord = orderRepository.getOrderByProductId(order.getItemId());
		ord.setQuantity(order.getQty());
		ord.setPrice(order.getTotalAmt());
		ord.setTax(order.getTax());
		ord.setDiscount(order.getDiscount());
		ord.setCoupon(order.isCoupon());
		ord.setDeliveryFee(order.getDeliveryFee());
		ord.setDeliveryAddress(order.getDeliveryAddress());
		ord.getPmOrders().setOrderStatus(order.getStatus()!=null ? order.getStatus(): "Order has been placed");
		ord.getPmOrders().setMessage("Buyer is available");
		ord.getPmOrders().setOrderCmpltdByCustmrId(order.getUserId());
		ord.getPmOrders().setUpdatedTime(new Date());
		orderProductsRepository.save(ord);
		//makeTrackEntry(ord.getPmOrders().getOrderId());
	}
	
	@Override
	public String updateOrder(Long orderId, String message, String status) {
		/*
		PmOrders pmOrder = orderRepository.findById(orderId).orElseThrow(() -> new BussinessExection("Order id not found", 404));
		pmOrder.setOrderStatus(status);
		pmOrder.setMessage(message);
		pmOrder.setUpdatedTime(new Date());
		orderRepository.save(pmOrder);
		*/
		return pushToOrderTracker(orderId, message, status);
		/*
		if (!StringUtils.equalsIgnoreCase(pmOrder.getOrderStatus(), status)) {
			return makeTrackEntry(pmOrder.getOrderId(), message, status);
		}
		return "No Track id yet!";
		*/
	}

	private String pushToOrderTracker(long orderId, String message, String status) {
		Map<String, String> trackMap = new HashMap<>();
		trackMap.put("event", status);
		trackMap.put("source", "");
		trackMap.put("status", status);
		trackMap.put("desc", message);
		return orderTrackerService.updateOrderAndTracker(orderId, trackMap);
	}

	@Override
	public void deleteOrder(Long orderId) {
		orderRepository.deleteById(orderId);
	}

	@Override
	public OrderWrapper getOrder(Long ordId) {
		return orderRepository.getOrderByOrderId(ordId);
	}
}
