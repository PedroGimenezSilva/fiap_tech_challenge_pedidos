package com.techchallenge.fiap_pedidos.adapters.gateway.pedido.mapper;

import com.techchallenge.fiap_pedidos.core.entities.pedido.ComboEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.ProdutoComboEntity;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.ComboDto;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.ProdutoComboDto;

public class PedidoMapper {

    public static PedidoDto toPedidoDto(PedidoEntity pedido) {
        return new PedidoDto(
                pedido.id(),
                pedido.status(),
                pedido.clientId(),
                pedido.combos().stream().map(PedidoMapper::toComboDto).toList(),
                pedido.createdAt(),
                pedido.updatedAt()
        );
    }

    public static PedidoEntity toPedidoEntity(PedidoDto pedido) {
        return new PedidoEntity(
                pedido.id(),
                pedido.status(),
                pedido.clientId(),
                pedido.combos().stream().map(PedidoMapper::toComboEntity).toList(),
                pedido.createdAt(),
                pedido.updatedAt()
        );
    }

    static ComboDto toComboDto(ComboEntity comboEntity) {
        return new ComboDto(
                toProdutoComboDto(comboEntity.lanche()),
                toProdutoComboDto(comboEntity.acompanhamento()),
                toProdutoComboDto(comboEntity.bebida()),
                toProdutoComboDto(comboEntity.sobremesa())
        );
    }

    static ComboEntity toComboEntity(ComboDto comboDto) {
        return new ComboEntity(
                toProdutoComboEntity(comboDto.lanche()),
                toProdutoComboEntity(comboDto.acompanhamento()),
                toProdutoComboEntity(comboDto.bebida()),
                toProdutoComboEntity(comboDto.sobremesa())
        );
    }

    static ProdutoComboDto toProdutoComboDto(ProdutoComboEntity produtoComboEntity) {
        return new ProdutoComboDto(
                produtoComboEntity.idProduto(),
                produtoComboEntity.quantity(),
                produtoComboEntity.price()
        );
    }

    static ProdutoComboEntity toProdutoComboEntity(ProdutoComboDto produtoComboDto) {
        return new ProdutoComboEntity(
                produtoComboDto.idProduto(),
                produtoComboDto.quantity(),
                produtoComboDto.price()
        );
    }
}
