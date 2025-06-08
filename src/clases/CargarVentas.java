package clases;

import clases.FarmaciaData;
import clases.LocalDateAdapter;
import clases.Venta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.WriteModel;

import org.bson.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CargarVentas {
    public static void main(String[] args) {
        try {
            // 1. Leer el archivo JSON
            System.out.println("Leyendo archivo JSON...");
            String json = new String(Files.readAllBytes(Paths.get("farmacia_data.json")));

            // 2. Deserializar JSON a objetos Java
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .setPrettyPrinting()
                    .create();

            FarmaciaData farmaciaData = gson.fromJson(json, FarmaciaData.class);
            List<Venta> ventas = farmaciaData.getVentas();
            System.out.println("Total de ventas a insertar: " + ventas.size());

            // 3. Conexión a MongoDB
            try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
                MongoDatabase database = mongoClient.getDatabase("farmacia");
                MongoCollection<Document> collection = database.getCollection("ventas");
                
                // Limpiar colección existente
                collection.drop();
                
                // 4. Inserción masiva optimizada
                List<WriteModel<Document>> bulkOperations = new ArrayList<>();
                
                for (Venta venta : ventas) {
                    String ventaJson = gson.toJson(venta);
                    bulkOperations.add(new InsertOneModel<>(Document.parse(ventaJson)));
                    
                    if (bulkOperations.size() >= 100) {
                        collection.bulkWrite(bulkOperations, new BulkWriteOptions().ordered(false));
                        bulkOperations.clear();
                    }
                }
                
                if (!bulkOperations.isEmpty()) {
                    collection.bulkWrite(bulkOperations);
                }
                
                System.out.println( ventas.size() + " ventas insertadas.");
                System.out.println("Total documentos: " + collection.countDocuments());
            }

        } catch (IOException | MongoBulkWriteException e) {
            e.printStackTrace();
        }
    }
}