package com.richard.circuitbreaker.resoures;

import com.richard.circuitbreaker.services.FailingService;
import org.reactivestreams.Publisher;
import org.springframework.cloud.circuitbreaker.commons.ReactiveCircuitBreaker;
import org.springframework.cloud.circuitbreaker.commons.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RestController
public class FailingResource {

    private final FailingService failingService;
    private final ReactiveCircuitBreaker circuitBreaker;

    public FailingResource(FailingService fs,
                           ReactiveCircuitBreakerFactory cbf) {
        this.failingService = fs;
        this.circuitBreaker = cbf.create("greet");
    }

    @GetMapping("/greet")
    Publisher<String> greet(@RequestParam Optional<String> name) {
        var result = this.failingService.greet(name);
        return this.circuitBreaker.run(result, throwable -> Mono.just("Hello Word! "));
    }


}
