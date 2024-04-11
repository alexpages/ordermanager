package com.alexpages.ordermanager.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.entity.UserEntity;
import com.alexpages.ordermanager.mapper.OrderManagerMapper;
import com.alexpages.ordermanager.repository.UserRepository;
import com.alexpages.ordermanager.security.UserInfoDetails;
import com.alexpages.ordermanager.service.UserService;

@Service
public class UserServiceImpl implements UserDetailsService, UserService { 
  
	@Autowired
    private UserRepository repository; 
	@Autowired
    private PasswordEncoder encoder; 
	@Autowired
    private OrderManagerMapper mapper;
  
    @Override
    public UserDetails loadUserByUsername(String username) 
	throws UsernameNotFoundException 
    { 
  
        Optional<UserEntity> userDetail = repository.findByUsername(username); 
        return userDetail.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
    
    @Override
    public String addUser(User user) {
    	
    	user.setPassword(encoder.encode(user.getPassword())); 
        repository.save(mapper.toUserEntity(user));
        return "User Added Successfully"; 
    } 

}