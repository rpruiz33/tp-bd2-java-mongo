package clases;

public class Producto {
    private int idProducto;
    private String descripcion;
    private double precio;
    private String codigo;
    private Laboratorio laboratorio;
    private String tipo; // Enum: Medicamento, Perfumeria, etc.
    
    public Producto(int idProducto, String descripcion, double precio, String codigo, 
                   Laboratorio laboratorio, String tipo) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.codigo = codigo;
        this.laboratorio = laboratorio;
        this.tipo = tipo;
    }
    
    // Getters y setters
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public Laboratorio getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Laboratorio laboratorio) { this.laboratorio = laboratorio; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}