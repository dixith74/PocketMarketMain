package com.pm.bs.product.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pm.common.entities.PmCategories;

public interface CategoryRepository extends CrudRepository<PmCategories, Long> {

	@Override
    List<PmCategories> findAll();
}
