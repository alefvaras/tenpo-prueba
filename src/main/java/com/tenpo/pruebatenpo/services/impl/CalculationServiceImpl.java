package com.tenpo.pruebatenpo.services.impl;

import com.tenpo.pruebatenpo.entity.HistorialEntity;
import com.tenpo.pruebatenpo.model.HistorialResponse;
import com.tenpo.pruebatenpo.model.MockResponse;
import com.tenpo.pruebatenpo.repository.HistorialRepository;
import com.tenpo.pruebatenpo.services.CalculationService;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import javax.net.ssl.SSLException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CalculationServiceImpl implements CalculationService {

    private static final Logger logger = LoggerFactory.getLogger(CalculationServiceImpl.class);


    private final WebClient webClient;

  private final CaffeineCacheManager caffeineCacheManager;


    private final HistorialRepository historialRepository;


    public CalculationServiceImpl(WebClient webClient, CaffeineCacheManager caffeineCacheManager, HistorialRepository historialRepository) {
        this.webClient = webClient;
        this.caffeineCacheManager = caffeineCacheManager;
        this.historialRepository = historialRepository;
    }

    private void updateCache(Integer value) {
        logger.info("Guardando porcentaje en caché: {}", value);
        caffeineCacheManager.getCache("percentageCache").put("cachedPercentage", value);
    }
    private Mono<Integer> recuperarDesdeCache() {
        Integer cachedValue = (Integer) caffeineCacheManager.getCache("porcentajeCache").get("cachedPercentage", Integer.class);
        if (cachedValue != null) {
            logger.warn("Usando porcentaje en caché: {}", cachedValue);

            return Mono.just(cachedValue);
        } else {
            logger.error("No hay un valor en caché y el servicio externo falló");

            return Mono.error(new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "No hay un valor en caché y el servicio externo falló"));
        }
    }

    public Mono<Integer> calculate(int num1, int num2) {
        return getPercentage()
                .map(percentage -> {
                    int resultado = (num1 + num2) * percentage / 100;
                    logger.info("Cálculo: ({} + {}) * {}% = {}", num1, num2, percentage, resultado);
                    registerHistorial("/calculate", "num1=" + num1 + "&num2=" + num2, resultado, null);
                    return resultado;
                })
                .doOnError(error -> registerHistorial("/calculate", "num1=" + num1 + "&num2=" + num2, null, error.getMessage()));
    }


    @Cacheable(value = "percentageCache", unless = "#result == null")

    public Mono<Integer> getPercentage() {
        return webClient
                .get()
                .uri("/api/v1/percentage")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(MockResponse.class)
                .map(MockResponse::getFake)
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2))
                        .doBeforeRetry(retrySignal -> logger.warn("Reintento {}/3 tras fallo en servicio externo", retrySignal.totalRetriesInARow() + 1)))
                .doOnNext(value -> {
                    logger.info("Porcentaje obtenido: {}", value);
                    updateCache(value);
                })
                .doOnError(error -> logger.error("Error al obtener porcentaje del servicio externo: {}", error.getMessage()))
                .onErrorResume(e -> recuperarDesdeCache());
    }

    private void registerHistorial(String endpoint, String parameter, Integer respponse, String error) {
        HistorialEntity historial = new HistorialEntity(LocalDateTime.now(), endpoint, parameter, respponse, error);
        historialRepository.save(historial)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(ignored -> logger.info("Historial registrado"), e -> logger.error("Error al registrar historial: {}", e.getMessage()));
    }

    public Mono<HistorialResponse> getHistorial(int page, int size) {
        int offset = page * size;
        Flux<HistorialEntity> historialFlux = historialRepository.findWithPagination(size, offset);
        Mono<Long> totalMono = historialRepository.countTotal();

        return historialFlux.collectList()
                .zipWith(totalMono)
                .map(tuple -> new HistorialResponse(tuple.getT1(), page, size, tuple.getT2()));
    }

    }


