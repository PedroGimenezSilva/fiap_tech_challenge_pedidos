package com.techchallenge.fiap_pedidos.core.requests.pedido;

import java.util.List;

public record CriarPedidoDto(String clientId, List<CriarComboDto> combos) {
}
