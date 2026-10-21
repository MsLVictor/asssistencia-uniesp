package br.edu.uniesp.assistencia.internal.cliente.entity;

import br.edu.uniesp.assistencia.internal.equipamento.entity.EquipamentoEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity // JPA: transforma esta classe numa tabela do banco
@Getter // Lombok: gera os getters. Atencao: boolean vira isAtivo(), nao getAtivo()
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA exige construtor vazio; PROTECTED impede que o resto do codigo crie objeto vazio
@Table(name = "clientes") // nome da tabela; sem isso o JPA usaria o nome da classe
public class ClienteEntity {

    @Id // marca a chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // quem gera o id e o banco (BIGSERIAL do Postgres)
    private Long id;

    // @Column descreve a coluna: nullable=false vira NOT NULL, length vira VARCHAR(n), unique vira UNIQUE.
    // Como o ddl-auto esta em "validate", isso NAO cria a coluna: so confere se bate com a migration do Flyway.
    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(nullable = false, length = 150, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean ativo = true;

    @OneToMany(mappedBy = "cliente")
    private List<EquipamentoEntity> equipamentos = new ArrayList<>();

    public ClienteEntity(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public void adicionarEquipamento(EquipamentoEntity equipamento) {
        equipamentos.add(equipamento);
        equipamento.associarCliente(this);
    }

    
    public void alterarNome(String nome) {
        this.nome = nome;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void inativar() {
        this.ativo = false;
    }
}
