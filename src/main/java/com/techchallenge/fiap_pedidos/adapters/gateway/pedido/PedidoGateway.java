package com.techchallenge.fiap_pedidos.adapters.gateway.pedido;


import com.techchallenge.fiap_pedidos.adapters.gateway.pedido.mapper.PedidoMapper;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.core.exceptions.InvalidPedidoException;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
import com.techchallenge.fiap_pedidos.pkg.interfaces.IPedidoDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PedidoGateway {

    private final IPedidoDataSource dataSource;

    public PedidoGateway(IPedidoDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public PedidoEntity criar(PedidoEntity pedido) {
        if (pedido.id() != null) throw new InvalidPedidoException("Pedido já existente");
        PedidoDto savedPedido = dataSource.insertPedido(PedidoMapper.toPedidoDto(pedido));
        return PedidoMapper.toPedidoEntity(savedPedido);
    }

    public PedidoEntity atualizar(PedidoEntity pedido) {
        if (pedido.id() == null) throw new InvalidPedidoException("Pedido não existente");
        PedidoDto savedPedido = dataSource.savePedido(PedidoMapper.toPedidoDto(pedido));
        return PedidoMapper.toPedidoEntity(savedPedido);
    }

    public PedidoEntity getById(String id) {
        return Optional.of(id)
                .map(dataSource::getPedidoById)
                .map(PedidoMapper::toPedidoEntity)
                .orElse(null);
    }

    public List<PedidoEntity> findAll() {
        return dataSource.findAll().stream().map(PedidoMapper::toPedidoEntity).toList();
    }

    public List<PedidoEntity> findAllInStatus(List<PedidoStatus> statuses) {
        return dataSource.findAllInStatus(statuses).stream().map(PedidoMapper::toPedidoEntity).toList();
    }
}
