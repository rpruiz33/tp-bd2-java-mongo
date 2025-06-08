package clases;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class TestConexion {
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            System.out.println("Conexión exitosa a MongoDB.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
