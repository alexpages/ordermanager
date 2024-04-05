package com.alexpages.ordermanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import org.hibernate.annotations.OptimisticLocking;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor 
@Validated
@OptimisticLocking
@Entity 
@Table(name = "orders")
public class OrderEntity 
implements Serializable 
{		
	private static final long serialVersionUID = 1L;
	
    @Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "DESCRIPTION")
	@NotNull
    private String description;
    
    @Column(name = "DISTANCE")
	@NotNull
    private int distance;
    
    @Column(name = "STATUS")
	@NotNull
    private String status;
    
    @Column(name = "CREATION_DATE")
	@NotNull
    private LocalDateTime creationDate;
	
    @Column(name = "VERSION")
    @Version
    private Long version;
}