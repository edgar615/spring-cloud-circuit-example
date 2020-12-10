package com.github.edgar615.resilience4j;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

public class CircuitBreakerExample {
    public static void main(String[] args) {
        AccountRemoteService accountRemoteService = new AccountRemoteServiceImpl();
        // Create a CircuitBreaker with default configuration
        // 创建定制化熔断器配置
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(20)
                .waitDurationInOpenState(Duration.ofMillis(1000))
                .slidingWindow(5, 5, CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .permittedNumberOfCallsInHalfOpenState(2)
                .build();

//        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendService");
        CircuitBreaker circuitBreaker = CircuitBreaker.of("backendService", config);
        //        circuitBreaker.getEventPublisher()
//                .onSuccess(event -> logger.info(...))
//    .onError(event -> logger.info(...))
//    .onIgnoredError(event -> logger.info(...))
//    .onReset(event -> logger.info(...))
//    .onStateTransition(event -> logger.info(...));
// Or if you want to register a consumer listening
// to all events, you can do:
        circuitBreaker.getEventPublisher()
                .onEvent(event -> System.out.println(event));
        Function<String, Account> docorated = CircuitBreaker.decorateFunction(circuitBreaker, accountRemoteService::getAccountInfo);

        for (int i = 0; i < 20; i ++) {
            try {
                Account account = docorated.apply("1");
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

//        CheckedFunction0<Account> function0 = CircuitBreaker.decorateCheckedSupplier(circuitBreaker, () -> accountRemoteService.getAccountInfo("1"));
//        for (int i = 0; i < 20; i++) {
//            Try<Account> result = Try.of(function0).recover(throwable -> {
//                Account account = new Account();
//                account.setName("fallback");
//                account.setId("fallback");
//                return account;
//            });
//            System.out.println(result.isSuccess());
//            System.out.println(result.get());
//        }

    }
}
