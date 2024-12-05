package com.techchallenge.fiap_pedidos.core.entities.pedido;


import com.techchallenge.fiap_pedidos.core.exceptions.InvalidPedidoException;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record PedidoEntity(
        String id,
        PedidoStatus status,
        String clientId,
        List<ComboEntity> combos,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
    public PedidoEntity {
        // Validation logic inside the record constructor
        if (clientId == null || clientId.isBlank()) {
            throw new InvalidPedidoException("id de cliente invalido");
        }

        if (combos == null || combos.isEmpty()) {
            throw new InvalidPedidoException("Pedido precisa ter pelo menos um combo");
        }
    }

    public BigDecimal getTotalPrice() {
        return combos.stream()
                .map(ComboEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // Setter-like methods (for status and updatedAt)
    public PedidoEntity withStatus(PedidoStatus novoStatus) {
        return new PedidoEntity(id, novoStatus, clientId, combos, createdAt, updatedAt);
    }

    public PedidoEntity withUpdatedAt(OffsetDateTime now) {
        return new PedidoEntity(id, status, clientId, combos, createdAt, now);
    }
}
