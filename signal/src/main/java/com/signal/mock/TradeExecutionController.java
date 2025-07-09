package com.signal.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TradeExecutionController {

    @Autowired
    private TradeService tradeService;
    
    @PostMapping("/post-signal")
    public ResponseEntity<String> handlePost(@RequestParam String signalprice,@RequestParam String type) {
        tradeService.processTrade(signalprice, type);
        String message = "Received signal: " + type + " at price: " + signalprice;
        return ResponseEntity.ok(message);
    }
    
    @GetMapping("/populateSymbolsDB")
    public String populateSymbolsDB() {
//    	tradeService.populateSymbolsDB();
    	return "Ok";
    }
} 
