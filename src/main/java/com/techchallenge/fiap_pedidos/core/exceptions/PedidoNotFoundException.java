package com.techchallenge.fiap_pedidos.core.exceptions;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException() {
        super("Pedido não existe");
    }
}
