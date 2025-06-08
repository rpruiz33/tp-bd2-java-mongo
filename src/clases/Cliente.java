package clases;

public class Cliente {
    private int idCliente;
    private String apellido;
    private String nombre;
    private String dni;
    private ObraSocial obraSocial;
    private Domicilio domicilio;
    
    public Cliente(int idCliente, String apellido, String nombre, String dni, 
                  ObraSocial obraSocial, Domicilio domicilio) {
        this.idCliente = idCliente;
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.obraSocial = obraSocial;
        this.domicilio = domicilio;
    }
    public Cliente(int idCliente, String nombre, String apellido, Domicilio domicilio, ObraSocial obraSocial) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.apellido = apellido;
        this.domicilio = domicilio;
        this.obraSocial = obraSocial;
        this.dni = ""; // o null si querés, porque no pasás dni aquí
    }


	// Getters y setters
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public ObraSocial getObraSocial() { return obraSocial; }
    public void setObraSocial(ObraSocial obraSocial) { this.obraSocial = obraSocial; }
    public Domicilio getDomicilio() { return domicilio; }
    public void setDomicilio(Domicilio domicilio) { this.domicilio = domicilio; }
}
