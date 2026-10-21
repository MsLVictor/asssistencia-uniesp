package br.edu.uniesp.assistencia.internal.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

// DTO de ENTRADA: e o formato do JSON que o usuario envia no POST.
// Nao tem id nem ativo de proposito - se tivesse, o usuario poderia mandar
// esses campos e alterar o que nao deveria (ataque de "mass assignment").
//
// IMPORTANTE: as anotacoes abaixo nao fazem nada sozinhas.
// So funcionam porque o controller escreveu @Valid no parametro.
public record ClienteRequestDto(

    @NotBlank(message = "O nome é obrigatório") // nao nulo, nao vazio e nao so espacos (@NotNull deixaria passar "")
    @Size(max = 150, message = "O nome deve ter no máximo 150 caracteres.") // espelha o VARCHAR(150) da migration
    String nome,

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(regexp = "[0-9]{11}", message = "O CPF deve conter exatamente 11 digitos, sem pontos ou traços.") // so valida o formato, nao o digito verificador
    String cpf,

    @NotBlank(message = "O email é obrigatório.")
    @Email(message = "O formato de email informado é inválido.") // confere o formato do endereco
    @Size(max = 150, message = "O email deve ter no máximo 150 caracteres.")
    String email
)
{
  // record: o compilador gera construtor, equals, hashCode, toString
  // e os getters SEM o prefixo get -> cliente.nome(), cliente.cpf()
}
