package com.techchallenge.fiap_pedidos.external.datasource.mongodb;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.techchallenge.fiap_pedidos.core.entities.pedido.PedidoStatus;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.ComboDto;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.PedidoDto;
import com.techchallenge.fiap_pedidos.pkg.dto.pedido.ProdutoComboDto;
import com.techchallenge.fiap_pedidos.pkg.interfaces.IPedidoDataSource;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class PedidoMongoDbDataSource implements IPedidoDataSource {
    private final MongoClient client;
    private final MongoCollection<Document> collection;

    public PedidoMongoDbDataSource(@Value("${mongo.connection}") String connectionString,
                                   @Value("${mongo.database}") String database) {
        this.client = MongoClients.create(connectionString);
        MongoDatabase mongoDatabase = client.getDatabase(database);
        this.collection = mongoDatabase.getCollection("pedidos");
    }

    @Override
    public PedidoDto insertPedido(PedidoDto pedido) {
        var id = UUID.randomUUID().toString();
        Document document = convertPedidoToDocument(id, pedido);
        collection.insertOne(document);

        return pedido.withId(id);
    }

    public PedidoDto savePedido(PedidoDto pedido) {

        var id = pedido.id();
        Document updatedDoc = convertPedidoToDocument(id, pedido);
        Bson filter = Filters.eq("id", pedido.id());

        collection.replaceOne(filter, updatedDoc);

        return pedido;
    }

    private Document convertPedidoToDocument(String id, PedidoDto pedido) {
        System.out.println(id);
        return new Document()
                .append("id", id)
                .append("status", pedido.status().toString())
                .append("clientId", pedido.clientId())
                .append("combos", pedido.combos().stream().map(this::convertComboToDocument).toList())
                .append("createdAt", pedido.createdAt().toString())
                .append("updatedAt", pedido.updatedAt().toString());
    }

    private Document convertComboToDocument(ComboDto combo) {
        return new Document()
                .append("lanche", convertProdutoComboToDocument(combo.lanche()))
                .append("acompanhamento", convertProdutoComboToDocument(combo.acompanhamento()))
                .append("bebida", convertProdutoComboToDocument(combo.bebida()))
                .append("sobremesa", convertProdutoComboToDocument(combo.sobremesa()));
    }

    private Document convertProdutoComboToDocument(ProdutoComboDto produto) {
        return new Document()
                .append("idProduto", produto.idProduto())
                .append("quantity", produto.quantity())
                .append("price", produto.price().toString());
    }

    public PedidoDto getPedidoById(String id) {
        Bson filter = Filters.eq("id", id);
        Document doc = collection.find(filter).first();

        if (doc != null) {
            return convertDocumentToPedido(doc);
        } else {
            return null;
        }
    }

    @Override
    public List<PedidoDto> findAll() {
        List<PedidoDto> pedidos = new ArrayList<>();
        FindIterable<Document> docs = collection.find();
        for (Document doc : docs) {
            pedidos.add(convertDocumentToPedido(doc));
        }
        return pedidos;
    }

    @Override
    public List<PedidoDto> findAllInStatus(List<PedidoStatus> statuses) {
        Bson filter = Filters.in("status", statuses);
        List<PedidoDto> pedidos = new ArrayList<>();
        FindIterable<Document> docs = collection.find(filter);
        for (Document doc : docs) {
            pedidos.add(convertDocumentToPedido(doc));
        }
        return pedidos;
    }

    private PedidoDto convertDocumentToPedido(Document doc) {
        String id = doc.getString("id");
        PedidoStatus status = PedidoStatus.valueOf(doc.getString("status"));
        String clientId = doc.getString("clientId");
        List<Document> comboDocs = (List<Document>) doc.get("combos");
        List<ComboDto> combos = new ArrayList<>();
        for (Document comboDoc : comboDocs) {
            combos.add(convertDocumentToCombo(comboDoc));
        }
        OffsetDateTime createdAt = OffsetDateTime.parse(doc.getString("createdAt"), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        OffsetDateTime updatedAt = OffsetDateTime.parse(doc.getString("updatedAt"), DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        return new PedidoDto(id, status, clientId, combos, createdAt, updatedAt);
    }

    private ComboDto convertDocumentToCombo(Document doc) {
        ProdutoComboDto lanche = convertDocumentToProdutoCombo((Document) doc.get("lanche"));
        ProdutoComboDto acompanhamento = convertDocumentToProdutoCombo((Document) doc.get("acompanhamento"));
        ProdutoComboDto bebida = convertDocumentToProdutoCombo((Document) doc.get("bebida"));
        ProdutoComboDto sobremesa = convertDocumentToProdutoCombo((Document) doc.get("sobremesa"));

        return new ComboDto(lanche, acompanhamento, bebida, sobremesa);
    }

    private ProdutoComboDto convertDocumentToProdutoCombo(Document doc) {
        String idProduto = doc.getString("idProduto");
        Integer quantity = doc.getInteger("quantity");
        BigDecimal price = new BigDecimal(doc.getString("price"));

        return new ProdutoComboDto(idProduto, quantity, price);
    }
}
