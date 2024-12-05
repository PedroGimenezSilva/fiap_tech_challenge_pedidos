package com.techchallenge.fiap_pedidos.pkg.dto.pedido;

public record ComboDto(
        ProdutoComboDto lanche,
        ProdutoComboDto acompanhamento,
        ProdutoComboDto bebida,
        ProdutoComboDto sobremesa
) {
}

