package com.pm.bs.order.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public void getOrders() {
		orderRepository.findAll();
	}

	@Override
	public PmOrders getOrderById(long ordId) {
		return orderRepository.findById(ordId).orElse(new PmOrders());
	}

	@Override
	public void updateOrder(PmOrders pmOrd) {
		orderRepository.save(pmOrd);
	}

	@Override
	public void deleteOrder(Long orderId) {
		orderRepository.deleteById(orderId);
	}
}
