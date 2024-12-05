package com.techchallenge.fiap_pedidos.core.entities.pedido;

import com.techchallenge.fiap_pedidos.core.exceptions.InvalidPedidoException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoEntityTest {

    @Test
    void shouldCreatePedidoEntitySuccessfully() {
        OffsetDateTime now = OffsetDateTime.now();
        ComboEntity combo = new ComboEntity(
                new ProdutoComboEntity("1", 2, new BigDecimal("10.00")),
                null,
                null,
                null
        );

        PedidoEntity pedido = new PedidoEntity("1", PedidoStatus.INICIAL, "client123", List.of(combo), now, now);

        assertNotNull(pedido);
        assertEquals("1", pedido.id());
        assertEquals(PedidoStatus.INICIAL, pedido.status());
        assertEquals("client123", pedido.clientId());
        assertEquals(1, pedido.combos().size());
        assertEquals(now, pedido.createdAt());
        assertEquals(now, pedido.updatedAt());
    }

    @Test
    void shouldThrowExceptionWhenClientIdIsNullOrBlank() {
        OffsetDateTime now = OffsetDateTime.now();
        ComboEntity combo = new ComboEntity(
                new ProdutoComboEntity("1", 2, new BigDecimal("10.00")),
                null,
                null,
                null
        );

        assertThrows(InvalidPedidoException.class, () ->
                new PedidoEntity("1", PedidoStatus.INICIAL, null, List.of(combo), now, now));

        assertThrows(InvalidPedidoException.class, () ->
                new PedidoEntity("1", PedidoStatus.INICIAL, "   ", List.of(combo), now, now));
    }

    @Test
    void shouldThrowExceptionWhenCombosListIsNullOrEmpty() {
        OffsetDateTime now = OffsetDateTime.now();

        assertThrows(InvalidPedidoException.class, () ->
                new PedidoEntity("1", PedidoStatus.INICIAL, "client123", null, now, now));

        assertThrows(InvalidPedidoException.class, () ->
                new PedidoEntity("1", PedidoStatus.INICIAL, "client123", List.of(), now, now));
    }

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        OffsetDateTime now = OffsetDateTime.now();
        ComboEntity combo1 = new ComboEntity(
                new ProdutoComboEntity("1", 2, new BigDecimal("10.00")),
                null,
                null,
                null
        );
        ComboEntity combo2 = new ComboEntity(
                null,
                new ProdutoComboEntity("2", 1, new BigDecimal("20.00")),
                null,
                null
        );

        PedidoEntity pedido = new PedidoEntity("1", PedidoStatus.INICIAL, "client123", List.of(combo1, combo2), now, now);

        assertEquals(new BigDecimal("40.00"), pedido.getTotalPrice());
    }

    @Test
    void withStatusShouldReturnNewPedidoEntityWithUpdatedStatus() {
        OffsetDateTime now = OffsetDateTime.now();
        ComboEntity combo = new ComboEntity(
                new ProdutoComboEntity("1", 2, new BigDecimal("10.00")),
                null,
                null,
                null
        );
        PedidoEntity pedido = new PedidoEntity("1", PedidoStatus.INICIAL, "client123", List.of(combo), now, now);

        PedidoEntity updatedPedido = pedido.withStatus(PedidoStatus.RECEBIDO);

        assertNotSame(pedido, updatedPedido);
        assertEquals(PedidoStatus.RECEBIDO, updatedPedido.status());
        assertEquals(PedidoStatus.INICIAL, pedido.status()); // Ensure original is unchanged
    }

    @Test
    void withUpdatedAtShouldReturnNewPedidoEntityWithUpdatedTimestamp() {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime updatedTime = now.plusHours(1);
        ComboEntity combo = new ComboEntity(
                new ProdutoComboEntity("1", 2, new BigDecimal("10.00")),
                null,
                null,
                null
        );
        PedidoEntity pedido = new PedidoEntity("1", PedidoStatus.INICIAL, "client123", List.of(combo), now, now);

        PedidoEntity updatedPedido = pedido.withUpdatedAt(updatedTime);

        assertNotSame(pedido, updatedPedido);
        assertEquals(updatedTime, updatedPedido.updatedAt());
        assertEquals(now, pedido.updatedAt()); // Ensure original is unchanged
    }
}
