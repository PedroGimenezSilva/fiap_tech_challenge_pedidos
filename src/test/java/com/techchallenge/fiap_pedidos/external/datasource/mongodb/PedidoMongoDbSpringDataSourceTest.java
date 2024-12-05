package com.techchallenge.fiap_pedidos.external.datasource.mongodb;

import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoMongoDbSpringDataSourceTest {

    @Mock
    private PedidoSpringRepository repository;

    @InjectMocks
    private PedidoMongoDbSpringDataSource dataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInsertPedido() {
        // Arrange
        PedidoDto pedido = new PedidoDto(null, PedidoStatus.INICIAL, "clientId", List.of(), null, null);
        PedidoDto savedPedido = pedido.withId(UUID.randomUUID().toString());
        when(repository.save(any(PedidoDto.class))).thenReturn(savedPedido);

        // Act
        PedidoDto result = dataSource.insertPedido(pedido);

        // Assert
        assertNotNull(result.id());
        assertEquals(savedPedido.id(), result.id());
        verify(repository, times(1)).save(any(PedidoDto.class));
    }

    @Test
    void testSavePedido() {
        // Arrange
        PedidoDto pedido = new PedidoDto("pedido-id", PedidoStatus.INICIAL, "clientId", List.of(), null, null);
        when(repository.save(pedido)).thenReturn(pedido);

        // Act
        PedidoDto result = dataSource.savePedido(pedido);

        // Assert
        assertEquals("pedido-id", result.id());
        verify(repository, times(1)).save(pedido);
    }

    @Test
    void testGetPedidoById() {
        // Arrange
        String id = "pedido-id";
        PedidoDto pedido = new PedidoDto(id, PedidoStatus.INICIAL, "clientId", List.of(), null, null);
        when(repository.findById(id)).thenReturn(Optional.of(pedido));

        // Act
        PedidoDto result = dataSource.getPedidoById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.id());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testGetPedidoByIdNotFound() {
        // Arrange
        String id = "non-existent-id";
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act
        PedidoDto result = dataSource.getPedidoById(id);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testFindAll() {
        // Arrange
        List<PedidoDto> pedidos = List.of(
                new PedidoDto("pedido-1", PedidoStatus.INICIAL, "client1", List.of(), null, null),
                new PedidoDto("pedido-2", PedidoStatus.PRONTO, "client2", List.of(), null, null)
        );
        when(repository.findAll()).thenReturn(pedidos);

        // Act
        List<PedidoDto> result = dataSource.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindAllInStatus() {
        // Arrange
        List<PedidoStatus> statuses = List.of(PedidoStatus.INICIAL, PedidoStatus.PRONTO);
        List<PedidoDto> pedidos = List.of(
                new PedidoDto("pedido-1", PedidoStatus.INICIAL, "client1", List.of(), null, null),
                new PedidoDto("pedido-2", PedidoStatus.PRONTO, "client2", List.of(), null, null)
        );
        when(repository.findAllByStatusIn(statuses)).thenReturn(pedidos);

        // Act
        List<PedidoDto> result = dataSource.findAllInStatus(statuses);

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAllByStatusIn(statuses);
    }
}
