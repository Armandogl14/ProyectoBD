package logico;

import java.io.Serializable;

public abstract class Persona implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String cedula;
	protected String nombre;
	protected String telefono;
	protected String direccion;
	protected boolean contratado;

	public Persona(String cedula, String nombre, String telefono, String direccion)
	{
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.telefono = telefono;
		this.direccion = direccion;
		contratado = false;
	}

	public String getId()
	{
		return cedula;
	}

	public void setId(String id)
	{
		this.cedula = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public String getTelefono()
	{
		return telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getDireccion()
	{
		return direccion;
	}

	public void setDireccion(String direccion)
	{
		this.direccion = direccion;
	}

	public boolean isContratado()
	{
		return contratado;
	}

	public void setContratado(boolean contratado)
	{
		this.contratado = contratado;
	}
}
