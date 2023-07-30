package logico;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class EmpUniversitario extends SoliEmpresa
{
	private String carrera;
	private int agnos;

	public EmpUniversitario(String codigo, boolean movilidad, String contrato, boolean licencia, String cuidad,
			String rnc, float porcentajeMacth, String tipoSalario, float sueldo, String idioma, 
			String carrera, int agnos)
	{
		super(codigo, movilidad, contrato, licencia, cuidad, sueldo, idioma, rnc, porcentajeMacth, tipoSalario, carrera, carrera, agnos, carrera);
		this.carrera = carrera;
		this.agnos = agnos;
	}

	public String getCarrera()
	{
		return carrera;
	}

	public void setCarrera(String carrera)
	{
		this.carrera = carrera;
	}

	public int getAgnos()
	{
		return agnos;
	}

	public void setAgnos(int agnos)
	{
		this.agnos = agnos;
	}
}
