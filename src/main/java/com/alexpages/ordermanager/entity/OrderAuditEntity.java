package com.alexpages.ordermanager.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor 
@AllArgsConstructor 
@Entity 
@Table(name = "orders_audit")
public class OrderAuditEntity implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
    @Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
   
    @Column(name = "ORDER_ID")
	@NotNull
	private Long orderId;
	
    @Column(name = "ACTION")
	@NotNull
    private String action;
      
    @Column(name = "USERNAME")
	@NotNull
    private String username;
    
    @Column(name = "ACTION_DATE")
	@NotNull
    private LocalDateTime actionDate;


}