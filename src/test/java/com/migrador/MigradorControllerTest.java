package com.migrador;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MigradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void deveRenderizarPaginaMigradorComListaDeTabelasEmMemoria() throws Exception {
        mockMvc.perform(get("/migrador"))
                .andExpect(status().isOk())
                .andExpect(view().name("migrador"))
                .andExpect(model().attributeExists("listaTabelas"))
                .andExpect(content().string(containsString("TB_CLIENTES")))
                .andExpect(content().string(containsString("TB_PRODUTOS")))
                .andExpect(content().string(containsString("Migrar")));
    }

    @Test
    void deveExecutarStrategyCorretaEDelegarQueryEspecificaAoDao() throws Exception {
        mockMvc.perform(post("/migrador")
                        .param("tabelas", "TB_CLIENTES")
                        .param("limiteRegistros", "500"))
                .andExpect(status().isOk())
                .andExpect(view().name("migrador"))
                .andExpect(model().attributeExists("resultado"))
                .andExpect(content().string(containsString("ClientesMigracaoStrategy")))
                .andExpect(content().string(containsString("SELECT id, cpf_cnpj, razao_social, data_cadastro FROM origem.tb_clientes ORDER BY id ASC LIMIT 500")));
    }
}
