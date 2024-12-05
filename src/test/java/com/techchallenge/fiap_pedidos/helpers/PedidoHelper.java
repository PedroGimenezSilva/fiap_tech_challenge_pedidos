package com.techchallenge.fiap_pedidos.helpers;

import com.techchallenge.fiap_pedidos.core.entities.pedido.ComboEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.core.entities.pedido.ProdutoComboEntity;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarComboDto;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarPedidoDto;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarProdutoComboDto;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.ComboDto;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.ProdutoComboDto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoHelper {

    public static ProdutoComboDto newProdutoComboDto() {
        return new ProdutoComboDto("p123", 1, BigDecimal.TEN);
    }


    public static ComboDto newComboDto() {
        return new ComboDto(newProdutoComboDto(), newProdutoComboDto(), newProdutoComboDto(), newProdutoComboDto());
    }

    public static PedidoDto newPedidoDto() {
        return new PedidoDto(
                "123",
                PedidoStatus.INICIAL,
                "client1",
                List.of(newComboDto()),
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    public static ComboEntity criarComboValido() {
        ProdutoComboEntity produtoCombo = new ProdutoComboEntity("produto1", 1, BigDecimal.valueOf(10.00));
        return new ComboEntity(produtoCombo, produtoCombo, produtoCombo, produtoCombo);
    }

    public static PedidoEntity criarPedidoValido(String id) {
        return new PedidoEntity(id, PedidoStatus.INICIAL, "client1", List.of(criarComboValido()), OffsetDateTime.now(), OffsetDateTime.now());
    }

    public static CriarProdutoComboDto criarProdutoComboDto() {
        return new CriarProdutoComboDto("produto1", 1, BigDecimal.valueOf(10.00));
    }

    public static CriarPedidoDto criarPedidoDto() {
        CriarProdutoComboDto produto = criarProdutoComboDto();
        return new CriarPedidoDto("client1", List.of(
                new CriarComboDto(produto, produto, produto, produto)
        ));
    }

    public static PedidoEntity criarPedidoEntity() {
        ProdutoComboEntity produto = new ProdutoComboEntity("produto1", 1, BigDecimal.valueOf(10.00));
        ComboEntity combo = new ComboEntity(produto, null, null, null);
        return new PedidoEntity("123", PedidoStatus.INICIAL, "client1", List.of(combo), OffsetDateTime.now(), OffsetDateTime.now());
    }
}
