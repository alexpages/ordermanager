package com.alexpages.ordermanager.repository;

import org.springframework.stereotype.Repository;

import com.alexpages.ordermanager.entity.OrderEntity;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@Repository
public interface OrderRepository 
extends CrudRepository<OrderEntity, Long>,
		QueryByExampleExecutor<OrderEntity>,
		PagingAndSortingRepository<OrderEntity, Long> {

    @Query(value = "SELECT s FROM OrderEntity s WHERE (:orderId IS NULL OR s.id = :orderId)" 
            + " AND (:status IS NULL OR UPPER(s.status) LIKE UPPER(CONCAT('%', :status, '%')))"
            + " AND (:startCreationDate IS NULL OR s.creationDate >= :startCreationDate)"
            + " AND (:endCreationDate IS NULL OR s.creationDate < :endCreationDate)"
            + " ORDER BY s.creationDate DESC")
    Page<OrderEntity> filterByParams(
            @Param("orderId") Long orderId,
            @Param("status") String status,
            @Param("startCreationDate") LocalDateTime startCreationDate,    
            @Param("endCreationDate") LocalDateTime endCreationDate,
            Pageable pageable);
}
