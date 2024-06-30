package com.alexpages.ordermanager.service;

import java.util.Optional;

import javax.validation.Valid;

import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.api.domain.UserInputData;
import com.alexpages.ordermanager.api.domain.UserOuputData;
import com.alexpages.ordermanager.entity.UserEntity;

public interface UserService {
	
    public String addUser(User user);

	public void deleteUserById(Long userId);

	public User getUserById(Long userId);

	public UserOuputData getUsers(@Valid UserInputData userInputData);

	public Optional<UserEntity> findUserByUsername(String username);

	public UserEntity saveUser(UserEntity userEntity); 

}