package com.wedul.circuit.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WedulConfig {

    private static final String CIRCUIT_NAME = "wedul";

    @Bean
    public io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(CIRCUIT_NAME);
    }

}
