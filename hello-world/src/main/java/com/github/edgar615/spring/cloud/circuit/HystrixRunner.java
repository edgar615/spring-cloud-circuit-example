package com.github.edgar615.spring.cloud.circuit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class HystrixRunner implements ApplicationRunner {

    @Autowired
    private AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 0; i < 10; i ++) {
            Account account = accountService.getAccountInfo("1");
            System.out.println(account);
        }
    }
}
