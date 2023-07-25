package logico;

@SuppressWarnings("serial")
public class Tecnico extends Persona
{
	private String area;
	private int agnos;

	public Tecnico(String id, String nombre, String telefono, String direccion, String area, int agnos)
	{
		super(id, nombre, telefono, direccion);
		this.area = area;
		this.agnos = agnos;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
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
