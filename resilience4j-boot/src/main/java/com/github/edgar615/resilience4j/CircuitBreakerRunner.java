package com.github.edgar615.resilience4j;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.bouncycastle.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CircuitBreakerRunner implements ApplicationRunner {

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Autowired
    private AccountClient accountClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("userCircuitBreaker");
//        System.out.println(circuitBreaker);

        for (int i = 0; i < 20; i ++) {
            try {
                Account account = accountClient.getAccountInfo(String.valueOf(i));
                System.out.println(account);
            } catch (CallNotPermittedException e) {
                System.out.println(e.getClass());
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException interruptedException) {
                }
            } catch (RuntimeException e) {
                System.out.println(e.getClass());
            }
        }
    }
}
