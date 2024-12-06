package com.techchallenge.fiap_pedidos.core.entities.pedido;

import com.techchallenge.fiap_pedidos.core.exceptions.InvalidProdutoComboException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProdutoComboEntityTest {

    @Test
    void shouldCreateProdutoComboEntityWithValidData() {
        ProdutoComboEntity produto = new ProdutoComboEntity("123", 2, new BigDecimal("10.00"));

        assertNotNull(produto);
        assertEquals("123", produto.idProduto());
        assertEquals(2, produto.quantity());
        assertEquals(new BigDecimal("10.00"), produto.price());
    }

    @Test
    void shouldThrowExceptionWhenIdProdutoIsNull() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity(null, 2, new BigDecimal("10.00"))
        );

        assertEquals("produto precisa ser informado", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenIdProdutoIsBlank() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity("   ", 2, new BigDecimal("10.00"))
        );

        assertEquals("produto precisa ser informado", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNull() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity("123", null, new BigDecimal("10.00"))
        );

        assertEquals("produto precisa ter uma quantidade maior que zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsZero() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity("123", 0, new BigDecimal("10.00"))
        );

        assertEquals("produto precisa ter uma quantidade maior que zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity("123", -1, new BigDecimal("10.00"))
        );

        assertEquals("produto precisa ter uma quantidade maior que zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNull() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity("123", 2, null)
        );

        assertEquals("Preço do produto precisa ser maior que zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsZero() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity("123", 2, BigDecimal.ZERO)
        );

        assertEquals("Preço do produto precisa ser maior que zero", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenPriceIsNegative() {
        Exception exception = assertThrows(InvalidProdutoComboException.class, () ->
                new ProdutoComboEntity("123", 2, new BigDecimal("-10.00"))
        );

        assertEquals("Preço do produto precisa ser maior que zero", exception.getMessage());
    }

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        ProdutoComboEntity produto = new ProdutoComboEntity("123", 3, new BigDecimal("15.00"));

        BigDecimal totalPrice = produto.getTotalPrice();
        assertEquals(new BigDecimal("45.00"), totalPrice);
    }

    @Test
    void shouldCalculateTotalPriceForOneItem() {
        ProdutoComboEntity produto = new ProdutoComboEntity("123", 1, new BigDecimal("20.00"));

        BigDecimal totalPrice = produto.getTotalPrice();
        assertEquals(new BigDecimal("20.00"), totalPrice);
    }
}
