package com.alexpages.ordermanager.error;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import lombok.experimental.StandardException;

@StandardException //experimental annotation
public class OrderManagerException403 extends RuntimeException {
	
    public static JSONObject constructJsonResponse(Exception e, String message) 
    {
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", HttpStatus.FORBIDDEN);
        jsonResponse.put("throwable", e.getCause() == null ? "null" : e.getCause());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        jsonResponse.put("timestamp", formattedDateTime);
        jsonResponse.put("message", message);
        return jsonResponse;
    }

}
