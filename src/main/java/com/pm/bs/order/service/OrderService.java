package com.pm.bs.order.service;

import java.util.List;

import com.pm.bs.beans.OrderRequest;
import com.pm.bs.beans.OrderWrapper;
import com.pm.common.entities.PmOrders;

public interface OrderService {

	PmOrders addOrder(PmOrders pmOrd);

	List<OrderWrapper> getOrders(Long userId, String type);
	
	OrderWrapper getOrder(Long ordId);

	PmOrders getOrderById(long ordId);

	void purchaseOrder(OrderRequest order);

	void deleteOrder(Long orderId);

	String updateOrder(Long orderId, String message, String status);
}
