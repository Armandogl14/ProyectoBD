package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Bolsa;
import logico.EmpTecnico;
import logico.EmpUniversitario;
import logico.Empresa;
import logico.Persona;
import logico.SoliEmpresa;
import logico.SoliPersona;
import logico.Solicitud;
import logico.Tecnico;
import logico.Universitario;

@SuppressWarnings("serial")
public class ListSoliEspecificas extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	Persona persona = null;
	Empresa empresa = null;
	boolean soliPersona = false;
	boolean soliEmpresa = false;
	private static DefaultTableModel model;
	private static Object[] rows;
	private Solicitud selected = null;
	private JTable table;
	private JButton btnMostrar;
	private JButton btnEliminar;
	private JButton btnSalir;
	private boolean match;

	public ListSoliEspecificas(Persona paux, Empresa eaux)
	{
		if (paux != null)
		{
			persona = paux;
			soliPersona = true;
		}

		else if (eaux != null)
		{
			empresa = eaux;
			soliEmpresa = true;
		}

		setBounds(100, 100, 683, 505);
		setLocationRelativeTo(null);

		if (soliPersona)
			setTitle("Lista de Solicitudes de " + persona.getNombre());
		else if (soliEmpresa)
			setTitle("Lista de Solicitudes de " + empresa.getNombre());

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(new BorderLayout(0, 0));
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panel.add(scrollPane, BorderLayout.CENTER);
				{
					model = new DefaultTableModel();
					String[] columnas = { "Codigo", "Cliente", "Cedula/RNC", "Nombre", "Tipo", "Cantidad", "Estado" };
					model.setColumnIdentifiers(columnas);
					table = new JTable();
					table.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							int rowSelected = -1;
							rowSelected = table.getSelectedRow();
							if (rowSelected >= 0)
							{
								btnEliminar.setEnabled(true);
								btnMostrar.setEnabled(true);
								selected = Bolsa.getInstance()
										.buscarOfertaByCodigo(Integer.getInteger(table.getValueAt(rowSelected, 0).toString()));
							}
						}
					});
					table.setModel(model);
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnMostrar = new JButton("Mostrar");
				btnMostrar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{

						MostrarSolicitud soli = new MostrarSolicitud(selected);
						soli.setModal(true);
						soli.setVisible(true);

					}
				});
				btnMostrar.setActionCommand("OK");
				buttonPane.add(btnMostrar);
				getRootPane().setDefaultButton(btnMostrar);
			}
			{
				btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						int option;
						if (selected != null)
						{
							option = JOptionPane.showConfirmDialog(null,
									"Está seguro que desea eliminar la solicitud con código: " + selected.getCodigo(),
									"Confirmación", JOptionPane.YES_NO_OPTION);
							if (option == JOptionPane.OK_OPTION)
							{
								Bolsa.getInstance().eliminarSolicitud(selected);
								loadSolicitudes();
							}
						}
					}
				});
				buttonPane.add(btnEliminar);
			}
			{
				btnSalir = new JButton("Salir");
				btnSalir.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						dispose();
					}
				});
				btnSalir.setActionCommand("Cancel");
				buttonPane.add(btnSalir);
			}
		}
		loadSolicitudes();
	}
	private void loadOferta()
	{
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		String selectQuery = "select Codigo, RNC, Nivel_Educativo_Deseado, Contrato, Mobilidad, Licencia, Sueldo,id_carrera,id_area,id_idioma,id_ciudad,Activa from Oferta_Empresa where ";
        try (Connection connection1 = DriverManager.getConnection(Bolsa.getDbUrl(),Bolsa.getUsername(), Bolsa.getPassword());
             Statement statement = connection1.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            // Recorrer el resultado del conjunto de resultados (ResultSet)
            while (resultSet.next()) {
                 rows[0] = String.valueOf(resultSet.getInt("Codigo"));
                 rows[1] = resultSet.getString("RNC");
                 rows[2] = resultSet.getString("Nivel_Educativo_Deseado");
                 rows[3] = resultSet.getString("Contrato");
                 rows[4] = resultSet.getString("Mobilidad");
                 rows[5] = resultSet.getString("Licencia");
                 rows[6] = String.valueOf(resultSet.getFloat("Sueldo"));
                 rows[7] = resultSet.getString("id_carrera");
                 rows[8] = resultSet.getString("id_area");
                 rows[9] = resultSet.getString("id_idioma");
                 rows[10] = resultSet.getString("id_ciudad");
                 rows[11] = resultSet.getString("Activa");
                 
                 if(match && resultSet.getString("Activa").equalsIgnoreCase("No"));
                 
                 else
               	  model.addRow(rows);
            }

        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/*for (Solicitud solicitud : Bolsa.getInstance().getSolicitudes())
		{
			if (solicitud instanceof SoliPersona)
				persona = Bolsa.getInstance().buscarPersonaByCedula(((SoliPersona) solicitud).getCedula());
			if (solicitud instanceof SoliEmpresa)
				empresa = Bolsa.getInstance().buscarEmpresaByRNC(((SoliEmpresa) solicitud).getRnc());

			if (persona != null || empresa != null)
			{
				rows[0] = solicitud.getCodigo();

				rows[1] = solicitud instanceof SoliEmpresa ? "Empresa" : solicitud instanceof SoliPersona ? "Persona" : "";
				rows[2] = solicitud instanceof SoliEmpresa ? ((SoliEmpresa) solicitud).getRnc()
						: solicitud instanceof SoliPersona ? ((SoliPersona) solicitud).getCedula() : "";

				rows[3] = solicitud instanceof SoliEmpresa ? empresa.getNombre()
						: solicitud instanceof SoliPersona ? persona.getNombre() : "";

				if (solicitud instanceof SoliPersona)
					rows[4] = persona instanceof Universitario ? "Universatario"
							: persona instanceof Tecnico ? "Tecnico" : "";

				else if (solicitud instanceof SoliEmpresa)
					rows[4] = solicitud instanceof EmpUniversitario ? "Universatario"
							: solicitud instanceof EmpTecnico ? "Tecnico" : "";

				rows[5] = solicitud instanceof SoliPersona ? "1"
						: solicitud instanceof SoliEmpresa ? "1" : "";

				rows[6] = solicitud.isActiva() ? "Activa" : "Inactiva";

				persona = null;
				empresa = null;

				if ((solicitud instanceof SoliPersona && match))
					;
				else if (match && !solicitud.isActiva())
					;
				else
					model.addRow(rows);
			}
		}*/
	}
	private void loadSolicitudes()
	{
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		String selectQuery = "select Codigo, Cedula, Nivel_Educativo, Contrato, Mobilidad, Licencia, Sueldo,id_carrera,id_area,id_idioma,id_ciudad,Activa from Solicitud_Persona";
        try (Connection connection1 = DriverManager.getConnection(Bolsa.getDbUrl(),Bolsa.getUsername(), Bolsa.getPassword());
             Statement statement = connection1.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            // Recorrer el resultado del conjunto de resultados (ResultSet)
            while (resultSet.next()) {
                 rows[0] = resultSet.getInt("Codigo");
                 rows[1] = resultSet.getString("Cedula");
                 rows[2] = resultSet.getString("Nivel_Educativo");
                 rows[3] = resultSet.getString("Contrato");
                 rows[4] = resultSet.getString("Mobilidad");
                 rows[5] = resultSet.getString("Licencia");
                 rows[6] = resultSet.getFloat("Sueldo");
                 rows[7] = resultSet.getString("id_carrera");
                 rows[8] = resultSet.getString("id_area");
                 rows[9] = resultSet.getString("id_idioma");
                 rows[10] = resultSet.getString("id_ciudad");
                 rows[11] = resultSet.getString("Activa");
                 model.addRow(rows);
            }

        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/*for (Solicitud solicitud : Bolsa.getInstance().getSolicitudes())
		{
			if (solicitud instanceof SoliPersona)
				persona = Bolsa.getInstance().buscarPersonaByCedula(((SoliPersona) solicitud).getCedula());
			if (solicitud instanceof SoliEmpresa)
				empresa = Bolsa.getInstance().buscarEmpresaByRNC(((SoliEmpresa) solicitud).getRnc());

			if (persona != null || empresa != null)
			{
				rows[0] = solicitud.getCodigo();

				rows[1] = solicitud instanceof SoliEmpresa ? "Empresa" : solicitud instanceof SoliPersona ? "Persona" : "";
				rows[2] = solicitud instanceof SoliEmpresa ? ((SoliEmpresa) solicitud).getRnc()
						: solicitud instanceof SoliPersona ? ((SoliPersona) solicitud).getCedula() : "";

				rows[3] = solicitud instanceof SoliEmpresa ? empresa.getNombre()
						: solicitud instanceof SoliPersona ? persona.getNombre() : "";

				if (solicitud instanceof SoliPersona)
					rows[4] = persona instanceof Universitario ? "Universatario"
							: persona instanceof Tecnico ? "Tecnico" : "";

				else if (solicitud instanceof SoliEmpresa)
					rows[4] = solicitud instanceof EmpUniversitario ? "Universatario"
							: solicitud instanceof EmpTecnico ? "Tecnico" : "";

				rows[5] = solicitud instanceof SoliPersona ? "1"
						: solicitud instanceof SoliEmpresa ? "1" : "";

				rows[6] = solicitud.isActiva() ? "Activa" : "Inactiva";

				persona = null;
				empresa = null;

				if ((solicitud instanceof SoliPersona && match))
					;
				else if (match && !solicitud.isActiva())
					;
				else
					model.addRow(rows);
			}
		}*/
	}
	/*private void loadSolicitudes()
	{
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		Persona persona = null;
		Empresa empresa = null;
		for (Solicitud solicitud : Bolsa.getInstance().getSolicitudes())
		{
			if (solicitud instanceof SoliPersona)
				persona = Bolsa.getInstance().buscarPersonaByCedula(((SoliPersona) solicitud).getCedula());
			if (solicitud instanceof SoliEmpresa)
				empresa = Bolsa.getInstance().buscarEmpresaByRNC(((SoliEmpresa) solicitud).getRnc());

			if (persona != null || empresa != null)
			{
				rows[0] = solicitud.getCodigo();

				rows[1] = solicitud instanceof SoliEmpresa ? "Empresa" : solicitud instanceof SoliPersona ? "Persona" : "";
				rows[2] = solicitud instanceof SoliEmpresa ? ((SoliEmpresa) solicitud).getRnc()
						: solicitud instanceof SoliPersona ? ((SoliPersona) solicitud).getCedula() : "";

				rows[3] = solicitud instanceof SoliEmpresa ? empresa.getNombre()
						: solicitud instanceof SoliPersona ? persona.getNombre() : "";

				if (solicitud instanceof SoliPersona)
					rows[4] = persona instanceof Universitario ? "Universatario"
							: persona instanceof Tecnico ? "Tecnico" : "";

				else if (solicitud instanceof SoliEmpresa)
					rows[4] = solicitud instanceof EmpUniversitario ? "Universatario"
							: solicitud instanceof EmpTecnico ? "Tecnico" : "";

				rows[5] = solicitud instanceof SoliPersona ? "1"
						: solicitud instanceof SoliEmpresa ? "1" : "";

				rows[6] = solicitud.isActiva() ? "Activa" : "Inactiva";

				if (persona != null && soliPersona && solicitud instanceof SoliPersona
						&& this.persona.getId().equalsIgnoreCase(persona.getId()))
					model.addRow(rows);
				else if (empresa != null && soliEmpresa && solicitud instanceof SoliEmpresa
						&& this.empresa.getRnc().equalsIgnoreCase(empresa.getRnc()))
					model.addRow(rows);

				persona = null;
				empresa = null;
			}
		}
	}*/

}
