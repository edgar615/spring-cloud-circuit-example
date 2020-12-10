package com.github.edgar615.spring.cloud.circuit;

import com.netflix.hystrix.*;

public class GetAccountCommand extends HystrixCommand<Account> {

    private final AccountClient accountClient;

    private final String id;

    protected GetAccountCommand(AccountClient accountClient, String id) {
        super(Setter.withGroupKey(
//                也就是说我们既可以把一批服务都划分到一个线程池中，也可以把单个服务划分到一个线程池中。
//                HystrixCommandGroupKey 和 HystrixCommandKey 分别用来配置服务分组名称和服务名称
                //设置命令组
                HystrixCommandGroupKey.Factory.asKey("circuitExample"))
                //设置命令键
                .andCommandKey(HystrixCommandKey.Factory.asKey("hystrix"))
                //设置线程池键
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("account"))
                //设置命令属性
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionTimeoutInMilliseconds(5000))
                //设置线程池属性
                .andThreadPoolPropertiesDefaults(
                        HystrixThreadPoolProperties.Setter()
                                .withMaxQueueSize(10)
                                .withCoreSize(2))
        );
        this.accountClient = accountClient;
        this.id = id;
    }

    @Override
    protected Account run() throws Exception {
        return accountClient.getAccountInfo(id);
    }

    @Override
    protected Account getFallback() {
        Account account = new Account();
        account.setId("fallback");
        account.setName("fallback");
        return account;
    }
}
