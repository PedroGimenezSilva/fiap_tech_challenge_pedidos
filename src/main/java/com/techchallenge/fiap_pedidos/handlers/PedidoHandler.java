package com.techchallenge.fiap_pedidos.handlers;

import com.techchallenge.fiap_pedidos.adapters.controller.pedido.PedidoController;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.requests.pedido.AtualizarPedidoStatusDto;
import com.techchallenge.fiap_pedidos.core.requests.pedido.CriarPedidoDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/pedidos")
public class PedidoHandler {
    private final PedidoController pedidoController;

    public PedidoHandler(PedidoController pedidoController) {
        this.pedidoController = pedidoController;
    }

    @Operation(summary = "Cria novo pedido", description = "Cria um novo pedido na base de dados.")
    @PostMapping
    public ResponseEntity<PedidoEntity> create(@RequestBody CriarPedidoDto dto) {
        PedidoEntity pedido = pedidoController.cadastrarPedido(dto);
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @Operation(summary = "Busca pedido por id", description = "Busca pedido usando identificador na base de dados.")
    @GetMapping("/{id}")
    public ResponseEntity<PedidoEntity> getPedidoById(
            @PathVariable("id") String id
    ) {
        return Optional.of(id)
                .map(pedidoController::getById)
                .map(p -> new ResponseEntity<>(p, HttpStatus.OK))
                .orElse(ResponseEntity.notFound().build())
                ;
    }

    @Operation(summary = "Listar todos os pedidos", description = "Lista todos os pedidos passando um status como parâmetro opcional")
    @GetMapping()
    public ResponseEntity<List<PedidoEntity>> getPedidos(
            @RequestParam(value = "status", required = false) String status
    ) {
        List<PedidoEntity> pedidos = pedidoController.getAll();
        return ResponseEntity.ok().body(pedidos);
    }

    @Operation(summary = "Atualiza status do pedido por id", description = "Atualizar status do pedido na base de dados por id.")
    @PatchMapping("/{id}")
    public ResponseEntity<PedidoEntity> patchPedidoStatus(
            @PathVariable("id") String id,
            @RequestBody AtualizarPedidoStatusDto atualizarPedidoStatusDto
    ) {
        PedidoEntity pedido = pedidoController.atualizarStatus(id, atualizarPedidoStatusDto.status());
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }
}
