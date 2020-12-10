package com.github.edgar615.resilience4j;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.vavr.CheckedRunnable;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class RateLimiterExample {
    public static void main(String[] args) {
        AccountRemoteService accountRemoteService = new AccountRemoteServiceImpl();
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(3000))
                .limitForPeriod(5)
                .timeoutDuration(Duration.ofMillis(1000))
                .build();
        RateLimiter rateLimiter = RateLimiter.of("ratelimiter", config);
        Function<String, Account> accountFunction = RateLimiter.decorateFunction(rateLimiter, accountRemoteService::getAccountInfo);
//        CheckedRunnable restrictedCall = RateLimiter
//                .decorateCheckedRunnable(rateLimiter, accountRemoteService::getAccountInfo);
        for (int i = 0; i < 20; i ++) {
            try {
                Account account = accountFunction.apply(String.valueOf(i));
                System.out.println(account);
            } catch (Exception e) {
                System.out.println(e.getClass());
            }
        }

    }
}
