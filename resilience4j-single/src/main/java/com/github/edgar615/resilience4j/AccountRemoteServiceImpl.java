package com.github.edgar615.resilience4j;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class AccountRemoteServiceImpl implements AccountRemoteService {
    private int seq = 0;
    @Override
    public Account getAccountInfo(String id) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("getAccountInfo");
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setName("retry");
        return account;
//        seq++;
//        if (seq < 3 || seq > 10) {
//            Account account = new Account();
//            account.setId(UUID.randomUUID().toString());
//            account.setName(UUID.randomUUID().toString());
//            return account;
//        }
//        throw new RuntimeException();
    }
}
