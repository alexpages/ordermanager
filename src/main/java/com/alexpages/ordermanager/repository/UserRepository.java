package com.alexpages.ordermanager.repository;

import org.springframework.stereotype.Repository;

import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.entity.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@Repository
public interface UserRepository 
extends CrudRepository<UserEntity, Long>, QueryByExampleExecutor<OrderEntity>,	PagingAndSortingRepository<UserEntity, Long> {

}
