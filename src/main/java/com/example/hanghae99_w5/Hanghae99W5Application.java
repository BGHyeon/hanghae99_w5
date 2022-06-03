package com.example.hanghae99_w5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Hanghae99W5Application {

    public static void main(String[] args) {
        SpringApplication.run(Hanghae99W5Application.class, args);
    }

}
