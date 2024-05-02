package com.alexpages.ordermanager.web;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.jeasy.random.EasyRandom;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

import com.alexpages.ordermanager.api.domain.OrderAudit;
import com.alexpages.ordermanager.api.domain.OrderDetails;
import com.alexpages.ordermanager.api.domain.OrderOutputAudit;
import com.alexpages.ordermanager.api.domain.OrderOutputData;
import com.alexpages.ordermanager.api.domain.OrderPatchResponse;
import com.alexpages.ordermanager.api.domain.OrderPostResponse;
import com.alexpages.ordermanager.error.OrderManagerException404;
import com.alexpages.ordermanager.service.impl.OrderServiceImpl;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {
	
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
    void testPostOrderSuccess_200() throws Exception 
    {
    	when(orderServiceImpl.postOrder(any())).thenReturn(easyRandom.nextObject(OrderPostResponse.class)); 
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidPlaceOrderRequestContent()))
        .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    void testTakeOrderSuccess_200() throws Exception 
    {
    	when(orderServiceImpl.takeOrder(any(), any())).thenReturn(easyRandom.nextObject(OrderPatchResponse.class)); 	
        mockMvc.perform(patch("/orders/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidTakeOrderRequestContent()))
        .andExpect(status().is(HttpStatus.OK.value()));
    }
    
    @Test
    void testGetOrderListSuccess_200() throws Exception {
        when(orderServiceImpl.getOrders(any())).thenReturn(generateValidOrderOutputData());
        mockMvc.perform(post("/orders/request")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidOrderInputData()))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
    
	@Test
    void testGetOrderListSuccess_204() throws Exception 
    {
    	when(orderServiceImpl.getOrders(any())).thenReturn(null); 	
        mockMvc.perform(post("/orders/request")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidOrderInputData()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }
	
	@Test
	void testGetOrderAuditSuccess_204() throws Exception {
		when(orderServiceImpl.getAuditList(any())).thenReturn(null);
        mockMvc.perform(post("/orders/audit/request")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidOrderInputData()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
    }
	
	@Test
	void testGetOrderAuditSuccess_200() throws Exception {
		when(orderServiceImpl.getAuditList(any())).thenReturn(generateValidOrderOutputAudit());
        mockMvc.perform(post("/orders/audit/request")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidOrderInputData()))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
	
	@Test
	void testDeleteOrder_204() throws Exception {
	    doNothing().when(orderServiceImpl).deleteOrderById(any());
	    mockMvc.perform(delete("/orders/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .headers(header())
	            .content("{}"))
	            .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}
	
	@Test
	void testDeleteOrder_404() throws Exception 
	{
	    mockMvc.perform(delete("/orders/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .headers(header())
	            .content("{}"))
	            .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}
	
	@Test
	void testDeleteOrder_400() throws Exception 
	{
	    mockMvc.perform(delete("/orders/{id}","{\"id\": null}")
	            .contentType(MediaType.APPLICATION_JSON)
	            .headers(header())
	            .content("{}"))
	            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
	}

	@Test
	void testGetOrderDetail_200() throws Exception 
	{
		when(orderServiceImpl.getOrderDetail(1L)).thenReturn(easyRandom.nextObject(OrderDetails.class));
		mockMvc.perform(get("/orders/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .headers(header())
	            .content("{}"))
	            .andExpect(status().is(HttpStatus.OK.value()));
	}
	
	@Test
	void testGetOrderDetail_404() throws Exception 
	{
	    when(orderServiceImpl.getOrderDetail(1L)).thenReturn(null);
	    assertThrows(OrderManagerException404.class, () -> {
	        try {
	            mockMvc.perform(get("/orders/{id}", 1L)
	                    .contentType(MediaType.APPLICATION_JSON)
	                    .headers(header())
	                    .content("{}"));
	        } catch (NestedServletException e) {
	            throw (Exception) e.getCause(); 
	        }
	    });
	}

	private String generateValidPlaceOrderRequestContent()
	throws JSONException 
	{
	    JSONObject coordinates = new JSONObject()
	            .put("origin", new JSONArray().put("41.412061").put("2.016507"))
	            .put("destination", new JSONArray().put("41.381115").put("2.119482"));
	    JSONObject requestBody = new JSONObject()
	            .put("coordinates", coordinates)
	            .put("description", "Pickup for Carlos");
	    return requestBody.toString();
	}
	
	private String generateValidTakeOrderRequestContent() 
	throws JSONException {
	    JSONObject requestBody = new JSONObject().put("status", "TAKEN");
	    return requestBody.toString();
	}

	private String generateValidOrderInputData() 
	throws JSONException 
	{
	    JSONObject inputSearch = new JSONObject()
	            .put("orderId", 123456)
	            .put("status", "TAKEN")
	            .put("creationDate", "2024-04-02");
	    JSONObject paginationBody = new JSONObject()
	            .put("page", 1)
	            .put("size", 10);
	    JSONObject requestBody = new JSONObject()
	            .put("inputSearch", inputSearch)
	            .put("paginationBody", paginationBody);
	    return requestBody.toString();
	}

    private OrderOutputData generateValidOrderOutputData() {
        ArrayList<OrderDetails> lOrderDetails = new ArrayList<>();
        lOrderDetails.add(easyRandom.nextObject(OrderDetails.class));
        OrderOutputData response = new OrderOutputData();
        response.setOrders(lOrderDetails);
        return response;
    }
    
    private OrderOutputAudit generateValidOrderOutputAudit() {
        ArrayList<OrderAudit> lOrderAudit = new ArrayList<>();
        lOrderAudit.add(easyRandom.nextObject(OrderAudit.class));
        OrderOutputAudit response = new OrderOutputAudit();
        response.setOrders(lOrderAudit);
        return response;
    }
    
}
