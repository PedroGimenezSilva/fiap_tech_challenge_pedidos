package com.techchallenge.fiap_pedidos.core.exceptions;

public class InvalidPedidoException extends RuntimeException {
    public InvalidPedidoException(String message) {
        super(message);
    }
}