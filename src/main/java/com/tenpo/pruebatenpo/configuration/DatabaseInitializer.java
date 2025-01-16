package com.tenpo.pruebatenpo.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DatabaseClient databaseClient;

    public DatabaseInitializer(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public void run(String... args) throws Exception {

        String sql = "CREATE TABLE IF NOT EXISTS historial_call (\n" +
                "                                                  id SERIAL PRIMARY KEY,\n" +
                "                                                  date_insert TIMESTAMP NOT NULL,\n" +
                "                                                  endpoint VARCHAR(255) NOT NULL,\n" +
                "    data_parameter TEXT NOT NULL,\n" +
                "    response INTEGER,\n" +
                "    error TEXT\n" +
                "    );";

        Mono<Void> result = databaseClient.sql(sql).fetch().all().then();


        result.block();

        System.out.println("Tabla 'historial_call' creada si no existía.");
    }
}
