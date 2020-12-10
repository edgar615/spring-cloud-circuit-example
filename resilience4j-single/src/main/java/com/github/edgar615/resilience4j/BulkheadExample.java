package com.github.edgar615.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;

import java.time.Duration;
import java.util.function.Function;

public class BulkheadExample {
    public static void main(String[] args) {
        AccountRemoteService accountRemoteService = new AccountRemoteServiceImpl();
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(5)
                .maxWaitDuration(Duration.ofSeconds(1))
                .build();
        Bulkhead bulkhead = Bulkhead.of("bulkhead", config);
        Function<String, Account> accountFunction = Bulkhead.decorateFunction(bulkhead, accountRemoteService::getAccountInfo);
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
