package clases;

import java.util.ArrayList;
import java.util.List;

public class FarmaciaData {
    private List<ObraSocial> obrasSociales;
    private List<Domicilio> domicilios;
    private List<Sucursal> sucursales;
    private List<Empleado> empleados;
    private List<FormaPago> formasPago;
    private List<Cliente> clientes;
    private List<Laboratorio> laboratorios;
    private List<Producto> productos;
    private List<Venta> ventas;
    
    public FarmaciaData() {
        this.obrasSociales = new ArrayList<>();
        this.domicilios = new ArrayList<>();
        this.sucursales = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.formasPago = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.laboratorios = new ArrayList<>();
        this.productos = new ArrayList<>();
        this.ventas = new ArrayList<>();
    }
    
    // Getters y setters para todas las listas
    public List<ObraSocial> getObrasSociales() { return obrasSociales; }
    public List<Domicilio> getDomicilios() { return domicilios; }
    public List<Sucursal> getSucursales() { return sucursales; }
    public List<Empleado> getEmpleados() { return empleados; }
    public List<FormaPago> getFormasPago() { return formasPago; }
    public List<Cliente> getClientes() { return clientes; }
    public List<Laboratorio> getLaboratorios() { return laboratorios; }
    public List<Producto> getProductos() { return productos; }
    public List<Venta> getVentas() { return ventas; }
    
    // Métodos para agregar elementos
    public void agregarObraSocial(ObraSocial obraSocial) { obrasSociales.add(obraSocial); }
    public void agregarDomicilio(Domicilio domicilio) { domicilios.add(domicilio); }
    public void agregarSucursal(Sucursal sucursal) { sucursales.add(sucursal); }
    public void agregarEmpleado(Empleado empleado) { empleados.add(empleado); }
    public void agregarFormaPago(FormaPago formaPago) { formasPago.add(formaPago); }
    public void agregarCliente(Cliente cliente) { clientes.add(cliente); }
    public void agregarLaboratorio(Laboratorio laboratorio) { laboratorios.add(laboratorio); }
    public void agregarProducto(Producto producto) { productos.add(producto); }
    public void agregarVenta(Venta venta) { ventas.add(venta); }
}
