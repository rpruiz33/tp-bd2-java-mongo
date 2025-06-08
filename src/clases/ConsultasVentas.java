package clases;

import org.bson.BsonNull;
import org.bson.Document;

import com.mongodb.client.*;
import com.mongodb.client.model.Sorts;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Projections.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class ConsultasVentas {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("farmacia");
            MongoCollection<Document> ventas = db.getCollection("ventas");

            LocalDate fechaDesde = LocalDate.of(2024, 6, 1);
            LocalDate fechaHasta = LocalDate.of(2024, 6, 30);

            System.out.println("\n----- Consulta 1: Ventas total y por sucursal -----");
            reporteVentasPorSucursal(ventas, fechaDesde, fechaHasta);

            System.out.println("\n----- Consulta 2: Ventas por obra social -----");
            reporteVentasPorObraSocial(ventas, fechaDesde, fechaHasta);

            System.out.println("\n----- Consulta 3: Cobranza total y por sucursal -----");
            reporteCobranzaPorSucursal(ventas, fechaDesde, fechaHasta);

            System.out.println("\n----- Consulta 4: Ventas por tipo de producto -----");
            reporteVentasPorTipoProducto(ventas, fechaDesde, fechaHasta);

            System.out.println("\n----- Consulta 5: Ranking monto vendido por producto y sucursal -----");
            rankingMontoVendido(ventas, fechaDesde, fechaHasta);

            System.out.println("\n----- Consulta 6: Ranking cantidad productos vendidos -----");
            rankingCantidadProductos(ventas, fechaDesde, fechaHasta);

            System.out.println("\n----- Consulta 7: Ranking compras por cliente (total cadena) -----");
            rankingComprasPorCliente(ventas, fechaDesde, fechaHasta);

            System.out.println("\n----- Consulta 8: Ranking compras por cliente y sucursal -----");
            rankingComprasPorClienteYSucursal(ventas, fechaDesde, fechaHasta);
        }
    }

    private static void reporteVentasPorSucursal(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> totalVentas = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(null, sum("totalVentas", 1))
        ));

        System.out.println("Total de ventas en toda la cadena:");
        for (Document doc : totalVentas) {
            System.out.println(doc.toJson());
        }

        AggregateIterable<Document> ventasPorSucursal = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group("$sucursal.nombre", sum("cantidadVentas", 1)),
            sort(Sorts.descending("cantidadVentas"))
        ));

        System.out.println("\nVentas por sucursal:");
        for (Document doc : ventasPorSucursal) {
            System.out.println(doc.toJson());
        }
    }

    private static void reporteVentasPorObraSocial(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> ventasPorOS = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                new Document("obraSocial",
                    new Document("$ifNull", Arrays.asList("$cliente.obraSocial.nombre", "Privado"))
                ),
                sum("cantidadVentas", 1)
            ),
            sort(Sorts.descending("cantidadVentas"))
        ));

        System.out.println("Ventas por obra social:");
        for (Document doc : ventasPorOS) {
            System.out.println(doc.toJson());
        }
    }

    private static void reporteCobranzaPorSucursal(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> totalCobranza = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(new BsonNull(), sum("totalCobranza", "$total"))
        ));

        System.out.println("Total cobranza en toda la cadena:");
        for (Document doc : totalCobranza) {
            System.out.println(doc.toJson());
        }

        AggregateIterable<Document> cobranzaPorSucursal = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group("$sucursal.nombre", sum("totalCobranza", "$total")),
            sort(Sorts.descending("totalCobranza"))
        ));

        System.out.println("\nCobranza por sucursal:");
        for (Document doc : cobranzaPorSucursal) {
            System.out.println(doc.toJson());
        }
    }

    private static void reporteVentasPorTipoProducto(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> ventasPorTipo = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            unwind("$productos"),
            group("$productos.tipo",
                sum("cantidadVentas", 1),
                sum("cantidadProductos", "$productos.cantidad")
            ),
            sort(Sorts.descending("cantidadVentas"))
        ));

        System.out.println("Ventas por tipo de producto:");
        for (Document doc : ventasPorTipo) {
            System.out.println(doc.toJson());
        }
    }

    private static void rankingMontoVendido(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> ranking = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            unwind("$productos"),
            group(
                new Document("sucursal", "$sucursal.nombre")
                    .append("producto", "$productos.nombre"),
                sum("montoTotal",
                    new Document("$multiply", Arrays.asList("$productos.precio", "$productos.cantidad"))
                )
            ),
            sort(Sorts.descending("montoTotal")),
            limit(10)
        ));

        System.out.println("Top 10 productos por monto vendido:");
        for (Document doc : ranking) {
            System.out.println(doc.toJson());
        }
    }

    private static void rankingCantidadProductos(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> ranking = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            unwind("$productos"),
            group(
                new Document("sucursal", "$sucursal.nombre")
                    .append("producto", "$productos.nombre"),
                sum("cantidadTotal", "$productos.cantidad")
            ),
            sort(Sorts.descending("cantidadTotal")),
            limit(10)
        ));

        System.out.println("Top 10 productos por cantidad vendida:");
        for (Document doc : ranking) {
            System.out.println(doc.toJson());
        }
    }

    private static void rankingComprasPorCliente(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> ranking = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group("$cliente.nombreCompleto",
                sum("totalCompras", "$total"),
                sum("cantidadCompras", 1)
            ),
            sort(Sorts.descending("totalCompras")),
            limit(10)
        ));

        System.out.println("Top 10 clientes por monto total de compras:");
        for (Document doc : ranking) {
            System.out.println(doc.toJson());
        }
    }

    private static void rankingComprasPorClienteYSucursal(MongoCollection<Document> ventas, LocalDate fechaDesde, LocalDate fechaHasta) {
        AggregateIterable<Document> ranking = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                new Document("sucursal", "$sucursal.nombre")
                    .append("cliente", "$cliente.nombreCompleto"),
                sum("totalCompras", "$total"),
                sum("cantidadCompras", 1)
            ),
            sort(Sorts.descending("totalCompras")),
            limit(10)
        ));

        System.out.println("Top 10 clientes por monto total de compras por sucursal:");
        for (Document doc : ranking) {
            System.out.println(doc.toJson());
        }
    }
}