package br.edu.uniesp.assistencia.internal.ordemservico.entity;

import java.time.LocalDateTime;

import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;
import br.edu.uniesp.assistencia.internal.equipamento.entity.EquipamentoEntity;
import br.edu.uniesp.assistencia.shared.exception.RegraNegocioException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ordensServico")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrdemServicoEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_protocolo", nullable = false, length = 20, unique = true)
    private String numeroProtocolo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private EquipamentoEntity equipamento;

    @Column(name = "descricao_problema", nullable = false, length = 1000)
    private String descricaoProblema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusOrdemServico status;

    @Column(name = "data_abertura", nullable = false)
    private LocalDateTime dataAbertura;

    private OrdemServicoEntity(String numeroProtocolo, ClienteEntity cliente, EquipamentoEntity equipamento,
                               String descricaoProblema, LocalDateTime dataAbertura) {
        this.numeroProtocolo = numeroProtocolo;
        this.cliente = cliente;
        this.equipamento = equipamento;
        this.descricaoProblema = descricaoProblema;
        this.dataAbertura = dataAbertura;
        this.status = StatusOrdemServico.RECEBIDA; // RN03
    }

    public static OrdemServicoEntity abrir(String numeroProtocolo, ClienteEntity cliente,
                                           EquipamentoEntity equipamento, String descricaoProblema,
                                           LocalDateTime dataAbertura) {
        if (!cliente.isAtivo()) { // RN01
            throw new RegraNegocioException("Não é permitido abrir Ordem de Serviço para um cliente inativo.");
        }
        if (!equipamento.pertenceA(cliente)) { // RN02
            throw new RegraNegocioException("O equipamento informado não pertence ao cliente indicado.");
        }
        return new OrdemServicoEntity(numeroProtocolo, cliente, equipamento, descricaoProblema, dataAbertura);
    }
}
