package com.alexpages.ordermanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.OptimisticLocking;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor 
@Validated @OptimisticLocking
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