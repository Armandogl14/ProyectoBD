package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import javax.swing.JSpinner;
import javax.swing.JTextField;

import javax.swing.SpinnerNumberModel;

import javax.swing.border.TitledBorder;

import logico.Bolsa;

import logico.Persona;
import logico.SoliPersona;
import logico.Tecnico;
import logico.Universitario;

@SuppressWarnings("serial")
public class SolPersona extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCedula;
	private JTextField txtNombre;
	private JButton btnBuscar;
	private JTextField txtDireccion;
	private JRadioButton rdbtnLicenciaSi;
	private JRadioButton rdbtnLicenciaNo;
	private JRadioButton rdbtnMudarseNo;
	private JRadioButton rdbtnMudarseSi;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxArea;
	private JLabel lblcarrera;
	private JLabel lblarea;
	private JSpinner spnAgnos;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxContrato;
	private JSpinner spnSalario;
	private JTextField txtIdiomas;
	private JButton btnSolicitar;
	private JButton btnCancelar;
	private JTextField txtTelefono;
	private JPanel PanelTipoSolicitud;
	private JRadioButton rdbtnUniversitario;
	private JRadioButton rdbtnTecnico;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxCiudad;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxCarrera;
	private JLabel lblagnos;
	private boolean mov = false;
	private boolean lic = false;
	private DefaultListModel<String> ModelActividades;
	private JPanel PanelAptitudes;
	private SoliPersona solicitud;
	private ArrayList<String> ciudades = obtenerCiudadesDesdeBaseDeDatos();
	private ArrayList<String> areas = obtenerAreasDesdeBaseDeDatos();
	private ArrayList<String> carreras = obtenerCarrerassDesdeBaseDeDatos();
	private Connection  conexion = Bolsa.abrirConexion();
	private String insertSoli = "Insert into Solicitud_Persona (Mobilidad, Licencia, Nivel_Educativo, Sueldo, Activa, Cedula, id_carrera, id_area, id_idioma, id_ciudad) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private String insertPersona = "insert into Persona (Cedula, Nombre, Telefono, Direccion, Contratado, Nivel_Educativo, id_ciudad) values (?, ?, ?, ?, ?, ?, ?)";
	private String mobilidadStr = "No";
	private String licenciaStr = "No";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SolPersona(SoliPersona aux)
	{
		solicitud = aux;
		setTitle("Registrar Solicitud de Persona");
		if (solicitud != null)
			setTitle("Modificar Solicitud de " + solicitud.getCedula());
		setBounds(100, 100, 613, 848);
		setResizable(false);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JPanel PanelDatos = new JPanel();
			PanelDatos.setBorder(new TitledBorder(null, "Datos:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			PanelDatos.setBounds(10, 11, 561, 203);
			panel.add(PanelDatos);
			PanelDatos.setLayout(null);

			JLabel lblNewLabel = new JLabel("Cedula:");
			lblNewLabel.setBounds(10, 27, 56, 16);
			PanelDatos.add(lblNewLabel);

			txtCedula = new JTextField();
			txtCedula.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyTyped(KeyEvent e)
				{
					char c = e.getKeyChar();
					if ((!Character.isDigit(c)) && (c != KeyEvent.VK_BACK_SPACE))
						e.consume();
				}
			});
			txtCedula.setBounds(80, 25, 163, 20);
			PanelDatos.add(txtCedula);
			txtCedula.setColumns(10);

			JLabel lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setBounds(10, 70, 56, 16);
			PanelDatos.add(lblNewLabel_1);

			txtNombre = new JTextField();
			txtNombre.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyTyped(KeyEvent e)
				{
					char c = e.getKeyChar();
					if ((!Character.isAlphabetic(c) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_SPACE)))
						e.consume();
				}
			});
			txtNombre.setEditable(false);
			txtNombre.setBounds(80, 68, 210, 20);
			PanelDatos.add(txtNombre);
			txtNombre.setColumns(10);

			btnBuscar = new JButton("Buscar");
			btnBuscar.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					Persona aux = Bolsa.getInstance().buscarPersonaByCedula(txtCedula.getText());
					if (aux != null)
					{
						txtNombre.setText(aux.getNombre());
						txtTelefono.setText(aux.getTelefono());
						txtDireccion.setText(aux.getDireccion());
						if (aux instanceof Universitario)
						{
							rdbtnUniversitario.setSelected(true);
							rdbtnTecnico.setEnabled(false);

						}
						else if (aux instanceof Tecnico)
						{
							rdbtnUniversitario.setSelected(false);
							rdbtnTecnico.setSelected(true);
							rdbtnUniversitario.setEnabled(false);
						}
					}
					else
					{
						txtNombre.setEditable(true);
						txtTelefono.setEditable(true);
						txtDireccion.setEditable(true);
					}
				}
			});
			btnBuscar.setBounds(454, 23, 97, 25);
			PanelDatos.add(btnBuscar);

			JLabel lblNewLabel_2 = new JLabel("Telefono:");
			lblNewLabel_2.setBounds(10, 113, 56, 16);
			PanelDatos.add(lblNewLabel_2);

			txtTelefono = new JTextField();
			txtTelefono.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyTyped(KeyEvent e)
				{
					char c = e.getKeyChar();
					if ((!Character.isDigit(c)) && (c != KeyEvent.VK_BACK_SPACE))
						e.consume();
				}
			});
			txtTelefono.setEditable(false);
			txtTelefono.setBounds(80, 111, 178, 20);
			PanelDatos.add(txtTelefono);
			txtTelefono.setColumns(10);

			JLabel lblNewLabel_3 = new JLabel("Direccion:");
			lblNewLabel_3.setBounds(10, 156, 66, 16);
			PanelDatos.add(lblNewLabel_3);

			txtDireccion = new JTextField();
			txtDireccion.setEditable(false);
			txtDireccion.setBounds(80, 154, 300, 20);
			PanelDatos.add(txtDireccion);
			txtDireccion.setColumns(10);

			PanelAptitudes = new JPanel();
			PanelAptitudes
					.setBorder(new TitledBorder(null, "Aptitudes:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			PanelAptitudes.setBounds(12, 568, 561, 186);
			panel.add(PanelAptitudes);
			PanelAptitudes.setLayout(null);

			lblcarrera = new JLabel("Carrera:");
			lblcarrera.setBounds(12, 77, 56, 16);
			PanelAptitudes.add(lblcarrera);

			cbxCarrera = new JComboBox();
			cbxCarrera.setBounds(72, 77, 208, 20);
			PanelAptitudes.add(cbxCarrera);

			lblarea = new JLabel("Area:");
			lblarea.setBounds(12, 77, 56, 16);
			PanelAptitudes.add(lblarea);

			cbxArea = new JComboBox();
			/*cbxArea.addItemListener(new ItemListener()
			{
				public void itemStateChanged(ItemEvent e)
				{
					if (!cbxArea.getSelectedItem().toString().equalsIgnoreCase("<Selecionar>"))
					{
						cbxCarrera.setEditable(true);
						cbxCarrera.setEnabled(true);

						if (cbxArea.getSelectedItem().toString().equalsIgnoreCase("Ciencias e Ingenieria"))
							cbxCarrera.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Ing. Civil",
									"Ing. Mecanica", "Ing. Electrica", "Ing. Industrial y de Sistemas", "Ing. Mecatronica",
									"Ing. Ciencias de la Computacion", "Ing. Telematica", "Ing. Ambiental" }));

						else if (cbxArea.getSelectedItem().toString().equalsIgnoreCase("Ciencias de la Salud"))
							cbxCarrera.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Estomatologia",
									"Medicina", "Nutricion y Dietetica", "Terapia Fisica" }));

						else if (cbxArea.getSelectedItem().toString().equalsIgnoreCase("Ciencias Administrativas"))
							cbxCarrera.setModel(new DefaultComboBoxModel(
									new String[] { "<Selecionar>", "Direccion Empresarial", "Administracion Hotelera",
											"Economia", "Gesttion Financiera y Adutoria", "Marketing", "Hospitalida y Turismo" }));

						else if (cbxArea.getSelectedItem().toString().equalsIgnoreCase("Ciencias Humanidades y Artes"))
							cbxCarrera.setModel(new DefaultComboBoxModel(
									new String[] { "<Selecionar>", "Arquitectura", "Comunicacion Social", "Derecho",
											"DiseÃ±o e Interiorismo", "Educacion", "Filosofia", "Trabajo Social" }));

						else
							cbxCarrera.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>" }));
					}
				}
			});*/
			//areas.add(0, "<Seleccionar>");
			//cbxArea.setModel(new DefaultComboBoxModel<>(areas.toArray(new String[0])));
			//cbxArea.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Ciencias de la Salud",
			//		"Ciencias e Ingenieria", "Ciencias Administrativas", "Ciencias Humanidades y Artes" }));
			carreras.add(0, "<Seleccionar>");
			areas.add(0, "<Seleccionar>");
			cbxArea.setBounds(72, 75, 208, 20);
			PanelAptitudes.add(cbxArea);

			lblagnos = new JLabel("A\u00F1os:");
			lblagnos.setBounds(327, 78, 46, 14);
			PanelAptitudes.add(lblagnos);

			spnAgnos = new JSpinner();
			spnAgnos.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
			spnAgnos.setBounds(374, 75, 110, 20);
			PanelAptitudes.add(spnAgnos);

			ModelActividades = new DefaultListModel<String>();

			JPanel PanelDatosSolicitud = new JPanel();
			PanelDatosSolicitud.setBorder(
					new TitledBorder(null, "Datos de Solicitud:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			PanelDatosSolicitud.setBounds(10, 225, 561, 252);
			panel.add(PanelDatosSolicitud);
			PanelDatosSolicitud.setLayout(null);

			JLabel lblNewLabel_7 = new JLabel("Contrato:");
			lblNewLabel_7.setBounds(12, 42, 56, 16);
			PanelDatosSolicitud.add(lblNewLabel_7);

			cbxContrato = new JComboBox();
			cbxContrato.setModel(new DefaultComboBoxModel(
					new String[] { "<Selecionar>", "Jornada Completa", "Media Jornada", "Jornada Mixta" }));
			cbxContrato.setBounds(78, 40, 147, 20);
			PanelDatosSolicitud.add(cbxContrato);

			JLabel lblNewLabel_9 = new JLabel("Salario:");
			lblNewLabel_9.setBounds(12, 104, 56, 16);
			PanelDatosSolicitud.add(lblNewLabel_9);

			spnSalario = new JSpinner();
			spnSalario.setModel(new SpinnerNumberModel(new Float(1000), new Float(1), null, new Float(500)));
			spnSalario.setBounds(78, 102, 93, 20);
			PanelDatosSolicitud.add(spnSalario);

			JLabel lblNewLabel_4 = new JLabel("Licencia:");
			lblNewLabel_4.setBounds(307, 178, 56, 16);
			PanelDatosSolicitud.add(lblNewLabel_4);

			rdbtnLicenciaSi = new JRadioButton("Si");
			rdbtnLicenciaSi.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					rdbtnLicenciaNo.setSelected(false);
				}
			});
			rdbtnLicenciaSi.setBounds(373, 174, 48, 25);
			PanelDatosSolicitud.add(rdbtnLicenciaSi);

			rdbtnLicenciaNo = new JRadioButton("No");
			rdbtnLicenciaNo.setSelected(true);
			rdbtnLicenciaNo.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					rdbtnLicenciaSi.setSelected(false);
				}
			});
			rdbtnLicenciaNo.setBounds(423, 174, 48, 25);
			PanelDatosSolicitud.add(rdbtnLicenciaNo);

			JLabel lblNewLabel_5 = new JLabel("Puede mudarse:");
			lblNewLabel_5.setBounds(289, 106, 97, 16);
			PanelDatosSolicitud.add(lblNewLabel_5);

			rdbtnMudarseSi = new JRadioButton("Si");
			rdbtnMudarseSi.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					rdbtnMudarseNo.setSelected(false);
				}
			});
			rdbtnMudarseSi.setBounds(392, 102, 48, 25);
			PanelDatosSolicitud.add(rdbtnMudarseSi);

			rdbtnMudarseNo = new JRadioButton("No");
			rdbtnMudarseNo.setSelected(true);
			rdbtnMudarseNo.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					rdbtnMudarseSi.setSelected(false);
				}
			});
			rdbtnMudarseNo.setBounds(442, 102, 53, 25);
			PanelDatosSolicitud.add(rdbtnMudarseNo);

			JLabel lblNewLabel_11 = new JLabel("Idioma:");
			lblNewLabel_11.setBounds(289, 40, 56, 16);
			PanelDatosSolicitud.add(lblNewLabel_11);

			txtIdiomas = new JTextField();
			txtIdiomas.setBounds(355, 38, 116, 20);
			PanelDatosSolicitud.add(txtIdiomas);
			txtIdiomas.setColumns(10);

			JLabel label = new JLabel("Ciudad:");
			label.setBounds(12, 177, 46, 14);
			PanelDatosSolicitud.add(label);

			cbxCiudad = new JComboBox();
			/*cbxCiudad.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Azua", "Bahoruco", "Barahona",
					"Dajabon", "Distrito Nacional", "Duarte", "El Seybo", "Elias PiÃ±a", "Espaillat", "Hato Mayor",
					"Hermanas Mirabal", "Independencia", "La Altagracia", "La Romana", "La Vega", "Maria Trinidad Sanchez",
					"MonseÃ±or Nouel", "Monte Plata", "Montecristi", "Pedernales", "Peravia", "Puerto Plata", "Samana",
					"San Cristobal", "San Jose De Ocoa", "San Juan", "San Pedro De Macoris", "Sanchez Ramirez", "Santiago",
					"Santiago Rodriguez", "Santo Domingo", "Valverde" }));*/
			ciudades.add(0, "<Seleccionar>");
			cbxCiudad.setModel(new DefaultComboBoxModel<>(ciudades.toArray(new String[0])));
			cbxCiudad.setBounds(78, 174, 147, 20);
			PanelDatosSolicitud.add(cbxCiudad);

			PanelTipoSolicitud = new JPanel();
			PanelTipoSolicitud.setBounds(12, 485, 561, 72);
			panel.add(PanelTipoSolicitud);
			PanelTipoSolicitud.setLayout(null);
			PanelTipoSolicitud.setBorder(
					new TitledBorder(null, "Tipo de Solicitud:", TitledBorder.LEADING, TitledBorder.TOP, null, null));

			rdbtnUniversitario = new JRadioButton("Universatario");
			rdbtnUniversitario.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					rdbtnTecnico.setSelected(false);
					lblarea.setVisible(false);
					cbxArea.setVisible(false);
					lblcarrera.setVisible(true);
					cbxCarrera.setVisible(true);
					cbxCarrera.setEnabled(true);
					cbxCarrera.setModel(new DefaultComboBoxModel<>(carreras.toArray(new String[0])));
					lblagnos.setVisible(true);
					spnAgnos.setVisible(true);
					
				}
			});
			
			rdbtnUniversitario.setSelected(true);
			rdbtnUniversitario.setBounds(117, 27, 108, 23);
			PanelTipoSolicitud.add(rdbtnUniversitario);

			rdbtnTecnico = new JRadioButton("Tecnico");
			rdbtnTecnico.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					rdbtnUniversitario.setSelected(false);
					lblarea.setVisible(true);
					cbxArea.setVisible(true);
					lblcarrera.setVisible(false);
					cbxCarrera.setVisible(false);

					cbxArea.setModel(new DefaultComboBoxModel<>(areas.toArray(new String[0])));
				}
			});
			rdbtnTecnico.setBounds(330, 27, 81, 23);
			PanelTipoSolicitud.add(rdbtnTecnico);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnSolicitar = new JButton("Solicitar");
				if (solicitud != null)
					btnSolicitar.setText("Modificar");
				btnSolicitar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (solicitud == null)
						{
							if (validar())
							{
								Persona auxPerson = Bolsa.getInstance().buscarPersonaByCedula(txtCedula.getText());
								if (auxPerson == null)
								{
									if (rdbtnUniversitario.isSelected())
									{
										try {
											PreparedStatement queryPerson = conexion.prepareStatement(insertPersona);
											
											queryPerson.setString(1, txtCedula.getText());
											queryPerson.setString(2, txtNombre.getText());
											queryPerson.setString(3, txtTelefono.getText());
											queryPerson.setString(4, txtDireccion.getText());
											queryPerson.setString(5, "No");
											queryPerson.setString(6, "Universitario");
											queryPerson.setString(7, cbxCiudad.getSelectedItem().toString().substring(0, cbxCiudad.getSelectedItem().toString().indexOf(" ")));
										} catch (SQLException e1){
											System.err.println("Error.");
										}
									}

									else if (rdbtnTecnico.isSelected())
									{
										try {
											PreparedStatement queryPerson = conexion.prepareStatement(insertPersona);
											
											queryPerson.setString(1, txtCedula.getText());
											queryPerson.setString(2, txtNombre.getText());
											queryPerson.setString(3, txtTelefono.getText());
											queryPerson.setString(4, txtDireccion.getText());
											queryPerson.setString(5, "No");
											queryPerson.setString(6, "Tecnico");
											queryPerson.setString(7, cbxCiudad.getSelectedItem().toString().substring(0, cbxCiudad.getSelectedItem().toString().indexOf(" ")));
										} catch (SQLException e1){
											System.err.println("Error.");
										}
									}
								}

								if (rdbtnMudarseSi.isSelected())
									mobilidadStr = "Si";

								if (rdbtnLicenciaSi.isSelected())
									licenciaStr = "Si";

								/*SoliPersona soli = new SoliPersona(txtCodigo.getText(), mov,
										cbxContrato.getSelectedItem().toString(), lic, cbxCiudad.getSelectedItem().toString(),
										Float.valueOf(spnSalario.getValue().toString()), idiomasAux, txtCedula.getText());
								Bolsa.getInstance().addSolicitud(soli);*/
								if (auxPerson.isContratado())
									Bolsa.getInstance().desactivarSoliPersona(auxPerson.getId());

								JOptionPane.showMessageDialog(null, "Solicitud Ingresada", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);

								clean();
							}

							else
								JOptionPane.showMessageDialog(null, "Solicitud Incompleta", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);
						}

						else
						{
							Persona person = Bolsa.getInstance().buscarPersonaByCedula(solicitud.getCedula());
							if (validar())
							{
								if (rdbtnMudarseSi.isSelected())
									mov = true;

								if (rdbtnLicenciaSi.isSelected())
									lic = true;

								solicitud.setContrato(cbxContrato.getSelectedItem().toString());
								solicitud.setSueldo(Float.valueOf(spnSalario.getValue().toString()));
								solicitud.setCuidad(cbxCiudad.getSelectedItem().toString());
								solicitud.setIdiomas(txtIdiomas.getText());
								solicitud.setLicencia(lic);
								solicitud.setMovilidad(mov);

								if (person instanceof Universitario)
								{
									((Universitario) person).setCarrera(cbxCarrera.getSelectedItem().toString());
									((Universitario) person).setAgnos(Integer.valueOf(spnAgnos.getValue().toString()));
								}

								else if (person instanceof Tecnico)
								{
									((Tecnico) person).setArea(cbxArea.getSelectedItem().toString());
									((Tecnico) person).setAgnos(Integer.valueOf(spnAgnos.getValue().toString()));
								}

								JOptionPane.showMessageDialog(null, "Modificacion Realizada", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);
							}
							else
								JOptionPane.showMessageDialog(null, "Modificacion Incompleta", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);
						}

					}
				});
				btnSolicitar.setActionCommand("OK");
				buttonPane.add(btnSolicitar);
				getRootPane().setDefaultButton(btnSolicitar);
			}
			{
				btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}

		if (solicitud != null)
		{
			Persona person = Bolsa.getInstance().buscarPersonaByCedula(solicitud.getCedula());
			txtCedula.setText(solicitud.getCedula());
			if (person != null)
			{
				txtNombre.setText(person.getNombre());
				txtTelefono.setText(person.getTelefono());
				txtDireccion.setText(person.getDireccion());
			}
			txtCedula.setEditable(false);
		}
		loadSolicitud();
	}

	private void loadSolicitud()
	{
		if (solicitud != null)
		{
			Persona person = Bolsa.getInstance().buscarPersonaByCedula(solicitud.getCedula());
			txtCedula.setText(solicitud.getCedula());
			txtNombre.setText(person.getNombre());
			txtTelefono.setText(person.getTelefono());
			txtDireccion.setText(person.getDireccion());
			if (solicitud.getContrato().equalsIgnoreCase("Jornada Completa"))
				cbxContrato.setSelectedIndex(1);
			else if (solicitud.getContrato().equalsIgnoreCase("Media Jornada"))
				cbxContrato.setSelectedIndex(2);
			else if (solicitud.getContrato().equalsIgnoreCase("Jornada Mixta"))
				cbxContrato.setSelectedIndex(3);

			spnSalario.setValue(solicitud.getSueldo());
			txtIdiomas.setText("");

			if (solicitud.isLicencia())
			{
				rdbtnLicenciaNo.setSelected(false);
				rdbtnLicenciaSi.setSelected(true);
			}
			else
			{
				rdbtnLicenciaNo.setSelected(true);
				rdbtnLicenciaSi.setSelected(false);
			}

			if (solicitud.isMovilidad())
			{
				rdbtnMudarseSi.setSelected(true);
				rdbtnMudarseNo.setSelected(false);
			}
			else
			{
				rdbtnMudarseSi.setSelected(false);
				rdbtnMudarseNo.setSelected(true);
			}

			if (person instanceof Universitario)
			{
				rdbtnUniversitario.setSelected(true);
				rdbtnTecnico.setSelected(false);
				rdbtnTecnico.setEnabled(false);
			}

			else if (person instanceof Tecnico)
			{
				rdbtnUniversitario.setSelected(false);
				rdbtnUniversitario.setEnabled(false);
				rdbtnTecnico.setSelected(true);
			}

			if (rdbtnUniversitario.isSelected() || rdbtnTecnico.isSelected())
				cbxArea.setSelectedIndex(0);

			cbxCiudad.setSelectedIndex(0);

			spnAgnos.setValue(new Integer("0"));

			if (person instanceof Universitario)
				spnAgnos.setValue(((Universitario) person).getAgnos());
			else if (person instanceof Tecnico)
				spnAgnos.setValue(((Tecnico) person).getAgnos());

			txtIdiomas.setText(solicitud.getIdiomas());
			ModelActividades.removeAllElements();
		}
	}

	private void clean()
	{
		txtCedula.setText("");
		txtNombre.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtNombre.setEditable(false);
		txtTelefono.setEditable(false);
		txtDireccion.setEditable(false);
		cbxContrato.setSelectedIndex(0);
		spnSalario.setValue(new Float("1000"));
		txtIdiomas.setText("");
		rdbtnLicenciaNo.setSelected(true);
		rdbtnLicenciaSi.setSelected(false);
		rdbtnMudarseSi.setSelected(false);
		rdbtnMudarseNo.setSelected(true);
		rdbtnUniversitario.setEnabled(true);

		rdbtnTecnico.setEnabled(true);
		rdbtnTecnico.setSelected(false);
		rdbtnTecnico.setSelected(false);
		rdbtnUniversitario.setSelected(true);
		if (rdbtnUniversitario.isSelected() || rdbtnTecnico.isSelected())
			cbxArea.setSelectedIndex(0);
		if (rdbtnUniversitario.isSelected())
			cbxCarrera.setSelectedIndex(0);
		cbxCiudad.setSelectedIndex(0);
		spnAgnos.setValue(new Integer("0"));
		ModelActividades.removeAllElements();
	}

	private boolean validar()
	{

		boolean validado = false;

		if (rdbtnUniversitario.isSelected() && (((txtCedula.getText().length() > 1) && (txtNombre.getText().length() > 1)
				&& (txtTelefono.getText().length() > 1) && (txtDireccion.getText().length() > 1)
				&& (cbxContrato.getSelectedIndex() > 0)) && (txtIdiomas.getText().length() > 1) && (cbxArea.getSelectedIndex() > 0)
				&& (cbxCarrera.getSelectedIndex() > 0) && (cbxCiudad.getSelectedIndex() > 0)))
			validado = true;
		else if (rdbtnTecnico.isSelected() && (((txtCedula.getText().length() > 1) && (txtNombre.getText().length() > 1)
				&& (txtTelefono.getText().length() > 1) && (txtDireccion.getText().length() > 1)
				&& (cbxContrato.getSelectedIndex() > 0) && (txtIdiomas.getText().length() > 1) && (cbxArea.getSelectedIndex() > 0)
				&& (cbxCiudad.getSelectedIndex() > 0))))
			validado = true;
	
		return validado;
	}
	 private ArrayList<String> obtenerCiudadesDesdeBaseDeDatos() {
		 ArrayList<String> ciudades = new ArrayList<>();
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;

	        try {
	            // Obtener la conexión con la base de datos usando tu función para abrir la conexión
	            conn = Bolsa.abrirConexion();

	            stmt = conn.createStatement();

	            // Ejecutar la consulta SQL para obtener las ciudades desde la tabla correspondiente
	            String sql = "select id_ciudad, nombre_ciudad from Ciudad"; // Reemplaza "tabla_ciudades" por el nombre de tu tabla
	            rs = stmt.executeQuery(sql);

	            // Recopilar los nombres de las ciudades en el ArrayList
	            while (rs.next()) {
	                String ciudad = rs.getString("id_ciudad");
	                String ciudad_nombre = rs.getString("nombre_ciudad");
	                ciudades.add(ciudad+ " - " + ciudad_nombre);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Cerrar recursos (result set, statement y conexión)
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return ciudades;
	    }
	 private ArrayList<String> obtenerAreasDesdeBaseDeDatos() {
		 ArrayList<String> areas = new ArrayList<>();
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;

	        try {
	            // Obtener la conexión con la base de datos usando tu función para abrir la conexión
	            conn = Bolsa.abrirConexion();

	            stmt = conn.createStatement();

	            // Ejecutar la consulta SQL para obtener las ciudades desde la tabla correspondiente
	            String sql = "select id_area, nombre_area from Area"; // Reemplaza "tabla_ciudades" por el nombre de tu tabla
	            rs = stmt.executeQuery(sql);

	            // Recopilar los nombres de las ciudades en el ArrayList
	            while (rs.next()) {
	                String id = rs.getString("id_area");
	                String nombre = rs.getString("nombre_area");
	                areas.add(id+ " - " + nombre);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Cerrar recursos (result set, statement y conexión)
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return areas;
	    }
	 private ArrayList<String> obtenerCarrerassDesdeBaseDeDatos() {
		 ArrayList<String> carreras = new ArrayList<>();
	        Connection conn = null;
	        Statement stmt = null;
	        ResultSet rs = null;

	        try {
	            // Obtener la conexión con la base de datos usando tu función para abrir la conexión
	            conn = Bolsa.abrirConexion();

	            stmt = conn.createStatement();

	            // Ejecutar la consulta SQL para obtener las ciudades desde la tabla correspondiente
	            String sql = "select id_carrera,nombre_carrera from Carrera"; // Reemplaza "tabla_ciudades" por el nombre de tu tabla
	            rs = stmt.executeQuery(sql);

	            // Recopilar los nombres de las ciudades en el ArrayList
	            while (rs.next()) {
	                String id = rs.getString("id_carrera");
	                String nombre = rs.getString("nombre_carrera");
	                carreras.add(id+ " - " + nombre);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            // Cerrar recursos (result set, statement y conexión)
	            try {
	                if (rs != null) rs.close();
	                if (stmt != null) stmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return carreras;
	    }
}
