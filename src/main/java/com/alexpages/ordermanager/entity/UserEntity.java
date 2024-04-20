package com.alexpages.ordermanager.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

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
@Table(name = "users")
public class UserEntity 
implements Serializable 
{		
	private static final long serialVersionUID = 1L;
	
    @Column(name = "ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
    @Column(name = "USERNAME")
	@NotNull
    private String username;
    
    @Column(name = "PASSWORD")
	@NotNull
    private String password;
	
    @Column(name = "ROLE")
	@NotNull
    private String role;
	
}