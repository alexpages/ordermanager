package com.alexpages.ordermanager.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.jeasy.random.EasyRandom;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alexpages.ordermanager.api.domain.User;
import com.alexpages.ordermanager.api.domain.UserOuputData;
import com.alexpages.ordermanager.service.impl.JwtServiceImpl;
import com.alexpages.ordermanager.service.impl.UserServiceImpl;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	@Mock
	private UserServiceImpl userService;
	@Mock
	private JwtServiceImpl jwtService; 
	@Mock
    private AuthenticationManager authenticationManager; 
    
    private EasyRandom easyRandom;
    private static MockMvc mockMvc;
	
    public HttpHeaders header()
    {
        return new HttpHeaders();
    }
    
    @BeforeEach
    public void setUp() 
    {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, jwtService, authenticationManager)).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
        easyRandom = new EasyRandom();
    }
    
	@Test
	void testAddUser_success() 
	throws JSONException, Exception 
	{	
    	when(userService.addUser(any())).thenReturn("any String"); 
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidUserJson()))
        .andExpect(status().is(HttpStatus.CREATED.value()));
	}
	
    @Test
    void testAuthenticateUser_success() 
    throws Exception 
    {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(jwtService.generateToken(any())).thenReturn("token");
        mockMvc.perform(post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateValidAuthenticateRequest()))
            .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void testAuthenticateUser_error() 
	throws Exception 
    {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(false);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        mockMvc.perform(post("/users/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(generateValidAuthenticateRequest()))
        .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()));
    }
    
    @Test
    void testDeleteUser_success() 
	throws Exception 
    {
    	doNothing().when(userService).deleteUserById(any());
        mockMvc.perform(delete("/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }	
    
    
    @Test
    void testGetUserById_success() 
	throws Exception 
    {
    	when(userService.getUserById(any())).thenReturn(new User());
        mockMvc.perform(get("/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().is(HttpStatus.OK.value()));
    }	
    
    @Test
    void testGetUserById_error() 
	throws Exception 
    {
    	when(userService.getUserById(any())).thenReturn(null);
        mockMvc.perform(get("/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().is(HttpStatus.NOT_FOUND.value()));
    }	
    
    @Test
    void testGetUsers_200()
	throws Exception 
    {
    	when(userService.getUsers(any())).thenReturn(generateUserOutputData());
        mockMvc.perform(post("/users/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    void testGetUsers_204()
	throws Exception 
    {
    	when(userService.getUsers(any())).thenReturn(new UserOuputData());
        mockMvc.perform(post("/users/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
        .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }	
	
    private UserOuputData generateUserOutputData() 
    {
    	UserOuputData output = new UserOuputData();
    	output.addUsersItem(easyRandom.nextObject(User.class));
    	return output;
    }
    
	private String generateValidAuthenticateRequest() 
	throws JSONException 
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", "name");
		jsonObject.put("password", "password");
		return jsonObject.toString();
	}

	private String generateValidUserJson() 
	throws JSONException 
	{
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("username", "name");
		jsonObject.put("password", "password");
		jsonObject.put("role", "ADMIN");
		return jsonObject.toString();
	}

}
