package br.edu.uniesp.assistencia.internal.cliente.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;

// @Repository: opcional aqui. Quem registra este bean e a heranca de JpaRepository,
// nao a anotacao. Serve como documentacao e para traduzir excecoes do banco.
@Repository
// E uma INTERFACE e nao tem implementacao: o Spring Data gera a classe em tempo de execucao.
// JpaRepository<ClienteEntity, Long> -> <tipo da entidade, tipo da chave primaria>.
// So com isso ja vem pronto: save, findById, findAll, deleteById, count...
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    // Query methods: o Spring monta o SQL lendo o NOME do metodo.
    // A gramatica e verbo + By + Campo, e o campo tem que existir na entidade.
    // Se o nome do campo estiver errado, NAO da erro de compilacao: quebra quando a app sobe.

    boolean existsByCpf(String cpf); // SELECT EXISTS(... WHERE cpf = ?)

    boolean existsByEmail(String email); // SELECT EXISTS(... WHERE email = ?)
}
