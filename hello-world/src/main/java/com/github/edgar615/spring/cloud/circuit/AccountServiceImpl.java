package com.github.edgar615.spring.cloud.circuit;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeoutException;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountClient accountClient;

    @Override
    @HystrixCommand(
            groupKey = "circuitExample",
            commandKey = "hystrix",
            threadPoolKey = "account",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
            },
            threadPoolProperties =
                    {
                            @HystrixProperty(name="coreSize",value="2"),
                            @HystrixProperty(name="maxQueueSize",value="5")
                    }
//            fallbackMethod = "getFallback"
    )
    public Account getAccountInfo(String id) {
        return accountClient.getAccountInfo(id);
    }

    private Account getFallback(String id) {
        Account account = new Account();
        account.setId("fallback");
        account.setName("fallback");
        return account;
    }
}
