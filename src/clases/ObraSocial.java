package clases;

public class ObraSocial {
    private int idObraSocial;
    private String nombre;
    
    // Constructor, getters y setters
    public ObraSocial(int idObraSocial, String nombre) {
        this.idObraSocial = idObraSocial;
        this.nombre = nombre;
    }
    
    public int getIdObraSocial() { return idObraSocial; }
    public void setIdObraSocial(int idObraSocial) { this.idObraSocial = idObraSocial; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}