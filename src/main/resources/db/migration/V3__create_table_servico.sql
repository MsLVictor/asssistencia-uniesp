CREATE TABLE ordens_servico
(
    id                 BIGSERIAL     NOT NULL,
    numero_protocolo   VARCHAR(20)   NOT NULL,
    cliente_id         BIGINT        NOT NULL,
    equipamento_id     BIGINT        NOT NULL,
    descricao_problema VARCHAR(1000) NOT NULL,
    status             VARCHAR(30)   NOT NULL,
    data_abertura      TIMESTAMP     NOT NULL,

    CONSTRAINT pk_ordens_servico PRIMARY KEY (id),
    CONSTRAINT uk_ordens_servico_protocolo UNIQUE (numero_protocolo),

    CONSTRAINT fk_ordens_servico_cliente
        FOREIGN KEY (cliente_id)
            REFERENCES clientes (id),

    CONSTRAINT fk_ordens_servico_equipamento
        FOREIGN KEY (equipamento_id)
            REFERENCES equipamentos (id)
);