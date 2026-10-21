package br.edu.uniesp.assistencia.internal.cliente.dto;

import br.edu.uniesp.assistencia.internal.cliente.entity.ClienteEntity;

// DTO de SAIDA: e o formato do JSON devolvido ao usuario.
// Nao tem anotacao de validacao - validacao so faz sentido na entrada.
// Tem id e ativo (que o usuario nunca envia, mas precisa receber) e NAO tem
// a lista de equipamentos: e isso que evita o loop infinito na serializacao
// (cliente -> equipamentos -> cliente -> ...) e o LazyInitializationException.
public record ClienteResponseDto(Long id, String nome, String cpf, String email, boolean ativo) {

    // Metodo de fabrica: converte a entidade (mundo banco) neste DTO (mundo HTTP).
    // static = pertence a classe, chama-se ClienteResponseDto.de(x) sem precisar de um objeto antes.
    public static ClienteResponseDto de(ClienteEntity cliente){

        return new ClienteResponseDto(cliente.getId(),
                                      cliente.getNome(),
                                      cliente.getCpf(),
                                      cliente.getEmail(),
                                      cliente.isAtivo() // boolean usa isAtivo(), nao getAtivo()
                                     );
    }

}

