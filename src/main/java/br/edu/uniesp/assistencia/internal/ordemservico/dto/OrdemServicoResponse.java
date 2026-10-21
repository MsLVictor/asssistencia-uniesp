package br.edu.uniesp.assistencia.internal.ordemservico.dto;

import br.edu.uniesp.assistencia.internal.ordemservico.entity.OrdemServicoEntity;
import br.edu.uniesp.assistencia.internal.ordemservico.entity.StatusOrdemServico;

import java.time.LocalDateTime;

public record OrdemServicoResponse(
        Long id,
        String numeroProtocolo,
        StatusOrdemServico status,
        LocalDateTime dataAbertura
) {
    public static OrdemServicoResponse de(OrdemServicoEntity ordem) {
        return new OrdemServicoResponse(ordem.getId(), ordem.getNumeroProtocolo(),
                ordem.getStatus(), ordem.getDataAbertura());
    }
}
