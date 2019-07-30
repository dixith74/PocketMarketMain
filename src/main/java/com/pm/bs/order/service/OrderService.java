package com.pm.bs.order.service;

import com.pm.common.entities.PmOrders;

public interface OrderService {

	PmOrders addOrder(PmOrders pmOrd);

	void getOrders();

	PmOrders getOrderById(long ordId);

	void updateOrder(PmOrders pmOrd);

	void deleteOrder(Long orderId);
}
