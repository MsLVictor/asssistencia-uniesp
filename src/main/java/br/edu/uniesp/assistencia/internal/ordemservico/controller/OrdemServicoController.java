package br.edu.uniesp.assistencia.internal.ordemservico.controller;

import br.edu.uniesp.assistencia.internal.ordemservico.dto.AbrirOrdemServicoRequest;
import br.edu.uniesp.assistencia.internal.ordemservico.dto.OrdemServicoResponse;
import br.edu.uniesp.assistencia.internal.ordemservico.service.OrdemServicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/ordens")
@RequiredArgsConstructor
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    @PostMapping
    public ResponseEntity<OrdemServicoResponse> abrir(@RequestBody @Valid AbrirOrdemServicoRequest request) {
        OrdemServicoResponse resposta = ordemServicoService.abrir(request);
        URI location = URI.create("/ordens/" + resposta.id());
        return ResponseEntity.created(location).body(resposta);
    }
}