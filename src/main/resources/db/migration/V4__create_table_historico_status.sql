CREATE TABLE historico_status
(
    id               BIGSERIAL    NOT NULL,
    ordem_servico_id BIGINT       NOT NULL,
    status           VARCHAR(30)  NOT NULL,
    responsavel      VARCHAR(100) NOT NULL,
    observacao       VARCHAR(500),
    data_hora        TIMESTAMP    NOT NULL,

    CONSTRAINT pk_historico_status PRIMARY KEY (id),

    CONSTRAINT fk_historico_status_ordem_servico
        FOREIGN KEY (ordem_servico_id)
            REFERENCES ordens_servico (id)
);