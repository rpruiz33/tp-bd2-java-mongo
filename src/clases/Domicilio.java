package clases;

public class Domicilio {
    private int idDomicilio;
    private String calle;
    private int numero;
    private String ciudad;
    private String provincia;
	private String localidad;
    
    public Domicilio(int idDomicilio, String calle, int numero, String ciudad,String provincia) {
        this.idDomicilio = idDomicilio;
        this.calle = calle;
        this.numero = numero;
        this.ciudad = ciudad;
        this.provincia=provincia;
    }

	public int getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(int idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

}