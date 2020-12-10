package com.github.edgar615.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;

import java.time.Duration;
import java.util.function.Function;

public class RetryExample {
    public static void main(String[] args) {
        AccountRemoteService accountRemoteService = new AccountRemoteServiceImpl();
        RetryConfig config = RetryConfig.custom()
                .retryOnResult(o -> {
                    Account account = (Account) o;
                    return "retry".equals(account.getName());
                })
                .build();
        Retry retry = Retry.of("retry", config);

        Function<String, Account> accountFunction = Retry.decorateFunction(retry, accountRemoteService::getAccountInfo);
        Account account = accountFunction.apply("1");
        System.out.println(account);

    }
}
