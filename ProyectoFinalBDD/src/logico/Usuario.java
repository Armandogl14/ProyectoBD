package logico;

import java.io.Serializable;

public class Usuario implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String tipo;

	public Usuario(String username, String password, String tipo)
	{
		super();
		this.username = username;
		this.password = password;
		this.tipo = tipo;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getTipo()
	{
		return tipo;
	}

	public void setTipo(String tipo)
	{
		this.tipo = tipo;
	}

	public boolean isAdmin()
	{
		return tipo.equalsIgnoreCase("Administrador");
	}

	public boolean isSecret()
	{
		return tipo.equalsIgnoreCase("Secretaria");
	}

}
