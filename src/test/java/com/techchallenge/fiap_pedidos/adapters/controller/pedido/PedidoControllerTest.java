// package com.techchallenge.fiap_pedidos.adapters.controller.pedido;

// import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
// import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
// import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarPedidoDto;
// import com.techchallenge.fiap_pedidos.core.usecases.PedidoUseCase;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.util.List;

// import static com.techchallenge.fiap_pedidos.helpers.PedidoHelper.criarPedidoDto;
// import static com.techchallenge.fiap_pedidos.helpers.PedidoHelper.criarPedidoEntity;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// class PedidoControllerTest {

//     @Mock
//     private PedidoUseCase pedidoUseCase;

//     private PedidoController pedidoController;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         pedidoController = new PedidoController(pedidoUseCase);
//     }

//     @Test
//     void testCadastrarPedido() {
//         CriarPedidoDto criarPedidoDto = criarPedidoDto();
//         PedidoEntity pedidoEntity = criarPedidoEntity();

//         when(pedidoUseCase.criarPedido(criarPedidoDto)).thenReturn(pedidoEntity);

//         PedidoEntity result = pedidoController.cadastrarPedido(criarPedidoDto);

//         assertNotNull(result);
//         assertEquals("123", result.id());
//         verify(pedidoUseCase, times(1)).criarPedido(criarPedidoDto);
//     }

//     @Test
//     void testAtualizarStatus() {
//         PedidoEntity pedidoEntity = criarPedidoEntity().withStatus(PedidoStatus.PRONTO);

//         when(pedidoUseCase.atualizarStatus("123", PedidoStatus.PRONTO)).thenReturn(pedidoEntity);

//         PedidoEntity result = pedidoController.atualizarStatus("123", PedidoStatus.PRONTO);

//         assertNotNull(result);
//         assertEquals(PedidoStatus.PRONTO, result.status());
//         verify(pedidoUseCase, times(1)).atualizarStatus("123", PedidoStatus.PRONTO);
//     }

//     @Test
//     void testGetById() {
//         PedidoEntity pedidoEntity = criarPedidoEntity();

//         when(pedidoUseCase.buscarPorId("123")).thenReturn(pedidoEntity);

//         PedidoEntity result = pedidoController.getById("123");

//         assertNotNull(result);
//         assertEquals("123", result.id());
//         verify(pedidoUseCase, times(1)).buscarPorId("123");
//     }

//     @Test
//     void testGetByIdNotFound() {
//         when(pedidoUseCase.buscarPorId("999")).thenReturn(null);

//         PedidoEntity result = pedidoController.getById("999");

//         assertNull(result);
//         verify(pedidoUseCase, times(1)).buscarPorId("999");
//     }

//     @Test
//     void testGetAll() {
//         PedidoEntity pedido1 = criarPedidoEntity();

//         when(pedidoUseCase.buscarTodos()).thenReturn(List.of(pedido1));

//         List<PedidoEntity> result = pedidoController.getAll();

//         assertNotNull(result);
//         assertEquals(1, result.size());
//         verify(pedidoUseCase, times(1)).buscarTodos();
//     }
// }
