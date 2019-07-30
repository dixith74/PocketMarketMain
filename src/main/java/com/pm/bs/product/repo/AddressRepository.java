package com.pm.bs.product.repo;

import org.springframework.data.repository.CrudRepository;

import com.pm.common.entities.PmAddress;

public interface AddressRepository extends CrudRepository<PmAddress, Long> {
}
