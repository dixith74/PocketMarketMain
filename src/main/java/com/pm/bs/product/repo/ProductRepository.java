package com.pm.bs.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pm.common.entities.PmProducts;

public interface ProductRepository extends CrudRepository<PmProducts, Long> {

	@Override
    List<PmProducts> findAll();
	
	@Query("select p from PmOrderProdcuts po inner join po.pmProducts p inner join po.pmOrders ord where ord.orderStatus ='OPEN' and ord.placedByCustmrId !=?1")
    List<PmProducts> getProductsOfOpenState(Long userId);
	
	@Query("select p from PmOrderProdcuts po inner join po.pmProducts p inner join po.pmOrders ord where ord.orderStatus ='OPEN' and p.location =:location"
			+ " and ord.placedByCustmrId !=:userId")
    List<PmProducts> getProductsOfOpenState(@Param("location") final String location, @Param("userId") Long userId);
}
