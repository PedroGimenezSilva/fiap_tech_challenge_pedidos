// package com.techchallenge.fiap_pedidos.adapters.gateway.pedido;


// import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
// import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
// import com.techchallenge.fiap_pedidos.core.exceptions.InvalidPedidoException;
// import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
// import com.techchallenge.fiap_pedidos.pkg.interfaces.IPedidoDataSource;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;

// import java.time.OffsetDateTime;
// import java.util.List;

// import static com.techchallenge.fiap_pedidos.helpers.PedidoHelper.criarPedidoValido;
// import static com.techchallenge.fiap_pedidos.helpers.PedidoHelper.newPedidoDto;
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// class PedidoGatewayTest {

//     @Mock
//     private IPedidoDataSource dataSource;

//     private PedidoGateway pedidoGateway;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//         pedidoGateway = new PedidoGateway(dataSource);
//     }

//     @Test
//     void testCriarPedidoComIdLancaExcecao() {
//         PedidoEntity pedido = criarPedidoValido("id").withStatus(PedidoStatus.INICIAL).withUpdatedAt(OffsetDateTime.now());

//         InvalidPedidoException exception = assertThrows(InvalidPedidoException.class, () -> pedidoGateway.criar(pedido));
//         assertEquals("Pedido já existente", exception.getMessage());
//     }

//     @Test
//     void testCriarPedido() {
//         PedidoEntity pedido = criarPedidoValido(null);
//         PedidoDto savedPedidoDto = newPedidoDto();

//         when(dataSource.insertPedido(any(PedidoDto.class))).thenReturn(savedPedidoDto);

//         PedidoEntity result = pedidoGateway.criar(pedido);

//         assertNotNull(result);
//         assertEquals("123", result.id());
//         verify(dataSource, times(1)).insertPedido(any(PedidoDto.class));
//     }

//     @Test
//     void testAtualizarPedidoSemIdLancaExcecao() {
//         PedidoEntity pedido = criarPedidoValido(null);

//         InvalidPedidoException exception = assertThrows(InvalidPedidoException.class, () -> pedidoGateway.atualizar(pedido));
//         assertEquals("Pedido não existente", exception.getMessage());
//     }

//     @Test
//     void testAtualizarPedido() {
//         PedidoEntity pedido = criarPedidoValido("123").withStatus(PedidoStatus.INICIAL).withUpdatedAt(OffsetDateTime.now());

//         PedidoDto updatedPedidoDto = newPedidoDto();

//         when(dataSource.savePedido(any(PedidoDto.class))).thenReturn(updatedPedidoDto);

//         PedidoEntity result = pedidoGateway.atualizar(pedido);

//         assertNotNull(result);
//         assertEquals(PedidoStatus.INICIAL, result.status());
//         verify(dataSource, times(1)).savePedido(any(PedidoDto.class));
//     }

//     @Test
//     void testGetById() {
//         PedidoDto pedidoDto = newPedidoDto();

//         when(dataSource.getPedidoById("123")).thenReturn(pedidoDto);

//         PedidoEntity result = pedidoGateway.getById("123");

//         assertNotNull(result);
//         assertEquals("123", result.id());
//         verify(dataSource, times(1)).getPedidoById("123");
//     }

//     @Test
//     void testGetByIdNotFound() {
//         when(dataSource.getPedidoById("999")).thenReturn(null);

//         PedidoEntity result = pedidoGateway.getById("999");

//         assertNull(result);
//         verify(dataSource, times(1)).getPedidoById("999");
//     }

//     @Test
//     void testFindAll() {
//         List<PedidoDto> pedidosDto = List.of(
//                 newPedidoDto(),
//                 newPedidoDto()
//         );

//         when(dataSource.findAll()).thenReturn(pedidosDto);

//         List<PedidoEntity> result = pedidoGateway.findAll();

//         assertNotNull(result);
//         assertEquals(2, result.size());
//         verify(dataSource, times(1)).findAll();
//     }

//     @Test
//     void testFindAllInStatus() {
//         List<PedidoDto> pedidosDto = List.of(
//                 newPedidoDto()
//         );

//         when(dataSource.findAllInStatus(anyList())).thenReturn(pedidosDto);

//         List<PedidoEntity> result = pedidoGateway.findAllInStatus(List.of(PedidoStatus.INICIAL));

//         assertNotNull(result);
//         assertEquals(1, result.size());
//         assertEquals(PedidoStatus.INICIAL, result.get(0).status());
//         verify(dataSource, times(1)).findAllInStatus(anyList());
//     }
// }
