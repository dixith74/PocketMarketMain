package com.pm.bs.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.pm.bs.beans.ProductMaster;
import com.pm.common.entities.PmProductMaster;

public interface ProductMasterRepository extends CrudRepository<PmProductMaster, Long> {

	@Override
    List<PmProductMaster> findAll();
	
	@Query("select new com.pm.bs.beans.ProductMaster(prdMstr.configId, prdMstr.configName,prdMstr.configDesc, prdMstr.availability, prdMstr.image, "
			+ "pc.categoryId, pc.categoryName) from PmProductMaster prdMstr inner join prdMstr.pmCategory pc where prdMstr.availability = :availability")
	List<ProductMaster> getPredefinedProducts(@Param("availability") Boolean availability);
}
