package logico;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SoliEmpresa extends Solicitud
{
	protected String rnc;
	protected float porcentajeMacth;
	protected short angos;


	public SoliEmpresa(String codigo, boolean movilidad, String contrato, boolean licencia, String cuidad, float sueldo,
			String idioma, String rnc, float porcentajeMacth,short angos)
	{
		super(codigo, movilidad, contrato, licencia, cuidad, sueldo, idioma);
		this.rnc = rnc;
		this.porcentajeMacth = porcentajeMacth;
		this.angos = angos;
	}

	public String getRnc()
	{
		return rnc;
	}

	public void setRnc(String rnc)
	{
		this.rnc = rnc;
	}

	public float getPorcentajeMacth()
	{
		return porcentajeMacth;
	}

	public void setPorcentajeMacth(float porcentajeMacth)
	{
		this.porcentajeMacth = porcentajeMacth;
	}

	@Override
	public boolean checkEstado()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public short getAngos()
	{
		return angos;
	}

	public void setAngos(short angos)
	{
		this.angos = angos;
	}
}
