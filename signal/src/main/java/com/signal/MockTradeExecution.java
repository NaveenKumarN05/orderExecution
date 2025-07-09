package com.signal;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MockTradeExecution {

    @PostMapping("/post-data")
    public ResponseEntity<String> handlePost(@RequestBody String requestData) {
        String message = "Received raw data: " + requestData;
        
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
} 
