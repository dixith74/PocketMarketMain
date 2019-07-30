package com.pm.bs.product.repo;

import org.springframework.data.repository.CrudRepository;

import com.pm.common.entities.PmOrders;

public interface OrderRepository extends CrudRepository<PmOrders, Long> {

}
