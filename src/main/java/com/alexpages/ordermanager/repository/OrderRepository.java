package com.alexpages.ordermanager.repository;

import org.springframework.stereotype.Repository;

import com.alexpages.ordermanager.entity.OrderEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@Repository
public interface OrderRepository 
extends CrudRepository<OrderEntity, Long>, QueryByExampleExecutor<OrderEntity>,	PagingAndSortingRepository<OrderEntity, Long> {

}
