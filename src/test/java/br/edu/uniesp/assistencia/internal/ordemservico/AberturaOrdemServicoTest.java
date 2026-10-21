package br.edu.uniesp.assistencia.internal.ordemservico;

import br.edu.uniesp.assistencia.internal.ordemservico.entity.HistoricoStatusEntity;
import br.edu.uniesp.assistencia.internal.ordemservico.entity.StatusOrdemServico;
import br.edu.uniesp.assistencia.internal.ordemservico.repository.HistoricoStatusRepository;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class AberturaOrdemServicoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HistoricoStatusRepository historicoStatusRepository;

    private ResultActions abrirOrdem(String json) throws Exception {
        return mockMvc.perform(post("/ordens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
    }

    @Test
    void cenario1_deveAbrirOrdemComSucesso() throws Exception {
        String resposta = abrirOrdem("""
                {"clienteId": 1, "equipamentoId": 2, "descricaoProblema": "Notebook não liga"}
                """)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.numeroProtocolo").value(matchesPattern("OS-\\d{8}-[0-9A-F]{4}")))
                .andExpect(jsonPath("$.status").value("RECEBIDA"))
                .andExpect(jsonPath("$.dataAbertura").exists())
                .andReturn().getResponse().getContentAsString();

        // RN14: confere se o histórico foi gravado
        Long id = ((Number) JsonPath.read(resposta, "$.id")).longValue();
        List<HistoricoStatusEntity> historico = historicoStatusRepository.findByOrdemServicoId(id);
        assertThat(historico).hasSize(1);
        assertThat(historico.get(0).getStatus()).isEqualTo(StatusOrdemServico.RECEBIDA);
        assertThat(historico.get(0).getResponsavel()).isEqualTo("SISTEMA");
    }

    @Test
    void cenario2_deveRetornar400QuandoCamposObrigatoriosInvalidos() throws Exception {
        abrirOrdem("""
                {"clienteId": null, "equipamentoId": null, "descricaoProblema": "   "}
                """)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erros.clienteId").exists())
                .andExpect(jsonPath("$.erros.equipamentoId").exists())
                .andExpect(jsonPath("$.erros.descricaoProblema").exists());
    }

    @Test
    void cenario3_deveRetornar404QuandoClienteNaoExiste() throws Exception {
        abrirOrdem("""
                {"clienteId": 999, "equipamentoId": 2, "descricaoProblema": "Teste"}
                """)
                .andExpect(status().isNotFound());
    }

    @Test
    void cenario3_deveRetornar404QuandoEquipamentoNaoExiste() throws Exception {
        abrirOrdem("""
                {"clienteId": 1, "equipamentoId": 999, "descricaoProblema": "Teste"}
                """)
                .andExpect(status().isNotFound());
    }

    @Test
    void cenario4_deveRetornar422QuandoClienteInativo() throws Exception {
        abrirOrdem("""
                {"clienteId": 2, "equipamentoId": 3, "descricaoProblema": "Teste"}
                """)
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.detail").value("Não é permitido abrir Ordem de Serviço para um cliente inativo."));
    }

    @Test
    void cenario5_deveRetornar422QuandoEquipamentoDeOutroCliente() throws Exception {
        abrirOrdem("""
                {"clienteId": 1, "equipamentoId": 4, "descricaoProblema": "Teste"}
                """)
                .andExpect(status().isUnprocessableContent())
                .andExpect(jsonPath("$.detail").value("O equipamento informado não pertence ao cliente indicado."));
    }
}
