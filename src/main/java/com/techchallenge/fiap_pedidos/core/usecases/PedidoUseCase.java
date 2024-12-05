package com.techchallenge.fiap_pedidos.core.usecases;

import com.techchallenge.fiap_pedidos.adapters.gateway.pedido.PedidoGateway;
import com.techchallenge.fiap_pedidos.core.entities.pedido.ComboEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.core.entities.pedido.ProdutoComboEntity;
import com.techchallenge.fiap_pedidos.core.exceptions.PedidoNotFoundException;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarPedidoDto;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarProdutoComboDto;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoUseCase {

    private final PedidoGateway pedidoGateway;

    public PedidoUseCase(PedidoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    public PedidoEntity criarPedido(CriarPedidoDto criarPedidoDto) {
        var pedido = toPedidoEntity(criarPedidoDto);

        return pedidoGateway.criar(pedido);
    }

    private PedidoEntity toPedidoEntity(CriarPedidoDto criarPedidoDto) {
        OffsetDateTime now = OffsetDateTime.now();
        List<ComboEntity> combos = criarPedidoDto.combos().stream()
                .map(c -> new ComboEntity(
                        toProdutoComboEntity(c.lanche()),
                        toProdutoComboEntity(c.acompanhamento()),
                        toProdutoComboEntity(c.bebida()),
                        toProdutoComboEntity(c.sobremesa())
                )).toList();

        return new PedidoEntity(
                null,
                PedidoStatus.INICIAL,
                criarPedidoDto.clientId(),
                combos,
                now,
                now
        );
    }


    private ProdutoComboEntity toProdutoComboEntity(CriarProdutoComboDto criarProdutoComboDto) {
        return new ProdutoComboEntity(
                criarProdutoComboDto.idProduto(),
                criarProdutoComboDto.quantity(),
                criarProdutoComboDto.price()
        );
    }

    public PedidoEntity buscarPorId(String id) {
        return pedidoGateway.getById(id);
    }

    public List<PedidoEntity> buscarTodos() {
        List<PedidoEntity> pedidos = pedidoGateway.findAllInStatus(List.of(
                PedidoStatus.RECEBIDO,
                PedidoStatus.EM_PREPARACAO,
                PedidoStatus.PRONTO
        ));

        return pedidos.stream()
                .sorted(Comparator
                        .comparing(PedidoEntity::status, Comparator.comparingInt(this::getStatusPriority))
                        .thenComparing(PedidoEntity::createdAt))
                .collect(Collectors.toList());
    }

    private int getStatusPriority(PedidoStatus status) {
        return switch (status) {
            case PRONTO -> 1;
            case EM_PREPARACAO -> 2;
            case RECEBIDO -> 3;
            default -> 4;
        };
    }

    public PedidoEntity atualizarStatus(String id, PedidoStatus status) {
        PedidoEntity pedido = Optional.of(id)
                .map(pedidoGateway::getById)
                .orElseThrow(PedidoNotFoundException::new);

        pedido = pedido.withStatus(status)
                .withUpdatedAt(OffsetDateTime.now());

        return pedidoGateway.atualizar(pedido);
    }
}
