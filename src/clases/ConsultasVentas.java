package clases;

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
import java.util.List;

public class ConsultasVentas {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("farmacia");
            MongoCollection<Document> ventas = db.getCollection("ventas");

            LocalDate fechaDesde = LocalDate.of(2024, 6, 1);
            LocalDate fechaHasta = LocalDate.of(2024, 6, 30);

            ejecutarReportes(ventas, fechaDesde, fechaHasta);
        }
    }

    private static void ejecutarReportes(MongoCollection<Document> ventas, 
                                       LocalDate fechaDesde, 
                                       LocalDate fechaHasta) {
        System.out.println("\n=== REPORTES DE VENTAS ===");
        System.out.println("Período: " + fechaDesde + " al " + fechaHasta + "\n");

        // Reporte 1: Ventas total y por sucursal con IDs
        System.out.println("1. VENTAS TOTAL Y POR SUCURSAL (CON IDs)");
        ventasTotalesYSucursales(ventas, fechaDesde, fechaHasta);

        // Reporte 2: Ventas por obra social con IDs
        System.out.println("\n2. VENTAS POR OBRA SOCIAL (CON IDs)");
        ventasPorObraSocial(ventas, fechaDesde, fechaHasta);

        // Reporte 3: Cobranza total y por sucursal con IDs
        System.out.println("\n3. COBRANZA TOTAL Y POR SUCURSAL (CON IDs)");
        cobranzaTotalYSucursales(ventas, fechaDesde, fechaHasta);

        // Reporte 4: Ventas por tipo de producto con IDs
        System.out.println("\n4. VENTAS POR TIPO DE PRODUCTO (CON IDs)");
        ventasPorTipoProducto(ventas, fechaDesde, fechaHasta);

        // Reporte 5: Ranking monto vendido con IDs
        System.out.println("\n5. RANKING MONTO VENDIDO POR PRODUCTO/SUCURSAL (CON IDs)");
        rankingMontoVendido(ventas, fechaDesde, fechaHasta);

        // Reporte 6: Ranking cantidad productos con IDs
        System.out.println("\n6. RANKING CANTIDAD PRODUCTOS VENDIDOS (CON IDs)");
        rankingCantidadProductos(ventas, fechaDesde, fechaHasta);

        // Reporte 7: Ranking clientes total cadena con IDs
        System.out.println("\n7. RANKING CLIENTES (TOTAL CADENA CON IDs)");
        rankingClientesTotal(ventas, fechaDesde, fechaHasta);

        // Reporte 8: Ranking clientes por sucursal con IDs
        System.out.println("\n8. RANKING CLIENTES POR SUCURSAL (CON IDs)");
        rankingClientesPorSucursal(ventas, fechaDesde, fechaHasta);
    }

    private static void ventasTotalesYSucursales(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        // Consulta para obtener todas las ventas con sus IDs
        FindIterable<Document> todasVentas = ventas.find(
            and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            ))
            .projection(fields(include("idVenta", "total", "sucursal.nombre"), excludeId()));

        System.out.println("\nDetalle de todas las ventas:");
        System.out.println("ID Venta\tTotal\t\tSucursal");
        System.out.println("----------------------------------");
        for (Document doc : todasVentas) {
            // Se asume que sucursal.nombre siempre está presente o el valor predeterminado es "Sin sucursal"
            System.out.printf("%d\t\t$%.2f\t%s\n", 
                doc.getInteger("idVenta"),
                doc.getDouble("total"),
                doc.getEmbedded(Arrays.asList("sucursal", "nombre"), "Sin sucursal"));
        }

        // Totales por sucursal
        AggregateIterable<Document> porSucursal = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                // Directamente el resultado de $ifNull como _id
                new Document("$ifNull", Arrays.asList("$sucursal.nombre", "SIN_SUCURSAL")),
                sum("cantidadVentas", 1),
                sum("totalVentas", "$total"),
                push("idsVentas", "$idVenta")
            ),
            sort(Sorts.descending("totalVentas"))
        ));

        System.out.println("\nResumen por sucursal:");
        System.out.println("Sucursal\tCantidad\tTotal\t\tIDs Ventas");
        System.out.println("--------------------------------------------------");
        for (Document doc : porSucursal) {
            // Acceso directo a _id
            System.out.printf("%s\t%d\t\t$%.2f\t%s\n",
                doc.getString("_id"),
                doc.getInteger("cantidadVentas"),
                doc.getDouble("totalVentas"),
                doc.getList("idsVentas", Integer.class).toString());
        }
    }

    private static void ventasPorObraSocial(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        AggregateIterable<Document> resultado = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                // Directamente el resultado de $ifNull como _id
                new Document("$ifNull", Arrays.asList("$cliente.obraSocial.nombre", "PRIVADO")),
                sum("cantidadVentas", 1),
                sum("totalVentas", "$total"),
                push("idsVentas", "$idVenta")
            ),
            sort(Sorts.descending("totalVentas"))
        ));

        System.out.println("\nVentas por Obra Social:");
        System.out.println("Obra Social\tCantidad\tTotal\t\tIDs Ventas");
        System.out.println("--------------------------------------------------");
        for (Document doc : resultado) {
            System.out.printf("%s\t%d\t\t$%.2f\t%s\n",
                doc.getString("_id"),
                doc.getInteger("cantidadVentas"),
                doc.getDouble("totalVentas"),
                doc.getList("idsVentas", Integer.class).toString());
        }
    }

    private static void cobranzaTotalYSucursales(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        // Total general
        AggregateIterable<Document> totalGeneral = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                null,
                sum("totalCobranza", "$total"),
                push("idsVentas", "$idVenta")
            )
        ));

        System.out.println("\nCobranza total:");
        for (Document doc : totalGeneral) {
            System.out.printf("Total Cobranza: $%.2f\nIDs Ventas: %s\n",
                doc.getDouble("totalCobranza"),
                doc.getList("idsVentas", Integer.class).toString());
        }

        // Por sucursal
        AggregateIterable<Document> porSucursal = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                // Directamente el resultado de $ifNull como _id
                new Document("$ifNull", Arrays.asList("$sucursal.nombre", "SIN_SUCURSAL")),
                sum("totalCobranza", "$total"), // Ahora sí suma el total de ventas para la cobranza
                push("idsVentas", "$idVenta")
            ),
            sort(Sorts.descending("totalCobranza"))
        ));

        System.out.println("\nCobranza por sucursal:");
        System.out.println("Sucursal\tTotal\t\tIDs Ventas");
        System.out.println("------------------------------------------");
        for (Document doc : porSucursal) {
            System.out.printf("%s\t$%.2f\t%s\n",
                doc.getString("_id"),
                doc.getDouble("totalCobranza"),
                doc.getList("idsVentas", Integer.class).toString());
        }
    }

    private static void ventasPorTipoProducto(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        AggregateIterable<Document> resultado = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            unwind("$detalles"), 
            group(
                new Document("tipoProducto", 
                    new Document("$ifNull", Arrays.asList("$detalles.producto.tipo", "SIN_TIPO")) 
                ),
                sum("cantidadVentas", 1),
                sum("totalVentas", "$detalles.subtotal"), 
                push("idsVentas", "$idVenta"),
                addToSet("productosIds", "$detalles.producto.idProducto")
            ),
            sort(Sorts.descending("totalVentas"))
        ));

        System.out.println("\nVentas por Tipo de Producto:");
        System.out.println("Tipo Producto\t\tCantidad\tTotal\t\tIDs Ventas\t\tIDs Productos"); 
        System.out.println("----------------------------------------------------------------------------------"); 
        for (Document doc : resultado) {
            // Obtener el documento _id que contiene el tipoProducto
            Document tipoInfo = doc.get("_id", Document.class);
            String tipoProducto = tipoInfo.getString("tipoProducto");
            
            System.out.printf("%-15s\t%-8d\t$%-10.2f\t%-20s\t%s\n",
                tipoProducto,
                doc.getInteger("cantidadVentas"),
                doc.getDouble("totalVentas"),
                doc.getList("idsVentas", Integer.class).toString(),
                doc.getList("productosIds", Integer.class).toString());
        }
    }

    private static void rankingMontoVendido(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        AggregateIterable<Document> resultado = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            unwind("$detalles"), 
            group(
                new Document()
                    .append("producto", new Document("$ifNull", Arrays.asList("$detalles.producto.descripcion", "Producto Desconocido"))) 
                    .append("idProducto", new Document("$ifNull", Arrays.asList("$detalles.producto.idProducto", 0))) 
                    .append("sucursal", 
                        new Document("$ifNull", Arrays.asList("$sucursal.nombre", "SIN_SUCURSAL"))
                    ),
                sum("montoVendido", "$detalles.subtotal"), 
                sum("cantidadVendida", "$detalles.cantidad"), 
                push("idsVentas", "$idVenta")
            ),
            sort(Sorts.descending("montoVendido")),
            limit(10)
        ));

        System.out.println("\nTop 10 productos por monto vendido:");
        System.out.println("Producto (ID)\t\t\tSucursal\t\tMonto\t\tCantidad\tIDs Ventas");
        System.out.println("--------------------------------------------------------------------------------");
        for (Document doc : resultado) {
            Document idInfo = doc.get("_id", Document.class);
            System.out.printf("%-20s (%-5d)\t%-15s\t$%-10.2f\t%-8d\t%s\n",
                idInfo.getString("producto"),
                idInfo.getInteger("idProducto"),
                idInfo.getString("sucursal"),
                doc.getDouble("montoVendido"),
                doc.getInteger("cantidadVendida"),
                doc.getList("idsVentas", Integer.class).toString());
        }
    }

    private static void rankingCantidadProductos(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        AggregateIterable<Document> resultado = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            unwind("$detalles"), 
            group(
                new Document()
                    .append("producto", new Document("$ifNull", Arrays.asList("$detalles.producto.descripcion", "Producto Desconocido"))) 
                    .append("idProducto", new Document("$ifNull", Arrays.asList("$detalles.producto.idProducto", 0))), 
                sum("cantidadVendida", "$detalles.cantidad"), 
                sum("montoVendido", "$detalles.subtotal"), 
                push("idsVentas", "$idVenta")
            ),
            sort(Sorts.descending("cantidadVendida")),
            limit(10)
        ));

        System.out.println("\nTop 10 productos por cantidad vendida:");
        System.out.println("Producto (ID)\t\tCantidad\tMonto\t\tIDs Ventas");
        System.out.println("------------------------------------------------------------");
        for (Document doc : resultado) {
            Document idProducto = doc.get("_id", Document.class);
            System.out.printf("%s (%d)\t%d\t$%.2f\t%s\n",
                idProducto.getString("producto"),  // Cambiado de %d a %s para el nombre
                idProducto.getInteger("idProducto"),
                doc.getInteger("cantidadVendida"),
                doc.getDouble("montoVendido"),
                doc.getList("idsVentas", Integer.class).toString());
        }
    }

    private static void rankingClientesTotal(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        AggregateIterable<Document> resultado = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                new Document()
                    .append("cliente", new Document("$ifNull", Arrays.asList("$cliente.nombre", "Cliente Desconocido")))
                    .append("idCliente", new Document("$ifNull", Arrays.asList("$cliente.idCliente", 0))),
                sum("cantidadCompras", 1),
                sum("montoTotal", "$total"),
                push("idsVentas", "$idVenta")
            ),
            sort(Sorts.descending("montoTotal")),
            limit(10)
        ));

        System.out.println("\nTop 10 clientes por monto total:");
        System.out.println("Cliente (ID)\t\tCompras\tMonto Total\tIDs Ventas");
        System.out.println("--------------------------------------------------------");
        for (Document doc : resultado) {
            Document idCliente = doc.get("_id", Document.class);
            System.out.printf("%s (%d)\t%d\t$%.2f\t%s\n",
                idCliente.getString("cliente"),
                idCliente.getInteger("idCliente"),
                doc.getInteger("cantidadCompras"),
                doc.getDouble("montoTotal"),
                doc.getList("idsVentas", Integer.class).toString());
        }
    }

    private static void rankingClientesPorSucursal(MongoCollection<Document> ventas, 
            LocalDate fechaDesde, LocalDate fechaHasta) {
        
        AggregateIterable<Document> resultado = ventas.aggregate(Arrays.asList(
            match(and(
                gte("fecha", fechaDesde.format(DATE_FORMATTER)),
                lte("fecha", fechaHasta.format(DATE_FORMATTER))
            )),
            group(
                new Document()
                    .append("sucursal", 
                        new Document("$ifNull", Arrays.asList("$sucursal.nombre", "SIN_SUCURSAL"))
                    )
                    .append("cliente", new Document("$ifNull", Arrays.asList("$cliente.nombre", "Cliente Desconocido")))
                    .append("idCliente", new Document("$ifNull", Arrays.asList("$cliente.idCliente", 0))),
                sum("cantidadCompras", 1),
                sum("montoTotal", "$total"),
                push("idsVentas", "$idVenta")
            ),
            sort(Sorts.orderBy(
                Sorts.ascending("_id.sucursal"),
                Sorts.descending("montoTotal")
            )),
            group(
                "$_id.sucursal",
                push("clientes", new Document("cliente", "$_id.cliente")
                    .append("idCliente", "$_id.idCliente")
                    .append("compras", "$cantidadCompras")
                    .append("monto", "$montoTotal")
                    .append("idsVentas", "$idsVentas")
                )
            ),
            project(new Document("sucursal", "$_id")
                .append("clientes", new Document("$slice", Arrays.asList("$clientes", 5)))
            ),
            sort(Sorts.ascending("_id"))
        ));

        System.out.println("\nTop 5 clientes por sucursal:");
        for (Document doc : resultado) {
            System.out.println("\nSucursal: " + doc.getString("_id"));
            System.out.println("Cliente (ID)\t\tCompras\tMonto\t\tIDs Ventas");
            System.out.println("----------------------------------------------------");
            
            List<Document> clientes = doc.getList("clientes", Document.class);
            for (Document cliente : clientes) {
                System.out.printf("%s (%d)\t%d\t$%.2f\t%s\n",
                    cliente.getString("cliente"),
                    cliente.getInteger("idCliente"),
                    cliente.getInteger("compras"),
                    cliente.getDouble("monto"),
                    cliente.getList("idsVentas", Integer.class).toString());
            }
        }
    }
}
