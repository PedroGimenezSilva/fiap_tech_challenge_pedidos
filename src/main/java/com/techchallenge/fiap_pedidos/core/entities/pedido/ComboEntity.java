package com.techchallenge.fiap_pedidos.core.entities.pedido;


import com.techchallenge.fiap_pedidos.core.exceptions.InvalidComboException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

public record ComboEntity(
        ProdutoComboEntity lanche,
        ProdutoComboEntity acompanhamento,
        ProdutoComboEntity bebida,
        ProdutoComboEntity sobremesa) {
    public ComboEntity {
        validate(lanche, acompanhamento, bebida, sobremesa);

    }

    private void validate(ProdutoComboEntity lanche, ProdutoComboEntity acompanhamento, ProdutoComboEntity bebida, ProdutoComboEntity sobremesa) {
        if (lanche == null && acompanhamento == null && bebida == null && sobremesa == null) {
            throw new InvalidComboException("Combo precisa ter pelo menos um produto");
        }
    }

    public BigDecimal getTotalPrice() {
        return Stream.of(lanche(), bebida(), acompanhamento(), sobremesa())
                .filter(Objects::nonNull)
                .map(ProdutoComboEntity::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
