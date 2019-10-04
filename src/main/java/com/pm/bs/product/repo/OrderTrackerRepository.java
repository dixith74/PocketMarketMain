package com.pm.bs.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pm.bs.beans.OrderWrapper;
import com.pm.common.entities.PmOrderTracker;

public interface OrderTrackerRepository extends CrudRepository<PmOrderTracker, Long> {

	List<PmOrderTracker> findByTrackId(String trackid);
	
	@Query("select new com.pm.bs.beans.OrderWrapper(p.itemId, p.itemName, p.itemDesc, p.units, p.grade, p.price, p.location, p.imagePath, p.qty, "
			+ "ord.orderId, p.pmCategories.categoryName, ord.placedByCustmrId, ord.orderStatus, ord.message) from PmOrderProdcuts po inner join po.pmProducts p inner join po.pmOrders ord "
			+ "where ord.trackId =:trackId")
	OrderWrapper getOrderByTrackId(@Param("trackId") String trackId);
	
	@Query(value = "SELECT nextval('track_id_gen_seq')", nativeQuery = true)
	Long getNextSeriesId();
	
}
