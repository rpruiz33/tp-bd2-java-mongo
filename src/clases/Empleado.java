package clases;

public class Empleado {
    private int idEmpleado;
    private String apellido;
    private String nombre;
    private String dni;
    private String cuil;
    private String numeroAfiliado;
    private ObraSocial obraSocial;
    private Domicilio domicilio;
    private transient Sucursal sucursal; // para que no se produzcan referencias circulares
    
    public Empleado(int idEmpleado, String apellido, String nombre, String dni, String cuil, 
                   String numeroAfiliado, ObraSocial obraSocial, Domicilio domicilio, Sucursal sucursal) {
        this.idEmpleado = idEmpleado;
        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.cuil = cuil;
        this.numeroAfiliado = numeroAfiliado;
        this.obraSocial = obraSocial;
        this.domicilio = domicilio;
        this.sucursal = sucursal;
    }
    
    // Getters y setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getCui() { return cuil; }
    public void setCui(String cui) { this.cuil = cui; }
    public String getNumeroAfiliado() { return numeroAfiliado; }
    public void setNumeroAfiliado(String numeroAfiliado) {
    	this.numeroAfiliado = numeroAfiliado; }
    public ObraSocial getObraSocial() { return obraSocial; }
    public void setObraSocial(ObraSocial obraSocial) { this.obraSocial = obraSocial; }
    public Domicilio getDomicilio() { return domicilio; }
    public void setDomicilio(Domicilio domicilio) { this.domicilio = domicilio; }
    public Sucursal getSucursal() { return sucursal; }
    public void setSucursal(Sucursal sucursal) { this.sucursal = sucursal; }
 
}