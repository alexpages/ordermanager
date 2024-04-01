package com.alexpages.ordermanager.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import org.springframework.validation.annotation.Validated;

import com.alexpages.ordermanager.domain.User;

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
    
    @Column(name = "NAME")
	@NotNull
    private String name;
    
    @Column(name = "PASSWORD")
	@NotNull
    private String password;
	
    @Column(name = "ROLE")
	@NotNull
    private String role;
	
}