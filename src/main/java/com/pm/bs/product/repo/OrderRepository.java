package com.pm.bs.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pm.bs.beans.OrderWrapper;
import com.pm.common.entities.PmOrderProdcuts;
import com.pm.common.entities.PmOrders;

public interface OrderRepository extends CrudRepository<PmOrders, Long> {
	
	@Query("select po from PmOrderProdcuts po where po.pmProducts.itemId =:prodId")
	PmOrderProdcuts getOrderByProductId(@Param("prodId") long prodId);
	
	@Query("select new com.pm.bs.beans.OrderWrapper(p.itemId, p.itemName, p.itemDesc, p.units, p.grade, p.price, p.location, p.imagePath, p.qty, "
			+ "ord.orderId, p.pmCategories.categoryName, ord.orderCmpltdByCustmrId, ord.orderStatus, ord.message) from PmOrderProdcuts po inner join po.pmProducts p inner join po.pmOrders ord "
			+ "where (ord.orderCmpltdByCustmrId =:userId OR ord.placedByCustmrId =:userId)")
	List<OrderWrapper> findOrdersShelf(@Param("userId") Long userId);
	
	@Query("select new com.pm.bs.beans.OrderWrapper(p.itemId, p.itemName, p.itemDesc, p.units, p.grade, p.price, p.location, p.imagePath, p.qty, "
			+ "ord.orderId, p.pmCategories.categoryName, ord.orderCmpltdByCustmrId, ord.orderStatus, ord.message) from PmOrderProdcuts po inner join po.pmProducts p inner join po.pmOrders ord "
			+ "where ord.orderCmpltdByCustmrId =:userId")
	List<OrderWrapper> findByOrderCmpltdByCustmrId(@Param("userId") Long userId);
	
	@Query("select new com.pm.bs.beans.OrderWrapper(p.itemId, p.itemName, p.itemDesc, p.units, p.grade, p.price, p.location, p.imagePath, p.qty, "
			+ "ord.orderId, p.pmCategories.categoryName, ord.orderCmpltdByCustmrId, ord.orderStatus, ord.message) from PmOrderProdcuts po inner join po.pmProducts p inner join po.pmOrders ord "
			+ "where ord.placedByCustmrId =:userId")
	List<OrderWrapper> findByOrderPlacedByCustmrId(@Param("userId") Long userId);

	@Query("select new com.pm.bs.beans.OrderWrapper(p.itemId, p.itemName, p.itemDesc, p.units, p.grade, p.price, p.location, p.imagePath, p.qty, "
			+ "ord.orderId, p.pmCategories.categoryName, ord.orderCmpltdByCustmrId, ord.orderStatus, ord.message) from PmOrderProdcuts po inner join po.pmProducts p inner join po.pmOrders ord "
			+ "where ord.orderId =:orderId")
	OrderWrapper getOrderByOrderId(@Param("orderId") Long orderId);
}
