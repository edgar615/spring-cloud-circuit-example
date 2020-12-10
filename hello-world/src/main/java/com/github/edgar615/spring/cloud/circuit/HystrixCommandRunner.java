package com.github.edgar615.spring.cloud.circuit;

import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.concurrent.Future;

//@Component
public class HystrixCommandRunner implements ApplicationRunner {

    @Autowired
    private AccountClient accountClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        GetAccountCommand command = new GetAccountCommand(accountClient, "1");
//        Account account = command.execute();
//        System.out.println(account);
//        Future<Account> future = command.queue();
//        System.out.println(future.get());

        Observable<Account> observable = command.observe();
        Account account = observable.toBlocking().first();
        System.out.println(account);

    }
}
