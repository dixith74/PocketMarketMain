package com.pm.bs.product.repo;

import org.springframework.data.repository.CrudRepository;

import com.pm.common.entities.PmUsers;

public interface UserRepository extends CrudRepository<PmUsers, Long> {
}
