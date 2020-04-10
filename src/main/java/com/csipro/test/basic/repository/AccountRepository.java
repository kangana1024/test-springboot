package com.csipro.test.basic.repository;

import com.csipro.test.basic.entity.AccountEntity;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountRepository extends PagingAndSortingRepository<AccountEntity, Integer> {
    public AccountEntity getById(Integer id);
    public List<AccountEntity> findAll();
}
