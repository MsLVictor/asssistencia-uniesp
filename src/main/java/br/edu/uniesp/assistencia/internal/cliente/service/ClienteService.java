package br.edu.uniesp.assistencia.internal.cliente.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.uniesp.assistencia.internal.cliente.dto.ClienteRequestDto;
import br.edu.uniesp.assistencia.internal.cliente.dto.ClienteResponseDto;
import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;
import br.edu.uniesp.assistencia.internal.cliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service // registra a classe no container do Spring para poder ser injetada (equivale ao AddScoped do C#)
@RequiredArgsConstructor // Lombok: gera o construtor com todos os campos final. Se escrever o construtor a mao tambem, da "duplicate method"
public class ClienteService {

    // final = dependencia obrigatoria e que ninguem troca depois.
    // E o final que faz o @RequiredArgsConstructor incluir o campo no construtor.
    private final ClienteRepository clienteRepository;

    // @Transactional: abre uma transacao ao entrar e commita ao sair.
    // Se uma RuntimeException subir, faz rollback automatico de tudo.
    // Aqui garante que as duas consultas e o insert aconteçam na mesma transacao.
    @Transactional
    public ClienteResponseDto criar(ClienteRequestDto cliente){

        // Guard clauses: valida primeiro, age depois ("fail fast").
        // Sem isso o CPF repetido bateria na constraint do Postgres e viraria erro 500.
        if(clienteRepository.existsByCpf(cliente.cpf())){
            // ResponseStatusException e RuntimeException: nao precisa de throws nem try/catch.
            // O Spring intercepta e transforma no status HTTP indicado (409 = conflito).
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                              "Já existe um cliente cadastrado com esse cpf.");
        }

        if(clienteRepository.existsByEmail(cliente.email())){
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                                              "Já existe um cliente cadastrado com esse Email.");
        }

        // 1) converte o DTO de entrada em entidade (o repository so aceita entidade)
        ClienteEntity novoCliente = new ClienteEntity(
                                                      cliente.nome(),
                                                      cliente.cpf(),
                                                      cliente.email()
                                                     );

        // 2) salva. O retorno e que traz o id gerado pelo banco - por isso guardamos numa variavel
        ClienteEntity salvandoCliente = clienteRepository.save(novoCliente);

        // 3) converte a entidade de volta em DTO. A entidade nunca sai do service.
        return ClienteResponseDto.de(salvandoCliente);
    }
}
