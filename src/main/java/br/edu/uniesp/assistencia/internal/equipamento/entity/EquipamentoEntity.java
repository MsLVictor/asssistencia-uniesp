package br.edu.uniesp.assistencia.internal.equipamento.entity;

import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA: transforma esta classe numa tabela do banco
@Getter // Lombok: gera os getters
@Table(name = "equipamentos") // nome da tabela
@NoArgsConstructor(access = AccessLevel.PROTECTED) // construtor vazio exigido pela JPA
public class EquipamentoEntity {

    @Id // chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id gerado pelo banco
    private Long id;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(nullable = false, length = 100)
    private String modelo;

    // name = "numero_serie": o campo Java e camelCase, a coluna do banco e snake_case.
    // Sem "nullable = false" a coluna aceita NULL (numero de serie e opcional).
    @Column(name = "numero_serie", length = 100)
    private String numeroSerie;

    // @ManyToOne: varios equipamentos pertencem a um cliente (o "N" do relacionamento 1-N).
    // Este e o lado DONO da relacao: e aqui que a coluna de chave estrangeira existe.
    // fetch = LAZY   -> nao carrega o cliente junto; so busca no banco se alguem chamar getCliente().
    //                   (ManyToOne vem EAGER por padrao, o que gera query desnecessaria - por isso trocamos)
    // optional=false -> todo equipamento precisa ter dono.
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn: nomeia a coluna de chave estrangeira gerada por este relacionamento.
    @JoinColumn(name = "cliente_id", nullable = false)
    private ClienteEntity cliente;

    public EquipamentoEntity(String tipo, String marca, String modelo, String numeroSerie) {
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
    }

    public void associarCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public boolean pertenceA(ClienteEntity outroCliente) {
        return cliente.getId().equals(outroCliente.getId());
    }

}
