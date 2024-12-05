package com.techchallenge.fiap_pedidos.external.datasource.mongodb;

import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PedidoSpringRepository extends MongoRepository<PedidoDto, String> {
    List<PedidoDto> findAllByStatusIn(List<PedidoStatus> statuses);
}
