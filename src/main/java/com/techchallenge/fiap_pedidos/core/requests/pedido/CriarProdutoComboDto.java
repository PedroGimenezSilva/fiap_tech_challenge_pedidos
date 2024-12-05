package com.techchallenge.fiap_pedidos.core.requests.pedido;

import java.math.BigDecimal;

public record CriarProdutoComboDto(String idProduto, Integer quantity, BigDecimal price) {
}

