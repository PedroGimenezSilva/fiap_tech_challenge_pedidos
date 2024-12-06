package com.techchallenge.fiap_pedidos.pkg.dto.pedido;


import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("pedidos")
public record PedidoDto(
        @Id
        String id,
        PedidoStatus status,
        String clientId,
        List<ComboDto> combos,
        String createdAt,
        String updatedAt
) {
    public PedidoDto withId(String newId) {
        return new PedidoDto(newId, status, clientId, combos, createdAt, updatedAt);
    }
}
