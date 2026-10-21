package br.edu.uniesp.assistencia.internal.cliente.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uniesp.assistencia.internal.cliente.dto.ClienteRequestDto;
import br.edu.uniesp.assistencia.internal.cliente.dto.ClienteResponseDto;
import br.edu.uniesp.assistencia.internal.cliente.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor // Lombok: gera o construtor com os campos final (a injecao de dependencia)
@RestController // marca como controller REST e ja converte todo retorno em JSON (equivale ao [ApiController] do C#)
@RequestMapping("/clientes") // prefixo de todas as rotas desta classe
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping // mapeia POST /clientes. Sem argumento porque o prefixo ja esta na classe
    public ClienteResponseDto criar(
            // @RequestBody: converte o JSON recebido em ClienteRequestDto.
            // @Valid: dispara as anotacoes de validacao do DTO. SEM ELE as validacoes
            //         sao ignoradas em silencio e o dado invalido passa direto pro banco.
            @RequestBody @Valid ClienteRequestDto cliente){

        // O controller so fala HTTP: recebe, delega e devolve. Regra de negocio fica no service.
        return clienteService.criar(cliente);
    }
}
