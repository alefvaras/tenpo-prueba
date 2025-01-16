package com.tenpo.pruebatenpo.rest;

import com.tenpo.pruebatenpo.entity.HistorialEntity;
import com.tenpo.pruebatenpo.model.HistorialResponse;
import com.tenpo.pruebatenpo.repository.HistorialRepository;
import com.tenpo.pruebatenpo.services.CalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Porcentaje API", description = "Endpoints para cálculo de porcentaje y consulta de historial")
public class CalculationController {

    @Autowired
    private CalculationService calculationService;


    @GetMapping("/calculate/{num1}/{num2}")
    @Operation(summary = "Calcula el resultado con porcentaje dinámico", description = "Recibe dos números y los multiplica por el porcentaje obtenido del servicio externo.")

    public Mono<ResponseEntity<Integer>>  calculate(@PathVariable int num1, @PathVariable int num2) {
        return calculationService.calculate(num1, num2)
                .map(respponse -> ResponseEntity.ok(respponse))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body(null)));
    }

    @GetMapping("/historial")
    @Operation(summary = "Obtiene el historial de llamadas paginado", description = "Permite consultar los registros con paginación.")

    public Mono<HistorialResponse> getHistorial(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return calculationService.getHistorial(page, size);
    }

}
