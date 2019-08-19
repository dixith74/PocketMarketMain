package com.pm.bs.order.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pm.bs.beans.OrderRequest;
import com.pm.bs.beans.OrderWrapper;
import com.pm.bs.product.repo.OrderProductsRepository;
import com.pm.bs.product.repo.OrderRepository;
import com.pm.common.entities.PmOrderProdcuts;
import com.pm.common.entities.PmOrders;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductsRepository orderProductsRepository;

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
	public void updateOrder(OrderRequest order) {
		PmOrderProdcuts ord = orderRepository.getOrderByProductId(order.getItemId());
		ord.setQuantity(order.getQty());
		ord.setPrice(order.getTotalAmt());
		ord.setTax(order.getTax());
		ord.setDiscount(order.getDiscount());
		ord.setCoupon(order.isCoupon());
		ord.setDeliveryFee(order.getDeliveryFee());
		ord.setDeliveryAddress(order.getDeliveryAddress());
		ord.getPmOrders().setOrderStatus("Order is placed");
		ord.getPmOrders().setOrderCmpltdByCustmrId(2L);
		ord.getPmOrders().setUpdatedTime(new Date());
		orderProductsRepository.save(ord);
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
