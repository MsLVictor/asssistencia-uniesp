package br.edu.uniesp.assistencia.internal.ordemservico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AbrirOrdemServicoRequest(
        @NotNull(message = "clienteId é obrigatório")
        Long clienteId,

        @NotNull(message = "equipamentoId é obrigatório")
        Long equipamentoId,

        @NotBlank(message = "descricaoProblema é obrigatória")
        String descricaoProblema
) {
}