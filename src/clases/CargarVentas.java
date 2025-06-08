package clases;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.Arrays;

public class CargarVentas {
    public static void main(String[] args) {
        // Conexión moderna a MongoDB
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("farmacia");
            MongoCollection<Document> ventas = db.getCollection("ventas");

            ventas.drop(); // Limpiar colección

            // Venta 1
            Document venta1 = new Document("idVenta", 1)
                    .append("fecha", "2024-06-07")
                    .append("cliente", "Juan Perez")
                    .append("productos", Arrays.asList(
                            new Document("nombre", "Paracetamol").append("cantidad", 2).append("precio", 500),
                            new Document("nombre", "Ibuprofeno").append("cantidad", 1).append("precio", 800)
                    ))
                    .append("total", 1800);

            // Venta 2
            Document venta2 = new Document("idVenta", 2)
                    .append("fecha", "2024-06-07")
                    .append("cliente", "Ana García")
                    .append("productos", Arrays.asList(
                            new Document("nombre", "Amoxidal").append("cantidad", 1).append("precio", 1200)
                    ))
                    .append("total", 1200);

            ventas.insertMany(Arrays.asList(venta1, venta2));

            System.out.println("Ventas cargadas con éxito.");
        }
    }
}
