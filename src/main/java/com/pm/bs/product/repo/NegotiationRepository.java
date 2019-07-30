package com.pm.bs.product.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.pm.common.entities.PmOrdNgtns;

public interface NegotiationRepository extends CrudRepository<PmOrdNgtns, Long> {

	@Override
    List<PmOrdNgtns> findAll();
}
