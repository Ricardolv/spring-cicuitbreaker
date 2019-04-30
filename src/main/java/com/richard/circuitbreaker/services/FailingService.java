package com.richard.circuitbreaker.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Log4j2
@Service
public class FailingService {

    public Mono<String> greet(Optional<String> name) {
        var seconds = (long) (Math.random() * 10);
        return name
                .map(str -> {
                  var msg = "Hello " + str + "! (in " + seconds + ")";
                  log.info(msg);
                  return Mono.just(msg);
                })
                .orElse(Mono.error(new NullPointerException()))
                .delayElement(Duration.ofSeconds(seconds));
    }

}
