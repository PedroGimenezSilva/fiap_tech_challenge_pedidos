package com.techchallenge.fiap_pedidos.bdd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoEntity;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.response.Response;

import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StepDefinition {
    ObjectMapper mapper = new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // Ignore unrecognized properties
            .registerModule(new JavaTimeModule());

    private final String ENDPOINT_API_PEDIDO = "http://localhost:8081/api/pedidos";
    private PedidoEntity pedidoEntity;
    private List<PedidoEntity> pedidoEntityList;

    @Quando("crio um pedido")
    public void crioUmPedido() throws JsonProcessingException {
        String requestBody = """
                    {
                        "client_id": "1",
                        "combos": [
                            {
                                "lanche": {
                                    "id_produto": "1",
                                    "quantity": 1,
                                    "price": 10
                                },
                                "acompanhamento": {
                                    "id_produto": "2",
                                    "quantity": 2,
                                    "price": 10
                                },
                                "bebida": {
                                    "id_produto": "3",
                                    "quantity": 1,
                                    "price": 10
                                },
                                "sobremesa": {
                                    "id_produto": "4",
                                    "quantity": 1,
                                    "price": 10
                                }
                            }
                        ]
                    }
                """;

        // Make the POST request
        Response response = given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Set the body of the request
                .when()
                .post(ENDPOINT_API_PEDIDO) // Endpoint to hit
                .then()
                .statusCode(201) // Validate the response status code (change as per your API)
                .extract()
                .response(); // Extract the response

        pedidoEntity = mapper.readValue(response.asString(), PedidoEntity.class);
    }

    @Então("vejo que o pedido foi criado")
    public void vejoQueOPedidoFoiCriado() {
        assertNotNull(pedidoEntity);
        assertNotNull(pedidoEntity.id());
        assertEquals(PedidoStatus.INICIAL, pedidoEntity.status());
    }

    @Dado("que tenho um pedido criado no status inicial")
    public void queTenhoUmPedidoCriadoNoStatusInicial() throws JsonProcessingException {
        crioUmPedido();
    }

    @Quando("eu consulto a lista de pedidos")
    public void euConsultoAListaDePedidos() throws JsonProcessingException {
        Response response = given()
                .when()
                .get(ENDPOINT_API_PEDIDO);
        pedidoEntityList = mapper.readValue(response.asString(), new TypeReference<>() {
        });
    }

    @Então("o pedido não está listado")
    public void oPedidoNãoEstáListado() {
        assertTrue(pedidoEntityList.stream().filter(it -> Objects.equals(it.id(), pedidoEntity.id())).toList().isEmpty());
    }

    @Quando("atualizo o seu estado para o estado recebido")
    public void atualizoOSeuEstadoParaOEstadoRecebido() {
        // Define the request payload
        String requestBody = "{\n" +
                "    \"status\": \"RECEBIDO\"\n" +
                "}";

        // Perform PATCH request
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch(ENDPOINT_API_PEDIDO + "/" + pedidoEntity.id());
    }

    @Então("o pedido está listado")
    public void oPedidoEstáListado() {
        assertFalse(pedidoEntityList.stream().filter(it -> Objects.equals(it.id(), pedidoEntity.id())).toList().isEmpty());
    }
}
