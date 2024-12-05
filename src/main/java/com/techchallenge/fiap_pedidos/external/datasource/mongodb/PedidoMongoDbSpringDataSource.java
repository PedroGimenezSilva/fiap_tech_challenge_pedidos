package com.techchallenge.fiap_pedidos.external.datasource.mongodb;

import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
import com.techchallenge.fiap_pedidos.pkg.interfaces.IPedidoDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class PedidoMongoDbSpringDataSource implements IPedidoDataSource {
    private final PedidoSpringRepository repository;

    public PedidoMongoDbSpringDataSource(PedidoSpringRepository repository) {
        this.repository = repository;
    }

    @Override
    public PedidoDto insertPedido(PedidoDto pedido) {
        var id = UUID.randomUUID().toString();
        return repository.save(pedido.withId(id));
    }

    public PedidoDto savePedido(PedidoDto pedido) {
        return repository.save(pedido);
    }

    public PedidoDto getPedidoById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<PedidoDto> findAll() {
        return repository.findAll();
    }

    @Override
    public List<PedidoDto> findAllInStatus(List<PedidoStatus> statuses) {
        return repository.findAllByStatusIn(statuses);
    }
}
