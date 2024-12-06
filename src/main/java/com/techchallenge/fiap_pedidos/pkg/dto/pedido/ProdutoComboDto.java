package com.techchallenge.fiap_pedidos.pkg.dto.pedido;

import java.math.BigDecimal;

public record ProdutoComboDto(String idProduto, Integer quantity, BigDecimal price) {
}

