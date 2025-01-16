package com.tenpo.pruebatenpo.configuration;

import lombok.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFlux

public class WebFluxConfig implements WebFluxConfigurer {


    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl("https://mock-c10ac848d3b549debe521adf583fc118.mock.insomnia.rest").build();
    }

}