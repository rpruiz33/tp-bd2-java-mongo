package clases;

public class FormaPago {
    private int idFormaPago;
    private String nombre;
    
    public FormaPago(int idFormaPago, String nombre) {
        this.idFormaPago = idFormaPago;
        this.nombre = nombre;
    }
    
    // Getters y setters
    public int getIdFormaPago() { return idFormaPago; }
    public void setIdFormaPago(int idFormaPago) { this.idFormaPago = idFormaPago; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}