package com.github.edgar615.spring.cloud.circuit;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "account", url = "http://localhost:8080")
public interface AccountClient {
    @GetMapping("/api/accounts/{id}")
    Account getAccountInfo(@PathVariable("id") String id);

    @GetMapping("/api/accounts/error")
    List<Account> error();

    @GetMapping("/api/accounts/sleep?second={second}")
    List<Account> sleep(@Param("second") Integer second);
}
