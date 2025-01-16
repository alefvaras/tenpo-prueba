CREATE TABLE IF NOT EXISTS historial_call (
                                              id SERIAL PRIMARY KEY,
                                              date_insert TIMESTAMP NOT NULL,
                                              endpoint VARCHAR(255) NOT NULL,
    data_parameter TEXT NOT NULL,
    response INTEGER,
    error TEXT
    );