package com.techchallenge.fiap_pedidos.core.requests.pedido;

public record CriarComboDto(
        CriarProdutoComboDto lanche,
        CriarProdutoComboDto acompanhamento,
        CriarProdutoComboDto bebida,
        CriarProdutoComboDto sobremesa
) {
}
