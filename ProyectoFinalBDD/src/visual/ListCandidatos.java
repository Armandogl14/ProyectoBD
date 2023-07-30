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
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import logico.Bolsa;
import logico.Empresa;
import logico.Persona;
import logico.SoliEmpresa;
import logico.SoliPersona;
import logico.Solicitud;
import logico.Tecnico;
import logico.Universitario;

@SuppressWarnings("serial")
public class ListCandidatos extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private JButton btnSalir;
	private static DefaultTableModel model;
	private static Object[] rows;
	private Solicitud selected = null;
	Persona persona = null;
	Empresa empresa = null;
	private JButton btnSelecionar;
	private SoliEmpresa solicitudEmpresa = null;
	private JButton btnMostrar;

	public ListCandidatos(SoliEmpresa aux)

	{
		solicitudEmpresa = aux;
		setTitle("Lista de Cadidatos");
		setBounds(100, 100, 683, 505);
		setLocationRelativeTo(null);
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
					String[] columnas = { "Codigo", "Cedula", "Nivel", "Tipo", "Especialidad", "Porcentaje" };
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
								btnSelecionar.setEnabled(true);
								btnMostrar.setEnabled(true);
								selected = Bolsa.getInstance().buscarSolicitudByCodigo(Integer.parseInt(table.getValueAt(rowSelected, 0).toString()));

							}
						}
					});
					table.setModel(model);
					table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
					scrollPane.setViewportView(table);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				{
					btnMostrar = new JButton("Mostrar");
					btnMostrar.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							MostrarSolicitud mostrar = new MostrarSolicitud(selected);
							mostrar.setModal(true);
							mostrar.setVisible(true);
						}
					});
					btnSelecionar = new JButton("Selecionar");
					btnSelecionar.addActionListener(new ActionListener()
					{

						public void actionPerformed(ActionEvent e)
						{
							MostrarMatch match = new MostrarMatch((SoliPersona) selected, solicitudEmpresa);
							match.setModal(true);
							match.setVisible(true);
							loadSolicitudes();
						}
					});
					btnSelecionar.setEnabled(false);
					buttonPane.add(btnSelecionar);
					btnMostrar.setEnabled(false);
					buttonPane.add(btnMostrar);
				}
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

	private void loadSolicitudes()
	{
		model.setRowCount(0);
		rows = new Object[model.getColumnCount()];
		String selectQuery = "select Codigo, Cedula, Nivel_Educativo_Deseado, id_carrera, id_area from Solicitud_Persona";
		SoliPersona auxSolPerson = null;
		Persona auxPerson = null;

		try (Connection connection1 = DriverManager.getConnection(Bolsa.getDbUrl(),Bolsa.getUsername(), Bolsa.getPassword());
				Statement statement = connection1.createStatement();
				ResultSet resultSet = statement.executeQuery(selectQuery)) {

			while (resultSet.next()) {
				auxSolPerson = Bolsa.getInstance().buscarSolicitudByCodigo(resultSet.getInt("Codigo"));		
				auxPerson = Bolsa.getInstance().buscarPersonaByCedula(resultSet.getString("Cedula"));
				float porcentaje = Bolsa.getInstance().match(solicitudEmpresa, auxSolPerson);
				
				rows[0] = resultSet.getInt("Codigo");
				rows[1] = auxPerson.getId();
				rows[2] = auxPerson.getNombre();
				rows[3] = resultSet.getString("Nivel_Educativo");
				if(resultSet.getString("Nivel_Educativo").equalsIgnoreCase("Universitario"))
					rows[4] = resultSet.getString("id_carrera");
				
				if(resultSet.getString("Nivel_Educativo").equalsIgnoreCase("Tecnico"))
					rows[4] = resultSet.getString("id_area");
				
				rows[5] = String.valueOf(porcentaje);
				
				if(porcentaje >= solicitudEmpresa.getPorcentajeMacth() && Bolsa.getInstance().isSolicitudActiva(resultSet.getInt("Codigo")))
					model.addRow(rows);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		/*for (Solicitud solicitud : Bolsa.getInstance().getSolicitudes())
		{
			if (solicitud instanceof SoliPersona)
			{
				persona = Bolsa.getInstance().buscarPersonaByCedula(((SoliPersona) solicitud).getCedula());
				float porcentaje = Bolsa.getInstance().match(solicitudEmpresa, (SoliPersona) solicitud);

				if (persona != null)
				{
					rows[0] = solicitud.getCodigo();
					rows[1] = ((SoliPersona) solicitud).getCedula();
					rows[2] = persona.getNombre();
					if (solicitud instanceof SoliPersona)
						rows[3] = persona instanceof Universitario ? "Universatario"
								: persona instanceof Tecnico ? "Tecnico" : "";

					rows[4] = persona instanceof Universitario ? ((Universitario) persona).getCarrera()
							: persona instanceof Tecnico ? ((Tecnico) persona).getArea()
									 : "";

					if (porcentaje > 100) //solo ocurre con los idiomas repetidos
						porcentaje = 100;
					rows[5] = String.valueOf(porcentaje);

					persona = null;

					if (porcentaje >= solicitudEmpresa.getPorcentajeMacth() && (solicitud.isActiva()))
						model.addRow(rows);
				}
			}
		}*/
	}
}