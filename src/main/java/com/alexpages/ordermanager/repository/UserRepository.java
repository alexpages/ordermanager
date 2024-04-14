package com.alexpages.ordermanager.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.alexpages.ordermanager.entity.OrderEntity;
import com.alexpages.ordermanager.entity.UserEntity;

@Repository
public interface UserRepository 
extends CrudRepository<UserEntity, Long>, 
		QueryByExampleExecutor<OrderEntity>,	
		PagingAndSortingRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);
	
    @Query(value = "SELECT s FROM UserEntity s WHERE (:userId IS NULL OR s.id = :userId)" 
            + " AND (:username IS NULL OR UPPER(s.username) LIKE UPPER(CONCAT('%', :username, '%')))"
            + " AND (:role IS NULL OR UPPER(s.role) LIKE UPPER(CONCAT('%', :role, '%')))"
            + " ORDER BY s.id DESC")
    Page<UserEntity> filterByParams(
            @Param("userId") 	Long userId,
            @Param("username") 	String username,
            @Param("role") 	String role,
            Pageable pageable);
}