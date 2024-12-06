// package com.techchallenge.fiap_pedidos.core.entities.pedido;

// import com.techchallenge.fiap_pedidos.core.exceptions.InvalidComboException;
// import org.junit.jupiter.api.Test;

// import java.math.BigDecimal;

// import static org.junit.jupiter.api.Assertions.*;

// class ComboEntityTest {

//     @Test
//     void shouldCreateComboEntityWithValidProducts() {
//         ProdutoComboEntity lanche = new ProdutoComboEntity("1", 2, new BigDecimal("10.00"));
//         ProdutoComboEntity bebida = new ProdutoComboEntity("2", 1, new BigDecimal("5.00"));

//         ComboEntity combo = new ComboEntity(lanche, null, bebida, null);

//         assertNotNull(combo);
//         assertEquals(lanche, combo.lanche());
//         assertEquals(bebida, combo.bebida());
//         assertNull(combo.acompanhamento());
//         assertNull(combo.sobremesa());
//     }

//     @Test
//     void shouldThrowExceptionWhenAllProductsAreNull() {
//         Exception exception = assertThrows(InvalidComboException.class, () ->
//                 new ComboEntity(null, null, null, null)
//         );

//         assertEquals("Combo precisa ter pelo menos um produto", exception.getMessage());
//     }

//     @Test
//     void shouldCalculateTotalPriceWithAllProducts() {
//         ProdutoComboEntity lanche = new ProdutoComboEntity("1", 2, new BigDecimal("10.00"));
//         ProdutoComboEntity bebida = new ProdutoComboEntity("2", 1, new BigDecimal("5.00"));
//         ProdutoComboEntity acompanhamento = new ProdutoComboEntity("3", 1, new BigDecimal("7.50"));
//         ProdutoComboEntity sobremesa = new ProdutoComboEntity("4", 1, new BigDecimal("12.00"));

//         ComboEntity combo = new ComboEntity(lanche, acompanhamento, bebida, sobremesa);

//         BigDecimal totalPrice = combo.getTotalPrice();
//         assertEquals(new BigDecimal("44.50"), totalPrice);
//     }

//     @Test
//     void shouldCalculateTotalPriceWithSomeNullProducts() {
//         ProdutoComboEntity lanche = new ProdutoComboEntity("1", 2, new BigDecimal("10.00"));
//         ProdutoComboEntity bebida = new ProdutoComboEntity("2", 1, new BigDecimal("5.00"));

//         ComboEntity combo = new ComboEntity(lanche, null, bebida, null);

//         BigDecimal totalPrice = combo.getTotalPrice();
//         assertEquals(new BigDecimal("25.00"), totalPrice);
//     }

//     @Test
//     void shouldCalculateTotalPriceWithNoProducts() {
//         ProdutoComboEntity lanche = new ProdutoComboEntity("1", 1, new BigDecimal("10.00"));

//         ComboEntity combo = new ComboEntity(lanche, null, null, null);

//         BigDecimal totalPrice = combo.getTotalPrice();
//         assertEquals(new BigDecimal("10.00"), totalPrice);
//     }

//     @Test
//     void shouldCalculateTotalPriceWithNoProducts_2() {
//         ProdutoComboEntity lanche = new ProdutoComboEntity("1", 1, new BigDecimal("10.00"));

//         ComboEntity combo = new ComboEntity(null, lanche, null, null);

//         BigDecimal totalPrice = combo.getTotalPrice();
//         assertEquals(new BigDecimal("10.00"), totalPrice);
//     }

//     @Test
//     void shouldCalculateTotalPriceWithNoProducts_3() {
//         ProdutoComboEntity lanche = new ProdutoComboEntity("1", 1, new BigDecimal("10.00"));

//         ComboEntity combo = new ComboEntity(null, null, lanche, null);

//         BigDecimal totalPrice = combo.getTotalPrice();
//         assertEquals(new BigDecimal("10.00"), totalPrice);
//     }

//     @Test
//     void shouldCalculateTotalPriceWithNoProducts_4() {
//         ProdutoComboEntity lanche = new ProdutoComboEntity("1", 1, new BigDecimal("10.00"));

//         ComboEntity combo = new ComboEntity(null, null, null, lanche);

//         BigDecimal totalPrice = combo.getTotalPrice();
//         assertEquals(new BigDecimal("10.00"), totalPrice);
//     }
// }
