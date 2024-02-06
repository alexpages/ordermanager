package com.alexpages.ordermanager.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

import com.alexpages.ordermanager.domain.OrderDTO;
import com.alexpages.ordermanager.domain.OrderListResponse;
import com.alexpages.ordermanager.domain.PlaceOrderResponse;
import com.alexpages.ordermanager.domain.TakeOrderResponse;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.service.impl.OrderServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
	
	@MockBean
	private OrderServiceImpl orderServiceImpl;
    private EasyRandom easyRandom;
    private static MockMvc mockMvc;
    
    public HttpHeaders header()
    {
        return new HttpHeaders();
    }
   
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderServiceImpl)).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
        easyRandom = new EasyRandom();
    }
    
    @Test
    void testPlaceOrderSuccess_200() throws Exception 
    {
    	when(orderServiceImpl.placeOrder(any())).thenReturn(easyRandom.nextObject(PlaceOrderResponse.class)); 	
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidPlaceOrderRequestContent()))
        .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    void testTakeOrderSuccess_200() throws Exception 
    {
    	when(orderServiceImpl.takeOrder(any(), any())).thenReturn(easyRandom.nextObject(TakeOrderResponse.class)); 	
        mockMvc.perform(patch("/orders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidTakeOrderRequestContent()))
        .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    void testGetOrderListSuccess_200() throws Exception {
        when(orderServiceImpl.getOrderList(any(), any())).thenReturn(generateValidOrderListResponse());
        mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .queryParam("page", "1")  
                .queryParam("limit", "10"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    void testGetOrderListSuccess_204() throws Exception 
    {
    	when(orderServiceImpl.getOrderList(any(), any())).thenReturn(generateEmptyOrderListResponse()); 	
        mockMvc.perform(get("/orders", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .queryParam("page", "1")
                .queryParam("limit", "10"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    void testGetOrderListError_400_1() 
	throws Exception 
    {
	  	try {
            mockMvc.perform(get("/orders")
                    .param("page", "0")
                    .param("limit", "10")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(header()));	
    	} catch (NestedServletException e) {
    		assertThat(e.getCause()).isInstanceOf(OrderManagerException400.class);
    	}
    }
    
    @Test
    void testGetOrderListError_400_2() 
	throws Exception 
    {
	  	try {
            mockMvc.perform(get("/orders")
                    .param("page", "1") 
                    .param("limit", "0")
                    .contentType(MediaType.APPLICATION_JSON)
                    .headers(header()));	
    	} catch (NestedServletException e) {
    		assertThat(e.getCause()).isInstanceOf(OrderManagerException400.class);
    	}
    } 
    
    private String generateValidPlaceOrderRequestContent() {
        return "{\n" +
                "  \"origin\": [\"22.319\", \"114.169\"],\n" +
                "  \"destination\": [\"22.2948341\", \"114.2329\"]\n" +
                "}";
    }

    private String generateValidTakeOrderRequestContent() {
        return "{\n" +
                "  \"status\": \"TAKEN\"\n" +
                "}";
    }
    
    private OrderListResponse generateValidOrderListResponse() {
        ArrayList<OrderDTO> lOrderDTO = new ArrayList<>();
        lOrderDTO.add(easyRandom.nextObject(OrderDTO.class));
        return OrderListResponse.builder().orders(lOrderDTO).build();
    }
    
    private OrderListResponse generateEmptyOrderListResponse() {
        ArrayList<OrderDTO> lOrderDTO = new ArrayList<>();
        return OrderListResponse.builder().orders(lOrderDTO).build();
    }
}
