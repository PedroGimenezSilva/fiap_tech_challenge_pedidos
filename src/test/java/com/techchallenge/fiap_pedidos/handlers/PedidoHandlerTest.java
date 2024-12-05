package com.techchallenge.fiap_pedidos.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techchallenge.fiap_pedidos.adapters.controller.pedido.PedidoController;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.core.requests.pedido.AtualizarPedidoStatusDto;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarPedidoDto;
import com.techchallenge.fiap_pedidos.helpers.PedidoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PedidoHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private PedidoController controller;

    private AutoCloseable mock;

    @BeforeEach
    void setup() {
        mock = MockitoAnnotations.openMocks(this);
        PedidoHandler pedidoHandler = new PedidoHandler(controller);
        mockMvc = MockMvcBuilders.standaloneSetup(pedidoHandler).build();
    }

    @AfterEach
    void tearsDown() throws Exception {
        mock.close();
    }

    @Test
    public void shouldCreatePedido() throws Exception {
        //Arrange
        CriarPedidoDto criarPedidoDto = PedidoHelper.criarPedidoDto();
        PedidoEntity pedidoEntity = PedidoHelper.criarPedidoEntity();

        Mockito.when(controller.cadastrarPedido(criarPedidoDto)).thenReturn(pedidoEntity);

        //Act & Assert
        mockMvc.perform(
                        post("/api/pedidos")
                                .contentType("application/json")
                                .content(asJsonString(criarPedidoDto))
                )
                .andExpect(status().isCreated());

        verify(controller, times(1)).cadastrarPedido(criarPedidoDto);
    }

    @Test
    public void shouldConsultarByPedidoId() throws Exception {
        //Arrange
        var pedidoId = "pedido_id";
        PedidoEntity pedidoEntity = PedidoHelper.criarPedidoEntity();
        Mockito.when(controller.getById(pedidoId)).thenReturn(pedidoEntity);

        //Act & Assert
        mockMvc.perform(get("/api/pedidos/" + pedidoId))
                .andExpect(status().isOk());

        verify(controller, times(1)).getById(pedidoId);
    }

    @Test
    public void shouldGetPedidos() throws Exception {
        //Arrange
        PedidoEntity pedidoEntity = PedidoHelper.criarPedidoEntity();
        Mockito.when(controller.getAll()).thenReturn(List.of(pedidoEntity));

        //Act & Assert
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk());

        verify(controller, times(1)).getAll();
    }

    @Test
    public void shouldUpdatePedidoStatis() throws Exception {
        //Arrange
        String pedidoId = "123";
        PedidoStatus status = PedidoStatus.RECEBIDO;
        PedidoEntity pedidoEntity = PedidoHelper.criarPedidoEntity();
        AtualizarPedidoStatusDto body = new AtualizarPedidoStatusDto(status);

        Mockito.when(controller.atualizarStatus(pedidoId, status)).thenReturn(pedidoEntity);

        //Act & Assert
        mockMvc.perform(patch("/api/pedidos/" + pedidoId)
                        .contentType("application/json")
                        .content(asJsonString(body)))
                .andExpect(status().isOk());

        verify(controller, times(1)).atualizarStatus(pedidoId, status);
    }

    private String asJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper()
                .writeValueAsString(object);
    }
}