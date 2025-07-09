package com.signal.kite;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class KiteAuthController {

    @Autowired
    private KiteAuthDetailsRepository repository;
    
    @Autowired
    private KiteAuthService service;

    @PostMapping
    public ResponseEntity<KiteAuthDetails> saveAuth(@RequestBody KiteAuthDetails authDetails) {
        KiteAuthDetails saved = repository.save(authDetails);
        return ResponseEntity.ok(saved);
    }
   
    @GetMapping("/kiteAuthForm")
    public String showFormPage() {
        return "kiteAuthForm";
    }

    @PostMapping("/kite-auth/save")
    public String saveKiteAuth(@RequestParam String requestToken, HttpServletRequest request) {

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        Optional<KiteAuthDetails> existing = repository.findByAuthDate(today);

        if (existing.isPresent()) {
            request.setAttribute("error", "Auth details already saved for today.");
            return "kiteAuthForm";
        } else {            
            service.saveKiteAuth(requestToken);
            request.setAttribute("success", "Auth details saved successfully.");
        }
        return "kiteAuthForm";
    }
    
    @GetMapping("/kite-auth/get-login-url")
    @ResponseBody
    public String getLoginUrl() {
        return service.getLoginUrl();
    }
}