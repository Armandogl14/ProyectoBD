package logico;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Solicitud implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String codigo;
	protected boolean movilidad;
	protected String contrato;
	protected boolean licencia;
	protected String cuidad;
	protected float sueldo;
	protected boolean activa;
	private String idioma;

	public Solicitud(String codigo, boolean movilidad, String contrato, boolean licencia, String cuidad, float sueldo,
			String idioma)
	{
		super();
		this.codigo = codigo;
		this.movilidad = movilidad;
		this.contrato = contrato;
		this.licencia = licencia;
		this.cuidad = cuidad;
		this.sueldo = sueldo;
		activa = true;
		this.idioma = idioma;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public boolean isMovilidad()
	{
		return movilidad;
	}

	public void setMovilidad(boolean movilidad)
	{
		this.movilidad = movilidad;
	}

	public String getContrato()
	{
		return contrato;
	}

	public void setContrato(String contrato)
	{
		this.contrato = contrato;
	}

	public boolean isLicencia()
	{
		return licencia;
	}

	public void setLicencia(boolean licencia)
	{
		this.licencia = licencia;
	}

	public String getCuidad()
	{
		return cuidad;
	}

	public void setCuidad(String cuidad)
	{
		this.cuidad = cuidad;
	}

	public Float getSueldo()
	{
		return sueldo;
	}

	public void setSueldo(float sueldo)
	{
		this.sueldo = sueldo;
	}

	public boolean isActiva()
	{
		return activa;
	}

	public void setActiva(boolean activa)
	{
		this.activa = activa;
	}

	public String getIdiomas()
	{
		return idioma;
	}

	public void setIdiomas(String idioma)
	{
		this.idioma = idioma;
	}

	public abstract boolean checkEstado();

}
