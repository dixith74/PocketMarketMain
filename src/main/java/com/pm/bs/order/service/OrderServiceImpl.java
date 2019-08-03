package com.pm.bs.order.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.bs.beans.OrderWrapper;
import com.pm.bs.product.repo.OrderRepository;
import com.pm.common.entities.PmOrders;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public PmOrders addOrder(PmOrders pmOrd) {
		return orderRepository.save(pmOrd);
	}

	@Override
	public List<OrderWrapper> getOrders(Long userId) {
		if (userId !=null) {
			return orderRepository.findByOrderCmpltdByCustmrId(userId);
		} else {
			orderRepository.findAll();
		}
		return Collections.emptyList();
	}

	@Override
	public PmOrders getOrderById(long ordId) {
		return null;
	}

	@Override
	public void updateOrder(long prodId) {
		PmOrders ord = orderRepository.getOrderByProductId(prodId);
		ord.setOrderStatus("PLACED");
		ord.setOrderCmpltdByCustmrId(2L);
		ord.setUpdatedTime(new Date());
		orderRepository.save(ord);
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
