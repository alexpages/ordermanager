package com.alexpages.ordermanager.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.alexpages.ordermanager.domain.Order;
import com.alexpages.ordermanager.domain.OrderDTO;
import com.alexpages.ordermanager.domain.OrderListResponse;
import com.alexpages.ordermanager.domain.OrderOuputData;
import com.alexpages.ordermanager.domain.OrderPatchResponse;
import com.alexpages.ordermanager.domain.OrderPostResponse;
import com.alexpages.ordermanager.domain.PlaceOrderResponse;
import com.alexpages.ordermanager.domain.TakeOrderResponse;
import com.alexpages.ordermanager.error.OrderManagerException400;
import com.alexpages.ordermanager.error.OrderManagerException404;
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
        when(orderServiceImpl.getOrderList(any())).thenReturn(generateValidOrderOuputData());
        mockMvc.perform(post("/orders/request")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidOrderInputData()))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
    
	@Test
    void testGetOrderListSuccess_204() throws Exception 
    {
    	when(orderServiceImpl.getOrderList(any())).thenReturn(new OrderOuputData()); 	
        mockMvc.perform(post("/orders/request")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(header())
                .content(generateValidOrderInputData()))
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
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
	void testDeleteOrder_404() throws Exception {
	    mockMvc.perform(delete("/orders/{id}", 1L)
	            .contentType(MediaType.APPLICATION_JSON)
	            .headers(header())
	            .content("{}"))
	            .andExpect(status().is(HttpStatus.NO_CONTENT.value()));
	}

	private String generateValidPlaceOrderRequestContent() {
	    return "{\n" +
	            "  \"coordinates\": {\n" +  // Change to object instead of array
	            "    \"origin\": [\"22.319\", \"114.169\"],\n" +
	            "    \"destination\": [\"22.2948341\", \"114.2329\"]\n" +
	            "  },\n" +
	            "  \"description\": \"Pick up for Carlos\"\n" +
	            "}";
	}


    private String generateValidTakeOrderRequestContent() {
        return "{\n" +
                "  \"status\": \"TAKEN\"\n" +
                "}";
    }
    
    private String generateValidOrderInputData() {
        return "{\n" +
                "  \"inputSearch\": {\n" +
                "    \"orderId\": 123456,\n" +
                "    \"status\": \"TAKEN\",\n" +
                "    \"creationDate\": \"2024-04-02\"\n" +
                "  },\n" +
                "  \"paginationBody\": {\n" +
                "    \"page\": 1,\n" +
                "    \"size\": 10\n" +
                "  }\n" +
                "}";
    }
    
    private OrderOuputData generateValidOrderOuputData() {
        ArrayList<Order> lOrder = new ArrayList<>();
        lOrder.add(easyRandom.nextObject(Order.class));
        
        OrderOuputData response = new OrderOuputData();
        response.setOrders(lOrder);
        return response;
    }
    
    private OrderListResponse generateEmptyOrderListResponse() {
        ArrayList<OrderDTO> lOrderDTO = new ArrayList<>();
        return OrderListResponse.builder().orders(lOrderDTO).build();
    }
}
