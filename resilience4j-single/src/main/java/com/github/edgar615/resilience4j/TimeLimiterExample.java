package com.github.edgar615.resilience4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.vavr.control.Try;

import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.function.Supplier;

public class TimeLimiterExample {
    public static void main(String[] args) {
        AccountRemoteService accountRemoteService = new AccountRemoteServiceImpl();
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(50))
                .cancelRunningFuture(true)
                .build();
        TimeLimiter timeLimiter = TimeLimiter.of("TimeLimiter", config);

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 将待执行任务提交到线程池，获取Future Supplier
        Supplier<Future<Account>> futureSupplier = () -> executorService.submit(() -> accountRemoteService.getAccountInfo("1"));
        // 使用限时器包装Future Supplier
        Callable restrictedCall = TimeLimiter
                .decorateFutureSupplier(timeLimiter, futureSupplier);
        // 若任务执行超时，onFailure会被触发
        Try.of(restrictedCall::call)
                .onFailure(throwable -> throwable.printStackTrace());

    }
}
