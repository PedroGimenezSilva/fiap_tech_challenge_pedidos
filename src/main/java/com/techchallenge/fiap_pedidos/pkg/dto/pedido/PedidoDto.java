package com.techchallenge.fiap_pedidos.pkg.dto.pedido;


import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;

import java.time.OffsetDateTime;
import java.util.List;

public record PedidoDto(
        String id,
        PedidoStatus status,
        String clientId,
        List<ComboDto> combos,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public PedidoDto withId(String newId) {
        return new PedidoDto(newId, status, clientId, combos, createdAt, updatedAt);
    }
}
