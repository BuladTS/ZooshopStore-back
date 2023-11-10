package ru.sfu.zooshopback.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.sfu.zooshopback.repository.CategoryRepository;
import ru.sfu.zooshopback.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
@ConfigurationProperties(prefix = "host")
public class TestConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().anyRequest();
        };
    }
}
