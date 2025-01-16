package com.tenpo.pruebatenpo.services;

import com.tenpo.pruebatenpo.model.HistorialResponse;
import com.tenpo.pruebatenpo.rest.CalculationController;
import com.tenpo.pruebatenpo.services.impl.CalculationServiceImpl;
import reactor.core.publisher.Mono;

public interface CalculationService {

    Mono<Integer> calculate(int num1, int num2);

    Mono<HistorialResponse> getHistorial(int page, int size);
}
