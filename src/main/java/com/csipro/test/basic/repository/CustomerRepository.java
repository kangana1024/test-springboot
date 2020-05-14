package com.csipro.test.basic.repository;

import com.csipro.test.basic.entity.CustomerEntity;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Integer> {
    public CustomerEntity getById(Integer id);
    public List<CustomerEntity> findAll();
}
