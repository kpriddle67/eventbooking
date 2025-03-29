package com.kp.eventmanagement;

import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Resilience4jConfig {

    @Bean
    public RateLimiter rateLimiter() {
        // Configuring RateLimiter programmatically (optional)
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitForPeriod(10)  // max 10 requests per period
                .limitRefreshPeriod(java.time.Duration.ofMillis(1))  // refresh period in seconds
                .timeoutDuration(java.time.Duration.ofMillis(1))  // timeout for requests
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
        return rateLimiterRegistry.rateLimiter("eventBookingRateLimiter");
    }
}