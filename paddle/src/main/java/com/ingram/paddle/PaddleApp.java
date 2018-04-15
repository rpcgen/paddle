package com.ingram.paddle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.core.context.SecurityContextHolder.MODE_INHERITABLETHREADLOCAL;

@SpringBootApplication
public class PaddleApp {

    static {
        SecurityContextHolder.setStrategyName(MODE_INHERITABLETHREADLOCAL);
    }

    public static void main(String[] args) {
        SpringApplication.run(PaddleApp.class, args);
    }
}