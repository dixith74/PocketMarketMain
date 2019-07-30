package com.pm.bs.product.repo;

import org.springframework.data.repository.CrudRepository;

import com.pm.common.entities.PmOrderProdcuts;

public interface OrderProductsRepository extends CrudRepository<PmOrderProdcuts, Long> {

}
