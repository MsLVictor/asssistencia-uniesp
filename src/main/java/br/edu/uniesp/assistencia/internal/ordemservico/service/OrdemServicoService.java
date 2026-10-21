package br.edu.uniesp.assistencia.internal.ordemservico.service;

import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;
import br.edu.uniesp.assistencia.internal.cliente.repository.ClienteRepository;
import br.edu.uniesp.assistencia.internal.equipamento.entity.EquipamentoEntity;
import br.edu.uniesp.assistencia.internal.equipamento.repository.EquipamentoRepository;
import br.edu.uniesp.assistencia.internal.ordemservico.dto.AbrirOrdemServicoRequest;
import br.edu.uniesp.assistencia.internal.ordemservico.dto.OrdemServicoResponse;
import br.edu.uniesp.assistencia.internal.ordemservico.entity.HistoricoStatusEntity;
import br.edu.uniesp.assistencia.internal.ordemservico.entity.OrdemServicoEntity;
import br.edu.uniesp.assistencia.internal.ordemservico.repository.HistoricoStatusRepository;
import br.edu.uniesp.assistencia.internal.ordemservico.repository.OrdemServicoRepository;
import br.edu.uniesp.assistencia.shared.exception.RecursoNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrdemServicoService {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("yyyyMMdd");

    private final ClienteRepository clienteRepository;
    private final EquipamentoRepository equipamentoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final HistoricoStatusRepository historicoStatusRepository;

    @Transactional
    public OrdemServicoResponse abrir(AbrirOrdemServicoRequest request) {
        ClienteEntity cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Cliente não encontrado: " + request.clienteId()));

        EquipamentoEntity equipamento = equipamentoRepository.findById(request.equipamentoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Equipamento não encontrado: " + request.equipamentoId()));

        LocalDateTime agora = LocalDateTime.now();
        OrdemServicoEntity ordem = OrdemServicoEntity.abrir(gerarProtocolo(agora), cliente, equipamento,
                request.descricaoProblema(), agora);
        ordemServicoRepository.save(ordem);

        historicoStatusRepository.save(HistoricoStatusEntity.registrarAbertura(ordem));

        return OrdemServicoResponse.de(ordem);
    }

    // RN04: OS-YYYYMMDD-XXXX, repetindo até achar um protocolo que ainda não existe
    private String gerarProtocolo(LocalDateTime data) {
        String protocolo;
        do {
            String sufixo = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
            protocolo = "OS-" + data.format(FORMATO_DATA) + "-" + sufixo;
        } while (ordemServicoRepository.existsByNumeroProtocolo(protocolo));
        return protocolo;
    }
}