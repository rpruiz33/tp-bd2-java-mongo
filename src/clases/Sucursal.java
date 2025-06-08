package clases;

import java.util.List;

public class Sucursal {
    private int idSucursal;
    private String nombre;
    private Domicilio domicilio;
    private List<Empleado> empleados;
    private  Empleado encargado;  
    

    public Sucursal(int idSucursal, String nombre, Domicilio domicilio, List<Empleado> empleados, Empleado encargado) {
		super();
		this.idSucursal = idSucursal;
		this.nombre = nombre;
		this.domicilio = domicilio;
		this.empleados = empleados;
		this.encargado = encargado;
	}
	// Getters y setters
    public int getIdSucursal() { return idSucursal; }
    public void setIdSucursal(int idSucursal) { this.idSucursal = idSucursal; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Domicilio getDomicilio() { return domicilio; }
    public void setDomicilio(Domicilio domicilio) { this.domicilio = domicilio; }

	public Empleado getEncargado() {
		return encargado;
	}

	public void setEncargado(Empleado encargado) {
		this.encargado = encargado;
	}
}