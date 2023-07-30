package logico;


@SuppressWarnings("serial")
public class SoliPersona extends Solicitud
{
	private String cedula;
	private String nivel_educativo;
	private String carrera;
	private int aniosExp;

	public SoliPersona(String codigo, boolean movilidad, String contrato, boolean licencia, String cuidad, float sueldo,
			String idioma, String cedula, String nivel_educativo,String carrera,int aniosExp)
	{
		super(codigo, movilidad, contrato, licencia, cuidad, sueldo, idioma);
		this.cedula = cedula;
		this.nivel_educativo = nivel_educativo;
		this.aniosExp = aniosExp;

	}

	public String getCedula()
	{
		return cedula;
	}

	public void setCedula(String cedula)
	{
		this.cedula = cedula;
	}

	@Override
	public boolean checkEstado()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public String getNivel_educativo() {
		return nivel_educativo;
	}

	public void setNivel_educativo(String nivel_educativo) {
		this.nivel_educativo = nivel_educativo;
	}

	public String getCarrera() {
		return carrera;
	}

	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}

	public int getAniosExp() {
		return aniosExp;
	}

	public void setAniosExp(int aniosExp) {
		this.aniosExp = aniosExp;
	}

}
