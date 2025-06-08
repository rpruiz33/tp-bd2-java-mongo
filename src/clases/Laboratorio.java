package clases;

public class Laboratorio {
    private int idLaboratorio;
    private String nombre;
    private String codigo;
    
    public Laboratorio(int idLaboratorio, String nombre, String codigo) {
        this.idLaboratorio = idLaboratorio;
        this.nombre = nombre;
        this.codigo = codigo;
    }
    
    // Getters y setters
    public int getIdLaboratorio() { return idLaboratorio; }
    public void setIdLaboratorio(int idLaboratorio) { this.idLaboratorio = idLaboratorio; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}