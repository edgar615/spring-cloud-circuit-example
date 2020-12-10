package com.github.edgar615.resilience4j;

import io.github.resilience4j.circuitbreaker.configure.CircuitBreakerConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resilience4j.circuitbreaker")
public class MyOwnCircuitBreakerConfigurationProperties extends CircuitBreakerConfigurationProperties {

}