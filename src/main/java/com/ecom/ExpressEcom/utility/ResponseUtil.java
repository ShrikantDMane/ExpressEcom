package com.ecom.ExpressEcom.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class ResponseUtil {

    private ResponseUtil() {
        // Private constructor to prevent instantiation
    }

    public static ResponseEntity<Map<String, Object>> successResponse(Object data, String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("message", message);
        response.put("data", data);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<Map<String, Object>> successResponse(Object data, String message) {
        return successResponse(data, message, HttpStatus.OK);
    }

    public static ResponseEntity<Map<String, Object>> createdResponse(Object data, String message) {
        return successResponse(data, message, HttpStatus.CREATED);
    }

    public static ResponseEntity<Map<String, Object>> errorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("message", message);
        response.put("error", status.getReasonPhrase());

        return new ResponseEntity<>(response, status);
    }
}
