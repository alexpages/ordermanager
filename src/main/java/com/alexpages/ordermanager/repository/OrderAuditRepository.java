package com.alexpages.ordermanager.repository;

import org.springframework.stereotype.Repository;

import com.alexpages.ordermanager.entity.OrderAuditEntity;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

@Repository
public interface OrderAuditRepository 
extends PagingAndSortingRepository<OrderAuditEntity, Long>,
		CrudRepository<OrderAuditEntity, Long>,
		QueryByExampleExecutor<OrderAuditEntity> {

    @Query(value = "SELECT s FROM OrderAuditEntity s WHERE (:orderId IS NULL OR s.id = :orderId)" 
            + " AND (:action IS NULL OR UPPER(s.action) LIKE UPPER(CONCAT('%', :action, '%')))"
            + " AND (:startActionDate IS NULL OR s.actionDate >= :startActionDate)"
            + " AND (:endActionDate IS NULL OR s.actionDate < :endActionDate)"
            + " ORDER BY s.actionDate DESC")
    Page<OrderAuditEntity> filterByParams(
            @Param("orderId") Long orderId,
            @Param("action") String action,
            @Param("startActionDate") LocalDateTime startActionDate,    
            @Param("endActionDate") LocalDateTime endActionDate,
            Pageable pageable);


}
