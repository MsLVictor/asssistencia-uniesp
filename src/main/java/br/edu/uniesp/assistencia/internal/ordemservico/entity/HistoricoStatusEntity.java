package br.edu.uniesp.assistencia.internal.ordemservico.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "historicoStatus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HistoricoStatusEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ordem_servico_id", nullable = false)
    private OrdemServicoEntity ordemServico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusOrdemServico status;

    @Column(nullable = false, length = 100)
    private String responsavel;

    @Column(length = 500)
    private String observacao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    private HistoricoStatusEntity(OrdemServicoEntity ordemServico, StatusOrdemServico status,
                                  String responsavel, String observacao, LocalDateTime dataHora) {
        this.ordemServico = ordemServico;
        this.status = status;
        this.responsavel = responsavel;
        this.observacao = observacao;
        this.dataHora = dataHora;
    }

    public static HistoricoStatusEntity registrarAbertura(OrdemServicoEntity ordem) {
        return new HistoricoStatusEntity(ordem, StatusOrdemServico.RECEBIDA, "SISTEMA",
                "Abertura da Ordem de Serviço.", ordem.getDataAbertura());
    }
}