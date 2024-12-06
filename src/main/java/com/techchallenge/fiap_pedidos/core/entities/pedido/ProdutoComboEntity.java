package com.techchallenge.fiap_pedidos.core.entities.pedido;


import com.techchallenge.fiap_pedidos.core.exceptions.InvalidProdutoComboException;

import java.math.BigDecimal;

public record ProdutoComboEntity(String idProduto, Integer quantity, BigDecimal price) {
    public ProdutoComboEntity {

        validate(idProduto, quantity, price);
    }

    private void validate(String idProduto, Integer quantity, BigDecimal price) {
        if (idProduto == null || idProduto.isBlank())
            throw new InvalidProdutoComboException("produto precisa ser informado");

        if (quantity == null || quantity <= 0)
            throw new InvalidProdutoComboException("produto precisa ter uma quantidade maior que zero");

        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidProdutoComboException("Preço do produto precisa ser maior que zero");
    }

    public BigDecimal getTotalPrice() {
        return price().multiply(BigDecimal.valueOf(quantity()));
    }
}
