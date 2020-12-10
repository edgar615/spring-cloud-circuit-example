package com.github.edgar615.openfeign.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 张雨舟
 * @Date 2020/8/6
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private int seq = 0;

    @GetMapping("/{id}")
    public Account get(@PathVariable("id") String id) {
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        seq++;
        if (seq < 3 || seq > 10) {
            Account account = new Account();
            account.setId(id);
            account.setName(UUID.randomUUID().toString());
            return account;
        }
        throw new RuntimeException();
    }

    @PostMapping
    public Account add(@RequestBody Account account) {
        System.out.println(account);
        return account;
    }

    @GetMapping
    public List<Account> list() {
        Account account = new Account();
        account.setId(UUID.randomUUID().toString());
        account.setName(UUID.randomUUID().toString());
        return Collections.singletonList(account);
    }

    @GetMapping("/error")
    public List<Account> error() {
        System.out.println("error");
        throw new RuntimeException("occur err");
    }

    @GetMapping("/sleep")
    public List<Account> sleep(@RequestParam(value = "second", defaultValue = "3") Integer second) {
        System.out.println("sleep start");
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("sleep end");
        throw new RuntimeException("occur err");
    }

}
