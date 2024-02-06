package com.alexpages.ordermanager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OptimisticLocking;
import org.springframework.validation.annotation.Validated;


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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	@NotNull
    private int distance;
    
	@NotNull
    private String status;
	
    @Version
    private Long version;
}