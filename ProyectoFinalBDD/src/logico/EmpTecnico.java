package logico;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class EmpTecnico extends SoliEmpresa
{
	private String area;


	public EmpTecnico(String codigo, boolean movilidad, String contrato, boolean licencia, String cuidad, String rnc,
			float porcentajeMacth, float sueldo, String idioma, String area,
			short angos)
	{
		super(codigo, movilidad, contrato, licencia, cuidad, sueldo, idioma, rnc, porcentajeMacth, angos);
		this.area = area;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}
}
