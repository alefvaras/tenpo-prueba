package com.tenpo.pruebatenpo.repository;

import com.tenpo.pruebatenpo.entity.HistorialEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface HistorialRepository extends R2dbcRepository<HistorialEntity, Long> {


    @Query("SELECT * FROM historial_call ORDER BY date_insert DESC LIMIT :limit OFFSET :offset")
    Flux<HistorialEntity> findWithPagination(int limit, int offset);

    @Query("SELECT COUNT(*) FROM historial_call")
    Mono<Long> countTotal();

}