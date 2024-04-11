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
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderManagerMapper;
import com.alexpages.ordermanager.repository.UserRepository;
import com.alexpages.ordermanager.security.UserInfoDetails;
import com.alexpages.ordermanager.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    public String addUser(User user) 
    {
    	try {
        	user.setPassword(encoder.encode(user.getPassword())); 
            UserEntity savedUser = repository.save(mapper.toUserEntity(user));
            return "User with ID: [" + savedUser.getId() + "] has been added successfully"; 

    	} catch(Exception e) {
    		log.error("UserServiceImpl > addUser > There was an error saving the user");
    		throw new OrderManagerException500("UserServiceImpl > addUser > There was an error saving the user", e.getCause());
    	}

    } 

}