package logico;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class SoliEmpresa extends Solicitud
{
	protected String rnc;
	protected float porcentajeMacth;
	protected String tipoSalario;
	protected String NivelEducativo;
	protected String Area;
	protected int anios;
	protected String Carrera;


	public SoliEmpresa(String codigo, boolean movilidad, String contrato, boolean licencia, String cuidad, float sueldo,
			String idioma, String rnc, float porcentajeMacth, String tipoSalario,String NivelEducativo,String Area,int anios,String Carrera)
	{
		super(codigo, movilidad, contrato, licencia, cuidad, sueldo, idioma);
		this.rnc = rnc;
		this.porcentajeMacth = porcentajeMacth;
		this.tipoSalario = tipoSalario;
		this.NivelEducativo = NivelEducativo;
		this.Area = Area;
		this.anios = anios;
		this.Carrera = Carrera;
		
	}

	public String getRnc()
	{
		return rnc;
	}

	public void setRnc(String rnc)
	{
		this.rnc = rnc;
	}

	public String getNivelEducativo() {
		return NivelEducativo;
	}

	public void setNivelEducativo(String nivelEducativo) {
		NivelEducativo = nivelEducativo;
	}

	public float getPorcentajeMacth()
	{
		return porcentajeMacth;
	}

	public void setPorcentajeMacth(float porcentajeMacth)
	{
		this.porcentajeMacth = porcentajeMacth;
	}

	public String getTipoSalario()
	{
		return tipoSalario;
	}

	public void setTipoSalario(String tipoSalario)
	{
		this.tipoSalario = tipoSalario;
	}

	
	@Override
	public boolean checkEstado()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

}
