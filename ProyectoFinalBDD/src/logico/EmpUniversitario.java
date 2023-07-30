package logico;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class EmpUniversitario extends SoliEmpresa
{
	private String carrera;

	public EmpUniversitario(String codigo, boolean movilidad, String contrato, boolean licencia, String cuidad,
			String rnc, float porcentajeMacth, float sueldo, String idioma, 
			String carrera, short angos)
	{
		super(codigo, movilidad, contrato, licencia, cuidad, sueldo, idioma, rnc, porcentajeMacth, angos);
		this.carrera = carrera;
	}

	public String getCarrera()
	{
		return carrera;
	}

	public void setCarrera(String carrera)
	{
		this.carrera = carrera;
	}
}
