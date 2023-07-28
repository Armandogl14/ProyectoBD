package logico;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Bolsa implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Persona> personas;
	private ArrayList<Solicitud> solicitudes;
	private ArrayList<Empresa> empresas;
	private ArrayList<Usuario> usuarios;
	private static Bolsa bolsa = null;
	private static Usuario loginUser;
	public int genSol;
	private static final String DB_URL = "jdbc:sqlserver://192.168.100.118:1433;databaseName=Bolsa_Empleo_Grupo1;encrypt=false";
	private static final String USERNAME = "mrodriguez";
	private static final String PASSWORD = "dzjl61945R";
	public static  Connection connection = null;

	private Bolsa()
	{
		super();
		personas = new ArrayList<>();
		solicitudes = new ArrayList<>();
		empresas = new ArrayList<>();
		usuarios = new ArrayList<>();
		setConnection(abrirConexion());
		genSol = 1;
	}

	public static Bolsa getInstance()
	{
		if (bolsa == null)
			bolsa = new Bolsa();

		return bolsa;
	}

	public static String getDbUrl() {
		return DB_URL;
	}

	public static String getUsername() {
		return USERNAME;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public ArrayList<Persona> getPersonas()
	{
		return personas;
	}

	public void setPersonas(ArrayList<Persona> personas)
	{
		this.personas = personas;
	}

	public ArrayList<Solicitud> getSolicitudes()
	{
		return solicitudes;
	}

	public void setSolicitudes(ArrayList<Solicitud> solicitudes)
	{
		this.solicitudes = solicitudes;
	}

	public ArrayList<Empresa> getEmpresas()
	{
		return empresas;
	}

	public void setEmpresas(ArrayList<Empresa> empresas)
	{
		this.empresas = empresas;
	}

	public ArrayList<Usuario> getUsuarios()
	{
		return usuarios;
	}

	public void setUsuarios(ArrayList<Usuario> usuarios)
	{
		this.usuarios = usuarios;
	}

	public static Usuario getLoginUser()
	{
		return loginUser;
	}
	

	public static void setLoginUser(Usuario loginUser)
	{
		Bolsa.loginUser = loginUser;
	}

	public int getGenSol()
	{
		return genSol;
	}

	public void setGenSol(int genSol)
	{
		this.genSol = genSol;
	}

	public boolean addPersona(Persona person)
	{
		return personas.add(person);
	}

	public boolean addSolicitud(Solicitud soli)
	{
		genSol++;
		return solicitudes.add(soli);
	}

	public boolean addEmpresa(Empresa empresa)
	{
		return empresas.add(empresa);
	}

	public boolean addUsuario(Usuario usuario)
	{
		return usuarios.add(usuario);
	}
	public static Connection getConnection() {
		return connection;
	}

	public static void setConnection(Connection connection) {
		Bolsa.connection = connection;
	}

	// Método para abrir la conexión
	public static Connection abrirConexion() {
		 Connection connection = null;
	    try {
	        // Cargar el controlador JDBC
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	        
	        // Establecer la conexión
	        connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	    } catch (ClassNotFoundException | SQLException e) {
	        System.err.println("Error al conectar con SQL Server: " + e.getMessage());
	    }
	    
	    return connection;
	}

	// Método para cerrar la conexión
	/*public static void cerrarConexion(Connection connection) {
	    try {
	        if (connection != null && !connection.isClosed()) {
	            connection.close();
	            System.out.println("Conexión cerrada con SQL Server.");
	        }
	    } catch (SQLException e) {
	        System.err.println("Error al cerrar la conexión con SQL Server: " + e.getMessage());
	    }
	}*/
	public Empresa buscarEmpresaByRNC(String rnc)
	{
		boolean encontrado = false;
		int i = 0;
		Empresa aux = null;

		while (!encontrado && i < empresas.size())
		{
			if (empresas.get(i).getRnc().equalsIgnoreCase(rnc))
			{
				encontrado = true;
				aux = empresas.get(i);
			}
			i++;
		}
		return aux;
	}
//=======================================================================================================
	public Persona buscarPersonaByCedula(String cedula)
	{
		boolean encontrado = false;
		int i = 0;
		Persona aux = null;

		while (!encontrado && i < personas.size())
		{
			if (personas.get(i).getId().equalsIgnoreCase(cedula))
			{
				encontrado = true;
				aux = personas.get(i);
			}
			i++;
		}
		return aux;
	}

	public Solicitud buscarSolicitudByCodigo(String codigo)
	{
		boolean encontrado = false;
		int i = 0;
		Solicitud aux = null;

		while (!encontrado && i < solicitudes.size())
		{
			if (solicitudes.get(i).getCodigo().equalsIgnoreCase(codigo))
			{
				encontrado = true;
				aux = solicitudes.get(i);
			}
			i++;
		}
		return aux;
	}

	public Usuario buscarUsuarioByUsername(String username)
	{
		boolean encontrado = false;
		int i = 0;
		Usuario aux = null;

		while (!encontrado && i < usuarios.size())
		{
			if (usuarios.get(i).getUsername().equalsIgnoreCase(username))
			{
				encontrado = true;
				aux = usuarios.get(i);
			}
			i++;
		}
		return aux;
	}

	public boolean eliminarSolicitud(Solicitud solicitud)
	{
		return solicitudes.remove(solicitud);
	}

	public boolean eliminarPersona(Persona person)
	{
		return personas.remove(person);
	}

	public boolean eliminarEmpresa(Empresa empresa)
	{
		return empresas.remove(empresa);
	}

	public boolean elimianrUsuarios(Usuario usuario)
	{
		return usuarios.remove(usuario);
	}

	public boolean isLibreSoliEmp(String RNC)
	{
		boolean libre = true;
		for (Solicitud solicitud : solicitudes)
			if (solicitud instanceof SoliEmpresa && ((SoliEmpresa) solicitud).getRnc().equalsIgnoreCase(RNC))
				libre = false;

		return libre;
	}

	public void cambiarRNC(String antiguo, String nuevo)
	{
		for (Solicitud solicitud : solicitudes)
			if (solicitud instanceof SoliEmpresa && ((SoliEmpresa) solicitud).getRnc().equalsIgnoreCase(antiguo))
				((SoliEmpresa) solicitud).setRnc(nuevo);
	}

	public boolean isLibreSoliPer(String cedula)
	{
		boolean libre = true;
		for (Solicitud solicitud : solicitudes)
			if (solicitud instanceof SoliPersona && ((SoliPersona) solicitud).getCedula().equalsIgnoreCase(cedula))
				libre = false;

		return libre;
	}

	public void cambiarCedula(String antiguo, String nuevo)
	{
		for (Solicitud solicitud : solicitudes)
			if (solicitud instanceof SoliPersona && ((SoliPersona) solicitud).getCedula().equalsIgnoreCase(antiguo))
				((SoliPersona) solicitud).setCedula(nuevo);
	}



	public boolean confirmarLogin(String username, String password)
	{
		boolean login = false;
		for (Usuario user : usuarios)
			if (user.getUsername().equals(username) && user.getPassword().equals(password))
			{
				setLoginUser(user);
				login = true;
			}
		return login;
	}

	public float match(SoliEmpresa solicitudEmpresa, SoliPersona solicitudPersona)
	{
		float puntaje = compararArea(solicitudEmpresa, solicitudPersona);
		Persona persona = buscarPersonaByCedula(solicitudPersona.getCedula());

		if (puntaje > 0 && persona != null)
		{
			puntaje += compararContratos(solicitudEmpresa.getContrato(), solicitudPersona.getContrato());
			puntaje += comprarSueldo(solicitudEmpresa.getSueldo(), solicitudPersona.getSueldo());
			puntaje += compararIdioma(solicitudEmpresa.getIdiomas(), solicitudPersona.getIdiomas());
			puntaje += compararMovilidad(solicitudEmpresa.isMovilidad(), solicitudPersona.isMovilidad());
			puntaje += compararLicencia(solicitudEmpresa.isLicencia(), solicitudPersona.isLicencia());
			puntaje += compararCiudad(solicitudEmpresa.getCuidad(), solicitudPersona.getCuidad());
			if (solicitudEmpresa instanceof EmpUniversitario && persona instanceof Universitario)
				puntaje += compararTiempo(((EmpUniversitario) solicitudEmpresa).getAgnos(),
						((Universitario) persona).getAgnos());
			else if (solicitudEmpresa instanceof EmpTecnico && persona instanceof Tecnico)
				puntaje += compararTiempo(((EmpTecnico) solicitudEmpresa).getAgnos(), ((Tecnico) persona).getAgnos());
		}
		return puntaje;
	}

	public float compararContratos(String contratoOfrecido, String contratoDeseado)
	{
		float total = 15;
		boolean coinciden = false;

		if (contratoOfrecido.equalsIgnoreCase(contratoDeseado))
			coinciden = true;

		else if (contratoOfrecido == "Media Jornada" && contratoDeseado == "Jornada Mixta")
			coinciden = true;

		else if (contratoOfrecido == "Jornada Completa" && contratoDeseado == "Jornada Mixta")
		{
			coinciden = true;
			total = (float) 7.5;
		}

		if (!coinciden)
			total = 0;

		return total;
	}

	public float comprarSueldo(float sueldoQuePagan, float sueldoQueQuieren)
	{
		float porcentaje = 0;
		float total = 15;
		float diferencia = 0;

		if (sueldoQuePagan <= sueldoQueQuieren)
			porcentaje = 100;

		else
		{
			diferencia = ((sueldoQueQuieren - sueldoQuePagan) / sueldoQuePagan) * 100;
			porcentaje = diferencia == 100 ? 0
					: diferencia >= 90 ? 10
							: diferencia >= 80 ? 20
									: diferencia >= 70 ? 30
											: diferencia >= 60 ? 40
													: diferencia >= 50 ? 50
															: diferencia >= 40 ? 60
																	: diferencia >= 30 ? 70
																			: diferencia >= 20 ? 80 : diferencia >= 10 ? 90 : 100;

		}

		return total * (porcentaje / 100);
	}

	public float compararIdioma(String idiomaRequerido, String idiomaHablado)
	{
		float porcentaje = 0;
		float total = 10;
		
		if(idiomaHablado.equalsIgnoreCase(idiomaRequerido))
			return total;
		
		else
			return porcentaje;
	}

	public float compararMovilidad(boolean empresa, boolean persona)
	{
		float total = 10;

		if (empresa && !persona)
			total = 0;

		return total;
	}

	public float compararLicencia(boolean empresa, boolean persona)
	{
		float total = 4;

		if (empresa && !persona)
			total = 0;

		return total;
	}

	public float compararCiudad(String empresa, String persona)
	{
		float total = 4;

		if (!empresa.equalsIgnoreCase(persona))
			total = 0;

		return total;
	}

	public float compararArea(SoliEmpresa solicitudEmpresa, SoliPersona solicitudPersona)
	{
		float total = -1;//24
		Persona persona = buscarPersonaByCedula(solicitudPersona.getCedula());

		if (persona != null)
		{
			if (solicitudEmpresa instanceof EmpUniversitario && persona instanceof Universitario)
			{
				if (((EmpUniversitario) solicitudEmpresa).getCarrera()
						.equalsIgnoreCase(((Universitario) persona).getCarrera()))
					total = 24;
			}
			else if (solicitudEmpresa instanceof EmpTecnico && persona instanceof Tecnico)
			{
				if (((EmpTecnico) solicitudEmpresa).getArea().equalsIgnoreCase(((Tecnico) persona).getArea()))
					total = 24;
			}
		}
		return total;
	}

	public float compararTiempo(int tiempoRequerido, int tiempoQueTiene)
	{
		float porcentaje = 0;
		float total = 18;
		float diferencia = 0;

		if (tiempoRequerido <= tiempoQueTiene)
			porcentaje = 100;

		else
		{
			diferencia = ((tiempoRequerido - tiempoQueTiene) / tiempoRequerido) * 100;
			porcentaje = diferencia == 100 ? 0
					: diferencia >= 90 ? 10
							: diferencia >= 80 ? 20
									: diferencia >= 70 ? 30
											: diferencia >= 60 ? 40
													: diferencia >= 50 ? 50
															: diferencia >= 40 ? 60
																	: diferencia >= 30 ? 70
																			: diferencia >= 20 ? 80 : diferencia >= 10 ? 90 : 100;

		}

		return total * (porcentaje / 100);
	}


	public void desactivarSoliPersona(String cedula)
	{
		for (Solicitud soli : solicitudes)
			if (soli instanceof SoliPersona)
				if (((SoliPersona) soli).getCedula().equalsIgnoreCase(cedula))
					soli.setActiva(false);

	}

	public void reactivarSoliPersona(String cedula)
	{
		for (Solicitud soli : solicitudes)
			if (soli instanceof SoliPersona)
				if (((SoliPersona) soli).getCedula().equalsIgnoreCase(cedula))
					soli.setActiva(true);

	}

	public void desemplearPersona(String cedula)
	{
		for (Persona person : personas)
			if (person.getId().equalsIgnoreCase(cedula))
				person.setContratado(false);

	}

	public void contrarPersona(String cedula)
	{
		for (Persona person : personas)
			if (person.getId().equalsIgnoreCase(cedula))
				person.setContratado(true);
	}

	public void actualizarEstadoSoliEmpresa(SoliEmpresa soli)
	{
		int cantidad = soli.getCantidad();

		soli.setCantidad(cantidad - 1);

		if (soli.getCantidad() == 0)
			soli.setActiva(false);
	}

	public boolean existeUsuario(String username)
	{
		boolean existe = false;

		for (Usuario usuario : usuarios)
			if (username.equalsIgnoreCase(usuario.getUsername()))
				existe = true;

		return existe;
	}


}
