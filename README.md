# Spring Cloud Circuit Breaker

<pre> <code>
    // Config
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
</code> </pre>

<pre> <code>
    // Service
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
</code> </pre>

<pre> <code>
    // Resources
    @GetMapping("/greet")
    Publisher<String> greet(@RequestParam Optional<String> name) {
        var result = this.failingService.greet(name);
        return this.circuitBreaker.run(result, throwable -> Mono.just("Hello Word! "));
    }
</code> </pre>

## Test Scenarios

![](data/get-name.png) 

![](data/get-error.png) 

![](data/get-delay.png) 

![](data/get-delay-error.png) 

