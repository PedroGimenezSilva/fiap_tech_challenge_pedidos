package com.techchallenge.fiap_pedidos.adapters.controller.pedido;


import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarPedidoDto;
import com.techchallenge.fiap_pedidos.core.usecases.PedidoUseCase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoController {
    private final PedidoUseCase pedidoUseCase;

    public PedidoController(
            PedidoUseCase pedidoUseCase
    ) {
        this.pedidoUseCase = pedidoUseCase;
    }

    public PedidoEntity cadastrarPedido(CriarPedidoDto criarPedidoDto) {
        return pedidoUseCase.criarPedido(criarPedidoDto);
    }

    public PedidoEntity atualizarStatus(String id, PedidoStatus status) {
        return pedidoUseCase.atualizarStatus(id, status);
    }

    public PedidoEntity getById(String id) {
        return pedidoUseCase.buscarPorId(id);
    }

    public List<PedidoEntity> getAll() {
        return pedidoUseCase.buscarTodos();
    }
}
