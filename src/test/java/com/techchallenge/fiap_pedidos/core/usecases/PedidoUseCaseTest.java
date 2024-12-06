// package com.techchallenge.fiap_pedidos.core.usecases;

// import com.techchallenge.fiap_pedidos.adapters.gateway.pedido.PedidoGateway;
// import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
// import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
// import com.techchallenge.fiap_pedidos.core.exceptions.PedidoNotFoundException;
// import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarPedidoDto;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.List;

// import static com.techchallenge.fiap_pedidos.helpers.PedidoHelper.criarPedidoDto;
// import static com.techchallenge.fiap_pedidos.helpers.PedidoHelper.criarPedidoEntity;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// class PedidoUseCaseTest {

//     @Mock
//     private PedidoGateway pedidoGateway;

//     private PedidoUseCase pedidoUseCase;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         pedidoUseCase = new PedidoUseCase(pedidoGateway);
//     }

//     @Test
//     void testCriarPedido() {
//         CriarPedidoDto criarPedidoDto = criarPedidoDto();
//         PedidoEntity pedidoEntity = criarPedidoEntity();

//         when(pedidoGateway.criar(any(PedidoEntity.class))).thenReturn(pedidoEntity);

//         PedidoEntity result = pedidoUseCase.criarPedido(criarPedidoDto);

//         assertNotNull(result);
//         assertEquals("123", result.id());
//         assertEquals(PedidoStatus.INICIAL, result.status());
//         verify(pedidoGateway, times(1)).criar(any(PedidoEntity.class));
//     }

//     @Test
//     void testBuscarPorId() {
//         PedidoEntity pedidoEntity = criarPedidoEntity();

//         when(pedidoGateway.getById("123")).thenReturn(pedidoEntity);

//         PedidoEntity result = pedidoUseCase.buscarPorId("123");

//         assertNotNull(result);
//         assertEquals("123", result.id());
//         verify(pedidoGateway, times(1)).getById("123");
//     }

//     @Test
//     void testBuscarPorIdNotFound() {
//         when(pedidoGateway.getById("999")).thenReturn(null);

//         PedidoEntity result = pedidoUseCase.buscarPorId("999");

//         assertNull(result);
//         verify(pedidoGateway, times(1)).getById("999");
//     }

//     @Test
//     void testBuscarTodos() {
//         PedidoEntity pedido1 = criarPedidoEntity().withStatus(PedidoStatus.RECEBIDO);
//         PedidoEntity pedido2 = criarPedidoEntity().withStatus(PedidoStatus.PRONTO);

//         when(pedidoGateway.findAllInStatus(anyList())).thenReturn(List.of(pedido1, pedido2));

//         List<PedidoEntity> result = pedidoUseCase.buscarTodos();

//         assertNotNull(result);
//         assertEquals(2, result.size());
//         assertEquals(PedidoStatus.PRONTO, result.get(0).status());
//         assertEquals(PedidoStatus.RECEBIDO, result.get(1).status());
//         verify(pedidoGateway, times(1)).findAllInStatus(anyList());
//     }
    
//     @Test
//     void testAtualizarStatus() {
//         PedidoEntity pedido = criarPedidoEntity();

//         when(pedidoGateway.getById("123")).thenReturn(pedido);
//         when(pedidoGateway.atualizar(any(PedidoEntity.class))).thenReturn(pedido.withStatus(PedidoStatus.PRONTO));

//         PedidoEntity result = pedidoUseCase.atualizarStatus("123", PedidoStatus.PRONTO);

//         assertNotNull(result);
//         assertEquals(PedidoStatus.PRONTO, result.status());
//         verify(pedidoGateway, times(1)).atualizar(any(PedidoEntity.class));
//     }

//     @Test
//     void testAtualizarStatusNotFound() {
//         when(pedidoGateway.getById("999")).thenReturn(null);

//         assertThrows(PedidoNotFoundException.class, () -> pedidoUseCase.atualizarStatus("999", PedidoStatus.PRONTO));
//         verify(pedidoGateway, never()).atualizar(any(PedidoEntity.class));
//     }
// }
