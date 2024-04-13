package com.alexpages.ordermanager.service;

import com.alexpages.ordermanager.api.domain.User;

public interface UserService 
{
	 
    public String addUser(User user);

	public void deleteUserById(Long userId); 

}