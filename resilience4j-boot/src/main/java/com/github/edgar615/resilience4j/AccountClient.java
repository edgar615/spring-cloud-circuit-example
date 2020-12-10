package com.github.edgar615.resilience4j;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "account", url = "http://localhost:8080")
public interface AccountClient {

    @CircuitBreaker(name = "account", fallbackMethod = "getFallback")
    @GetMapping("/api/accounts/{id}")
    Account getAccountInfo(@PathVariable("id") String id);

    default Account getFallback(String id, Exception e) {
        Account account = new Account();
        account.setId("fallback");
        account.setName("fallback");
        return account;
    }
}