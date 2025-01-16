package com.tenpo.pruebatenpo.model;

import com.tenpo.pruebatenpo.entity.HistorialEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

@AllArgsConstructor

public class HistorialResponse {
    private final List<HistorialEntity> historial;
    private final int currentPage;
    private final int pageSize;
    private final long totalElements;



}
