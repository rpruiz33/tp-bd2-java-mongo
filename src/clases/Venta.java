package clases;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Venta {
    private int idVenta;
    private LocalDate fecha;
    private FormaPago formaPago;
    private String numeroTicket;
    private double total;
	private Empleado empleadoAtencion;
	private Empleado empleadoCaja;
    private Cliente cliente;
    private List<DetalleVenta> detalles;
    private Sucursal sucursal;
    

    public Venta(int idVenta, LocalDate fecha, FormaPago formaPago, String numeroTicket, Empleado empleadoCaja, Empleado empleadoAtencion,Cliente cliente) {
	   this.idVenta = idVenta;
	   this.fecha = fecha;
	   this.formaPago = formaPago;
	   this.numeroTicket = numeroTicket;
	   this.empleadoCaja = empleadoCaja;
	   this.empleadoAtencion = empleadoAtencion;
	   this.cliente = cliente;
	   this.detalles = new ArrayList<>();
}

    
  

    // Getters y setters
    public int getIdVenta() { return idVenta; }
    public void setIdVenta(int idVenta) { this.idVenta = idVenta; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }

    public String getNumeroTicket() { return numeroTicket; }
    public void setNumeroTicket(String numeroTicket) { this.numeroTicket = numeroTicket; }

    public double getTotal() { return total; }
   
    
    public void setTotal(double total) { this.total = total; }

    public Empleado getEmpleadoCaja() { return empleadoCaja; }
    public void setEmpleadoCaja(Empleado empleadoCaja) { this.empleadoCaja = empleadoCaja; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }

    public void agregarDetalle(DetalleVenta detalle) { this.detalles.add(detalle); }
    
    public double calcularPrecioFinal() {
        return detalles.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
    }
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
}