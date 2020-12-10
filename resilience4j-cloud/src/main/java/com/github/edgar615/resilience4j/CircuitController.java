package com.github.edgar615.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class CircuitController {

    @Autowired
    private CircuitBreakerRegistry registry;

    @GetMapping
    CircuitBreaker get() {
        return registry.circuitBreaker("account");
    }
}
