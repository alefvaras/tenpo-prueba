package com.tenpo.pruebatenpo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class PruebaTenpoApplication  {

    public static void main(String[] args) {
        SpringApplication.run(PruebaTenpoApplication.class, args);
    }

}
