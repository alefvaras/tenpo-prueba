package com.tenpo.pruebatenpo.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;


    @Table(name = "historial_call")
    @Data
    public class HistorialEntity {

        @Id
        private Long id;
        @Column("date_insert")
        private LocalDateTime date;
        private String endpoint;
        @Column("data_parameter")
        private String parameter;
        private Integer response;
        private String error;

        public HistorialEntity(LocalDateTime date, String endpoint, String parameter, Integer response, String error) {

            this.date = date;
            this.endpoint = endpoint;
            this.parameter = parameter;
            this.response = response;
            this.error = error;
        }


    }
