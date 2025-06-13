package test;

import clases.*;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.module.ModuleDescriptor.Modifier;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        FarmaciaData farmaciaData = new FarmaciaData();

        // Domicilios
        Domicilio domicilio1 = new Domicilio(1, "Calle 9 de Julio", 2453, "Almirante Brown", "Buenos Aires");
        Domicilio domicilio2 = new Domicilio(2, "Calle Rio Negro", 3180, "Lomas de Zamora", "Buenos Aires");
        Domicilio domicilio3 = new Domicilio(3, "Calle Sarmiento", 2732, "Retiro", "Ciudad Autónoma de Buenos Aires");
        Domicilio domicilio4 = new Domicilio(4, "Av Corrientes", 1120, "Belgrano", "Ciudad Autónoma de Buenos Aires");
        Domicilio domicilio5 = new Domicilio(5, "Calle San Martín", 1589, "Lanus", "Buenos Aires");

        farmaciaData.agregarDomicilio(domicilio1);
        farmaciaData.agregarDomicilio(domicilio2);
        farmaciaData.agregarDomicilio(domicilio3);
        farmaciaData.agregarDomicilio(domicilio4);
        farmaciaData.agregarDomicilio(domicilio5);

        // Obras sociales
        ObraSocial os1 = new ObraSocial(1, "OSDE");
        ObraSocial os2 = new ObraSocial(2, "Swiss Medical");
        ObraSocial os3 = new ObraSocial(3, "Galeno");
        ObraSocial os4 = new ObraSocial(4, "Medicus");
        farmaciaData.agregarObraSocial(os1);
        farmaciaData.agregarObraSocial(os2);
        farmaciaData.agregarObraSocial(os3);
        farmaciaData.agregarObraSocial(os4);

        // Empleados
        Empleado encargado1 = new Empleado(1, "Maria", "Pérez", "12.325.682", "20-12345-5", "12834", os2, domicilio2, null);
        Empleado encargado2 = new Empleado(2, "Ernesto", "Martinez", "14.568.987", "20-54321-7", "88746", os2, domicilio2, null);
        Empleado empleado1 = new Empleado(3, "Juan", "Pérez", "28.345.672", "20-28345672-5", "58716", os2, domicilio2, null);
        Empleado empleado2 = new Empleado(4, "María", "González", "33.876.904", "27-33876904-3", "48397", os3, domicilio3, null);
        Empleado empleado3 = new Empleado(5, "Pedro", "López", "39.112.845", "20-39112845-9", "78195", os1, domicilio4, null);
        Empleado empleado4 = new Empleado(6, "Gustavo", "Gimenez", "12.182.942", "20-39218865-3", "12345", os1, domicilio5, null);
       
        List<Empleado> empleadosSucursal1 = new ArrayList<>();
        empleadosSucursal1.add(encargado1);
        empleadosSucursal1.add(empleado1);
        empleadosSucursal1.add(empleado2);
        
        List<Empleado> empleadosSucursal2 = new ArrayList<>();
        empleadosSucursal2.add(encargado2);
        empleadosSucursal2.add(empleado3);
        empleadosSucursal2.add(empleado4);
        
        // Sucursales
        Sucursal sucursal1 = new Sucursal(1, "Sucursal Central", domicilio1, empleadosSucursal1, encargado1);
        Sucursal sucursal2 = new Sucursal(2, "Sucursal Norte", domicilio3, empleadosSucursal2, encargado2);
        
        farmaciaData.agregarEmpleado(encargado1);
        farmaciaData.agregarEmpleado(encargado2);
        farmaciaData.agregarEmpleado(empleado1);
        farmaciaData.agregarEmpleado(empleado2);
        farmaciaData.agregarEmpleado(empleado3);
        farmaciaData.agregarEmpleado(empleado4);
        
        farmaciaData.agregarSucursal(sucursal1);
        farmaciaData.agregarSucursal(sucursal2);

        // Clientes
        Cliente cliente1 = new Cliente(1, "Laura", "Gómez", domicilio1, os1);
        Cliente cliente2 = new Cliente(2, "Carlos", "Rodríguez", domicilio2, os2);
        Cliente cliente3 = new Cliente(3, "Ana", "Martínez", domicilio3, os3);
        Cliente cliente4 = new Cliente(4, "Sofía", "Fernández", domicilio4, os4);
        Cliente cliente5 = new Cliente(5, "Miguel", "Suárez", domicilio1, os2);
        Cliente cliente6 = new Cliente(6, "Natalia", "Vega", domicilio2, os3);
        Cliente cliente7 = new Cliente(7, "Diego", "Ramírez", domicilio3, os1);
        Cliente cliente8 = new Cliente(8, "Valentina", "Silva", domicilio4, os4);
        Cliente cliente9 = new Cliente(9, "Jorge", "Alonso", domicilio1, os1);

        farmaciaData.agregarCliente(cliente1);
        farmaciaData.agregarCliente(cliente2);
        farmaciaData.agregarCliente(cliente3);
        farmaciaData.agregarCliente(cliente4);
        farmaciaData.agregarCliente(cliente5);
        farmaciaData.agregarCliente(cliente6);
        farmaciaData.agregarCliente(cliente7);
        farmaciaData.agregarCliente(cliente8);
        farmaciaData.agregarCliente(cliente9);

        // Laboratorios
        Laboratorio lab1 = new Laboratorio(1, "Bayer", "BAY");
        Laboratorio lab2 = new Laboratorio(2, "Pfizer", "PFZ");
        Laboratorio lab3 = new Laboratorio(3, "Astrazeneca", "AZN");
        farmaciaData.agregarLaboratorio(lab1);
        farmaciaData.agregarLaboratorio(lab2);
        farmaciaData.agregarLaboratorio(lab3);

        // Productos
        Producto prod1 = new Producto(1, "Paracetamol", 500.0, "Prct250", lab1, "Medicamento");
        Producto prod2 = new Producto(2, "Ibuprofeno", 700.0, "Ibu500", lab2, "Medicamento");
        Producto prod3 = new Producto(3, "Amoxicilina", 1500.0, "Amox750", lab3, "Medicamento");
        Producto prod4 = new Producto(4, "Nivea Men", 400.0, "NvM01", lab1, "Perfumería");
        Producto prod5 = new Producto(5, "Rexona Antibacterial", 900.0, "Rxn01", lab2, "Perfumería");
        Producto prod6 = new Producto(6, "Aspirina", 300.0, "Asp100", lab1, "Medicamento");
        Producto prod7 = new Producto(7, "Jabón Dove", 350.0, "JabDove", lab3, "Higiene");
        Producto prod8 = new Producto(8, "Shampoo Head&Shoulders", 600.0, "ShHS", lab2, "Higiene");
        
        farmaciaData.agregarProducto(prod1);
        farmaciaData.agregarProducto(prod2);
        farmaciaData.agregarProducto(prod3);
        farmaciaData.agregarProducto(prod4);
        farmaciaData.agregarProducto(prod5);
        farmaciaData.agregarProducto(prod6);
        farmaciaData.agregarProducto(prod7);
        farmaciaData.agregarProducto(prod8);

        // Formas de pago
        FormaPago fp1 = new FormaPago(1, "Efectivo");
        FormaPago fp2 = new FormaPago(2, "Tarjeta Crédito");
        FormaPago fp3 = new FormaPago(3, "Tarjeta Débito");
        FormaPago fp4 = new FormaPago(4, "Transferencia Bancaria");
        farmaciaData.agregarFormaPago(fp1);
        farmaciaData.agregarFormaPago(fp2);
        farmaciaData.agregarFormaPago(fp3);
        farmaciaData.agregarFormaPago(fp4);

        // Detalle Venta
        List<DetalleVenta> detalles1 = new ArrayList<>();
        List<DetalleVenta> detalles2 = new ArrayList<>();
        List<DetalleVenta> detalles3 = new ArrayList<>();
        List<DetalleVenta> detalles4 = new ArrayList<>();

        // Detalles completos para cada venta
        detalles1.add(new DetalleVenta(1, prod1, 2, prod1.getPrecio(), prod1.getPrecio() * 2)); // $1000
        detalles1.add(new DetalleVenta(2, prod2, 1, prod2.getPrecio(), prod2.getPrecio() * 1)); // $700
        detalles1.add(new DetalleVenta(3, prod3, 3, prod3.getPrecio(), prod3.getPrecio() * 3)); // $4500
        // Total venta1: $6200

        detalles2.add(new DetalleVenta(4, prod4, 2, prod4.getPrecio(), prod4.getPrecio() * 2)); // $800
        detalles2.add(new DetalleVenta(5, prod5, 4, prod5.getPrecio(), prod5.getPrecio() * 4)); // $3600
        detalles2.add(new DetalleVenta(6, prod1, 5, prod1.getPrecio(), prod1.getPrecio() * 5)); // $2500
        // Total venta2: $6900

        detalles3.add(new DetalleVenta(7, prod2, 3, prod2.getPrecio(), prod2.getPrecio() * 3)); // $2100
        detalles3.add(new DetalleVenta(8, prod3, 1, prod3.getPrecio(), prod3.getPrecio() * 1)); // $1500
        // Total venta3: $3600

        detalles4.add(new DetalleVenta(9, prod4, 3, prod4.getPrecio(), prod4.getPrecio() * 3)); // $1200
        detalles4.add(new DetalleVenta(10, prod5, 2, prod5.getPrecio(), prod5.getPrecio() * 2)); // $1800
        detalles4.add(new DetalleVenta(11, prod6, 1, prod6.getPrecio(), prod6.getPrecio() * 1)); // $300
        // Total venta4: $3300

        // Ventas 
        Venta venta1 = new Venta(1, LocalDate.of(2024, 6, 1), fp1, "0001-00000001", empleado1, empleado2, cliente1);
        venta1.setDetalles(detalles1);
        venta1.setTotal(venta1.calcularPrecioFinal());
        venta1.setSucursal(sucursal1);

        Venta venta2 = new Venta(2, LocalDate.of(2024, 6, 2), fp2, "0001-00000002", empleado1, empleado2, cliente2);
        venta2.setDetalles(detalles2);
        venta2.setTotal(venta2.calcularPrecioFinal());
        venta2.setSucursal(sucursal1);

        Venta venta3 = new Venta(3, LocalDate.of(2024, 6, 3), fp3, "0002-00000003", empleado3, empleado4, cliente3);
        venta3.setDetalles(detalles3);
        venta3.setTotal(venta3.calcularPrecioFinal());
        venta3.setSucursal(sucursal2);

        Venta venta4 = new Venta(4, LocalDate.of(2024, 6, 4), fp3, "0002-00000004", empleado3, empleado4, cliente4);
        venta4.setDetalles(detalles4);
        venta4.setTotal(venta4.calcularPrecioFinal());
        venta4.setSucursal(sucursal1);

        farmaciaData.agregarVenta(venta1);
        farmaciaData.agregarVenta(venta2);
        farmaciaData.agregarVenta(venta3);
        farmaciaData.agregarVenta(venta4);
        System.out.println("Venta 1 ID: " + venta1.getIdVenta());
        System.out.println("Venta 2 ID: " + venta2.getIdVenta());
        // Verifica otros IDs...

        // Serializar con adapter para LocalDate
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new clases.LocalDateAdapter())
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls() // Incluye campos nulos
                .enableComplexMapKeySerialization() // Mejor manejo de estructuras complejas
                .setPrettyPrinting()
                .create();

        String json = gson.toJson(farmaciaData);

        try (FileWriter writer = new FileWriter("farmacia_data.json")) {
            writer.write(json);
            System.out.println("Datos guardados correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.println("---- Iniciando carga de ventas ----");
        CargarVentas.main(args);

        System.out.println("\n---- Ejecutando consultas ----");
        ConsultasVentas.main(args);

        System.out.println("\n---- Proceso finalizado ----");
    }
}