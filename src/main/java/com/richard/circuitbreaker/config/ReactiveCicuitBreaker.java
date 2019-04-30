package com.richard.circuitbreaker.config;


import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.commons.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class ReactiveCicuitBreaker {

    @Bean
    ReactiveCircuitBreakerFactory circuitBreakerFactory() {
        var factory = new ReactiveResilience4JCircuitBreakerFactory();
        factory
        .configureDefault(s -> new Resilience4JConfigBuilder(s)
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(5)).build())
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .build());
        return factory;
    }

}
