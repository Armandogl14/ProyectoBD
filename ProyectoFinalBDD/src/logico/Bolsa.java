package logico;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class Bolsa implements Serializable
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Persona> personas;
	private ArrayList<Solicitud> solicitudes;
	private ArrayList<Empresa> empresas;
	private ArrayList<Usuario> usuarios;
	private static Bolsa bolsa = null;
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

	// M茅todo para abrir la conexi贸n
	public static Connection abrirConexion() {
		Connection connection = null;
		try {
			// Cargar el controlador JDBC
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			// Establecer la conexi贸n
			connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("Error al conectar con SQL Server: " + e.getMessage());
		}

		return connection;
	}

	// M茅todo para cerrar la conexi贸n
	public void cerrarConexion(Connection connection) {
		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				System.out.println("Conexi贸n cerrada con SQL Server.");
			}
		} catch (SQLException e) {
			System.err.println("Error al cerrar la conexi贸n con SQL Server: " + e.getMessage());
		}
	}

	public Empresa buscarEmpresaByRNC(String rnc)
	{
		/*boolean encontrado = false;
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
		return aux;*/
		Empresa aux = null;

		String selectQuery = "select RNC,Nombre,Telefono,Direccion from Empresa";
		try (Connection connection1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement statement = connection1.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			// Recorrer el resultado del conjunto de resultados (ResultSet)
			while (resultSet.next()) {
				String comparador = resultSet.getString("RNC");
				if (comparador.equalsIgnoreCase(rnc)) {
					aux = new Empresa(null, null, null, null);
					aux.setNombre(resultSet.getString("Nombre"));
					aux.setRnc(resultSet.getString("RNC"));
					aux.setDireccion(resultSet.getString("Direccion"));
					aux.setTelefono(resultSet.getString("Telefono"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aux;
	}
	//=======================================================================================================
	public Persona buscarPersonaByCedula(String cedula) {
		Persona aux = null;

		String selectQuery = "select Cedula,Nombre,Telefono,Direccion,Contratado, Nivel_Educativo from Persona";
		try (Connection connection1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement statement = connection1.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			// Recorrer el resultado del conjunto de resultados (ResultSet)
			while (resultSet.next()) {
				String comparador = resultSet.getString("Cedula");
				if (comparador.equalsIgnoreCase(cedula)) {

					aux = new Persona(null, null, null, null);
					aux.setNombre(resultSet.getString("Nombre"));
					aux.setId(resultSet.getString("Cedula"));
					aux.setDireccion(resultSet.getString("Direccion"));
					aux.setTelefono(resultSet.getString("Telefono"));
					String contratado = resultSet.getString("Contratado");
					if(contratado.equalsIgnoreCase("Si")) {
						aux.setContratado(true);
					}else {
						aux.setContratado(false);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aux;
	}
	public SoliEmpresa buscarOfertaByCodigo(int codigo) {
		SoliEmpresa aux = null;
		String selectQuery = "select Codigo,Mobilidad,Contrato,Licencia,Nivel_Educativo_Deseado,Sueldo,Activa,RNC,id_carrera,id_area,id_idioma,id_ciudad, Agnos_Experiencia, Porcentaje_Match from Oferta_Empresa";
		try (Connection connection1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement statement = connection1.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			// Recorrer el resultado del conjunto de resultados (ResultSet)
			while (resultSet.next()) {
				int comparador = resultSet.getInt("Codigo");
				if (comparador == codigo) {

					if(resultSet.getString("Nivel_Educativo_Deseado").equalsIgnoreCase("Universitario"))
					{
						aux = new EmpUniversitario(null, false, null, false, null, null, 0, 0, null, null, (short) 0);
						((EmpUniversitario) aux).setCarrera(resultSet.getString("id_carrera"));
					}

					else if(resultSet.getString("Nivel_Educativo_Deseado").equalsIgnoreCase("Tecnico"))
					{
						aux = new EmpTecnico(null, false, null, false, null, null, 0, 0, null, null, (short) 0);
						((EmpTecnico) aux).setArea(resultSet.getString("id_area"));
					}

					aux.setCodigo(String.valueOf(resultSet.getInt("Codigo")));
					String movilidad = resultSet.getString("Mobilidad");
					if (movilidad.equalsIgnoreCase("Si")) {
						aux.setMovilidad(true);
					} else {
						aux.setMovilidad(false);
					}
					aux.setContrato(resultSet.getString("Contrato"));
					String licencia = resultSet.getString("Licencia");
					if (licencia.equalsIgnoreCase("Si")) {
						aux.setLicencia(true);
					} else {
						aux.setLicencia(false);
					}
					aux.setCuidad(resultSet.getString("id_ciudad"));
					aux.setSueldo(resultSet.getFloat("Sueldo"));
					aux.setIdiomas(resultSet.getString("id_idioma"));
					aux.setRnc(resultSet.getString("RNC"));
					aux.setPorcentajeMacth(resultSet.getFloat("Porcentaje_Match"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aux;
	}

	public SoliPersona buscarSolicitudByCodigo(int codigo) {
		SoliPersona aux = new SoliPersona(null, false, null, false, null, (float) 0.00, null, null, null, null, null , (short) 0);
		String selectQuery = "select Codigo,Mobilidad,Contrato,Licencia,Nivel_Educativo,Sueldo,Activa,Cedula,id_carrera,id_area,id_idioma,id_ciudad,Agnos_Experiencia from Solicitud_Persona";
		try (Connection connection1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
				Statement statement = connection1.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			// Recorrer el resultado del conjunto de resultados (ResultSet)
			while (resultSet.next()) {
				int comparador = resultSet.getInt("Codigo");
				if (comparador == codigo) {
					// C骴igo de solicitud encontrado en la tabla
					aux.setCodigo(String.valueOf(resultSet.getInt("Codigo")));
					String movilidad = resultSet.getString("Mobilidad");
					if (movilidad.equalsIgnoreCase("Si")) {
						aux.setMovilidad(true);
					} else {
						aux.setMovilidad(false);
					}
					aux.setContrato(resultSet.getString("Contrato"));
					String licencia = resultSet.getString("Licencia");
					if (licencia.equalsIgnoreCase("Si")) {
						aux.setLicencia(true);
					} else {
						aux.setLicencia(false);
					}
					aux.setLicencia(licencia.equalsIgnoreCase("Si"));
					aux.setCuidad(resultSet.getString("id_ciudad"));
					aux.setSueldo(resultSet.getFloat("Sueldo"));
					aux.setIdiomas(resultSet.getString("id_idioma"));
					aux.setCedula(resultSet.getString("Cedula"));
					aux.setAniosExp(resultSet.getShort("Agnos_Experiencia"));
					aux.setNivel_educativo(resultSet.getString("Nivel_Educativo"));
					aux.setCarrera(resultSet.getString("id_carrera"));
					aux.setArea(resultSet.getString("id_area"));

					// No necesitas continuar el bucle si ya encontraste la solicitud
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aux;
	}
	/*public Solicitud buscarOfertaByCodigo(int codigo)
	{
		boolean encontrado = false;
		int i = 0;

		SoliEmpresa aux = new SoliEmpresa(null, false, null, false, null, (float) 0.00, null, null, 0.0f, null);
	    String selectQuery = "select Codigo,Mobilidad,Contrato,Licencia,Nivel_Educativo_Deseado,Sueldo,Activa,RNC,id_carrera,id_area,id_idioma,id_ciudad,Porcentaje_Match from Oferta_Empresa";
	    try (Connection connection1 = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	         Statement statement = connection1.createStatement();
	         ResultSet resultSet = statement.executeQuery(selectQuery)) {

	        // Recorrer el resultado del conjunto de resultados (ResultSet)
	        while (resultSet.next()) {
	            int comparador = resultSet.getInt("Codigo");
	            if (comparador == codigo) {

	                aux.setCodigo(String.valueOf(resultSet.getInt("Codigo")));
	                String movilidad = resultSet.getString("Mobilidad");
	                if(movilidad.equalsIgnoreCase("Si")) {
	                	aux.setMovilidad(true);
	                }else {
	                	aux.setMovilidad(false);
	                }
	                aux.setContrato(resultSet.getString("Contrato"));
	                String licencia = resultSet.getString("Licencia");
	                if(licencia.equalsIgnoreCase("Si")) {
	                	aux.setLicencia(true);
	                }else {
	                	aux.setLicencia(false);
	                }
	                aux.setCuidad(resultSet.getString("id_ciudad"));
	                aux.setSueldo(resultSet.getFloat("Sueldo"));
	                aux.setIdiomas(resultSet.getString("id_idioma"));
	                aux.setRnc(resultSet.getString("RNC"));
	                aux.setPorcentajeMacth(resultSet.getFloat("Oferta_Empresa"));
	                //aux.setTipoSalario(tipoSalario);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return aux;
	}*/

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
		Connection conexion = abrirConexion();
		String select = "select username, contrasenia from Usuario";

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			while(result.next()) {
				if(result.getString("username").equalsIgnoreCase(username) && result.getString("contrasenia").equalsIgnoreCase(password))
					login = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return login;
	}

	public float match(SoliEmpresa solicitudEmpresa, SoliPersona solicitudPersona)
	{
		float puntaje = compararArea(solicitudEmpresa, solicitudPersona);
		Persona persona = buscarPersonaByCedula(solicitudPersona.getCedula());

		if (puntaje > 0)
		{
			puntaje += compararContratos(solicitudEmpresa.getContrato(), solicitudPersona.getContrato());
			puntaje += comprarSueldo(solicitudEmpresa.getSueldo(), solicitudPersona.getSueldo());
			puntaje += compararIdioma(solicitudEmpresa.getIdiomas(), solicitudPersona.getIdiomas());
			puntaje += compararMovilidad(solicitudEmpresa.isMovilidad(), solicitudPersona.isMovilidad());
			puntaje += compararLicencia(solicitudEmpresa.isLicencia(), solicitudPersona.isLicencia());
			puntaje += compararCiudad(solicitudEmpresa.getCuidad(), solicitudPersona.getCuidad());
			if (solicitudEmpresa instanceof EmpUniversitario && solicitudPersona.getNivel_educativo().equalsIgnoreCase("Universitario"))
				puntaje += compararTiempo(((EmpUniversitario) solicitudEmpresa).getAngos(),
						solicitudPersona.getAniosExp());
			else if (solicitudEmpresa instanceof EmpTecnico && solicitudPersona.getNivel_educativo().equalsIgnoreCase("Tecnico"))
				puntaje += compararTiempo(((EmpTecnico) solicitudEmpresa).getAngos(), solicitudPersona.getAniosExp());
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

		if(solicitudEmpresa != null && solicitudPersona != null)
		{
			if (solicitudEmpresa instanceof EmpUniversitario && solicitudPersona.getNivel_educativo().equalsIgnoreCase("Universitario"))
			{
				if (((EmpUniversitario) solicitudEmpresa).getCarrera().equalsIgnoreCase(solicitudPersona.getCarrera()))
					total = 24;
			}
			else if (solicitudEmpresa instanceof EmpTecnico && solicitudPersona.getNivel_educativo().equalsIgnoreCase("Tecnico"))
			{
				if (((EmpTecnico) solicitudEmpresa).getArea().equalsIgnoreCase(solicitudPersona.getArea()))
					total = 24;
			}
		}
		return total;
	}

	public float compararTiempo(short tiempoRequerido, short tiempoQueTiene)
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


	public boolean existeUsuario(String username)
	{
		boolean existe = false;
		Connection conexion = abrirConexion();
		String select = "select username from Usuario";

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			while(result.next()) {
				if(result.getString("username").equalsIgnoreCase(username))
					existe = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return existe;
	}

	public boolean existePersona(String Cedula)
	{
		boolean existe = false;
		Connection conexion = abrirConexion();
		String select = "select Cedula from Persona";

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			while(result.next()) {
				if(result.getString("Cedula").equalsIgnoreCase(Cedula))
					existe = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return existe;
	}

	public boolean existeEmpresa(String RNC)
	{
		boolean existe = false;
		Connection conexion = abrirConexion();
		String select = "select RNC from Empresa";

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			while(result.next()) {
				if(result.getString("RNC").equalsIgnoreCase(RNC))
					existe = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return existe;
	}

	public boolean existeSoliPersona(String code)
	{
		boolean existe = false;
		Connection conexion = abrirConexion();
		String select = "select Codigo from Solicitud_Persona";

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			while(result.next()) {
				if(result.getString("Codigo").equalsIgnoreCase(code))
					existe = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return existe;
	}

	public boolean existeOfertaEmpresa(String code)
	{
		boolean existe = false;
		Connection conexion = abrirConexion();
		String select = "select Codigo from Oferta_Empresa";

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			while(result.next()) {
				if(result.getString("Codigo").equalsIgnoreCase(code))
					existe = true;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return existe;
	}

	public boolean isContratado(String Cedula)
	{
		boolean contratado = false;
		Connection conexion = abrirConexion();
		String select = "select Cedula, Contratado from Persona where Cedula = "+Cedula;

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			if(result.getString("Contratado").equalsIgnoreCase("Si"))
				contratado = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return contratado;
	}

	public boolean isSolicitudActiva(int Code)
	{
		System.out.println(Code);
		/*boolean Activa = false;
		Connection conexion = abrirConexion();
		String select = "select Codigo, Activa from Solicitud_Persona where Codigo = "+Code;

		try
		{
			Statement stmnt = conexion.createStatement();
			ResultSet result = stmnt.executeQuery(select);

			if(result.getString("Activa").equalsIgnoreCase("Si"))
				Activa = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return Activa;*/
		boolean Activa = false;
		Connection conexion = abrirConexion();
		String select = "select Codigo, Activa from Solicitud_Persona where Codigo = (?)";

		try
		{
			PreparedStatement stmnt = conexion.prepareStatement(select);
			stmnt.setInt(1, Code);
			ResultSet result = stmnt.executeQuery(select);

			if(result.getString("Activa").equalsIgnoreCase("Si"))
				Activa = true;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return Activa;
	}
}
