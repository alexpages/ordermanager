package com.alexpages.ordermanager.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.api.domain.UserInputData;
import com.alexpages.ordermanager.api.domain.UserInputDataInputSearch;
import com.alexpages.ordermanager.api.domain.UserOuputData;
import com.alexpages.ordermanager.entity.UserEntity;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.error.OrderManagerException409;
import com.alexpages.ordermanager.error.OrderManagerException500;
import com.alexpages.ordermanager.mapper.OrderManagerMapper;
import com.alexpages.ordermanager.repository.UserRepository;
import com.alexpages.ordermanager.security.UserInfoDetails;
import com.alexpages.ordermanager.service.UserService;
import com.alexpages.ordermanager.utils.PageableUtils;

import javax.validation.Valid;
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
  
	@Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) 
	throws UsernameNotFoundException 
    { 
  
        Optional<UserEntity> userDetail = repository.findByUsername(username); 
        return userDetail.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
    
    @Transactional
    @Override
    public String addUser(User user) 
    {
    	Optional<UserEntity> oUserEntity = repository.findByUsername(user.getUsername());
    	if (oUserEntity.isPresent()) {
    		log.error("UserServiceImpl > addUser > User with username: [" + user.getUsername()+ "] already exists");
    		throw new OrderManagerException409("User with username: [" + user.getUsername()+ "] already exists");	    	
    	} else {
    		user.setPassword(encoder.encode(user.getPassword())); 
            UserEntity savedUser = repository.save(mapper.toUserEntity(user));
            return "User with ID: [" + savedUser.getId() + "] has been added successfully"; 
    	}    	
    }

    @Transactional
    @Override
	public void deleteUserById(Long userId) 
    {
	    Optional<UserEntity> deletedEntity = repository.findById(userId);
	    if (!deletedEntity.isPresent()) 
	    {
	        throw new OrderManagerException404("User with id: [" + userId + "] was not found");		    	
	    } else {
		    repository.deleteById(userId);
	    }		
	}
    
	@Transactional(readOnly = true)
    @Override
	public User getUserById(Long userId) 
    {
	    Optional<UserEntity> userEntity = repository.findById(userId);
	    if (userEntity.isPresent()) 
	    {
	        return mapper.toUser(userEntity.get());		    	
	    } else {
		    return null;
	    }		
	}

	@Transactional(readOnly = true)
	@Override
	public UserOuputData getUsers(@Valid UserInputData userInputData) 
	{
		final String LOG_PREFIX = "UserServiceImpl > getUsers > ";
		try {
			Pageable pageable = PageableUtils.getPageable(userInputData.getPaginationBody());
			UserInputDataInputSearch inputSearch;
			String role = null;
			
			if(userInputData.getInputSearch() != null) {
				inputSearch = userInputData.getInputSearch();
				if (inputSearch.getRole() != null) {
					role = inputSearch.getRole().getValue();
				}
			} else {
				inputSearch = new UserInputDataInputSearch();	// Object with nulls
			}
			log.info(LOG_PREFIX + "Users: {}", userInputData.toString());
			Page<UserEntity> pageUserEntity = repository.filterByParams(
					inputSearch.getUserId(),
					inputSearch.getUsername(),
					role,
					pageable);

		    log.info(LOG_PREFIX + "pageUserEntity: {}", pageUserEntity.getContent());
		    UserOuputData response = new UserOuputData();
			response.setUsers(mapper.toUserList(pageUserEntity.getContent()));
			response.setPageResponse(PageableUtils.getPaginationResponse(pageUserEntity, pageUserEntity.getPageable()));
			return response;
			
		} catch (Exception e) {
			log.error(LOG_PREFIX + "It could not get the list of users: [" + e.getMessage() + "]");
			throw new OrderManagerException500(LOG_PREFIX + "User list could not get retrieved, Exception: [" + e.getMessage() + "]");
		}
	} 

}