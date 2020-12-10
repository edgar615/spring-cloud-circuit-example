package com.github.edgar615.spring.cloud.circuit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCircuitBreaker
@EnableFeignClients
//@SpringCloudApplication
public class Application {

    @Autowired
    private AccountClient accountClient;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
