package visual;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import logico.Bolsa;
import logico.EmpTecnico;
import logico.EmpUniversitario;
import logico.Empresa;
import logico.SoliEmpresa;
import logico.Solicitud;

@SuppressWarnings({ "serial", "unused" })
public class SolEmpresa extends JDialog
{
	private final JPanel contentPanel = new JPanel();
	private JTextField txtRNC;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxArea;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxCarrera;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxCiudad;
	private JSpinner spnSalario;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxContrato;
	private JRadioButton rdbtnLicenciaSi;
	private JRadioButton rdbtnLicenciaNo;
	private JRadioButton rdbtnMudarseSi;
	private JRadioButton rdbtnMudarseNo;
	private JRadioButton rdbtnUniversitario;
	private JRadioButton rdbtnTecnico;
	private JButton btnSolicitar;
	private JButton btnCancelar;
	private JLabel lblarea;
	private JLabel lblcarrera;
	private JLabel lblagnos;
	private JSpinner spnAgnos;
	private JSpinner spnPorcentaje;
	private boolean mov = false;
	private boolean lic = false;
	private String idioma;
	private DefaultListModel<String> ModelActividades;
	private SoliEmpresa solicitud = null; // mod
	private ArrayList<String> ciudades = obtenerCiudadesDesdeBaseDeDatos();
	private ArrayList<String> areas = obtenerAreasDesdeBaseDeDatos();
	private ArrayList<String> carreras = obtenerCarrerassDesdeBaseDeDatos();
	private ArrayList<String> idiomas = obteneridiomasDesdeBaseDeDatos();
	@SuppressWarnings("rawtypes")
	private JComboBox cbxIdiomas;
	private Connection  conexion = Bolsa.abrirConexion();
	private String insertSoli = "Insert into Oferta_Empresa (Mobilidad, Contrato, Licencia, Nivel_Educativo_Deseado, Sueldo, Activa, RNC, id_carrera, id_area, id_idioma, id_ciudad, Agnos_Experiencia, Porcentaje_Match) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private String insertEmp = "insert into Empresa (RNC, Nombre, Telefono, Direccion, id_ciudad) values (?, ?, ?, ?, ?)";
	private String updateSoli = "update Oferta_Empresa set Mobilidad = ?, Contrato = ?, Licencia = ? , Nivel_Educativo_Deseado = ?, Sueldo = ?, Activa = ?, RNC = ? , id_carrera = ?, id_area = ?, id_idioma = ?, id_ciudad = ?, Agnos_Experiencia = ?, Porcentaje_Match = ? where Codigo = ?";
	private String mobilidadStr = "No";
	private String licenciaStr = "No";
	private Empresa auxEmp = null;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SolEmpresa(SoliEmpresa aux1)
	{
		SoliEmpresa  aux = aux1;
		setResizable(false);
		setTitle("Registrar Solicitud de Empresa");
		if (aux != null) // mod
			setTitle("Modificar Solicitud de " + aux.getRnc()); // mod
		setBounds(100, 100, 613, 920);
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
			PanelDatos.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos:",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			PanelDatos.setBounds(10, 11, 561, 203);
			panel.add(PanelDatos);
			PanelDatos.setLayout(null);
			{
				JLabel lblNewLabel = new JLabel("RNC:");
				lblNewLabel.setBounds(10, 29, 46, 14);
				PanelDatos.add(lblNewLabel);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Nombre:");
				lblNewLabel_1.setBounds(10, 72, 65, 14);
				PanelDatos.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_2 = new JLabel("Telefono:");
				lblNewLabel_2.setBounds(10, 115, 65, 14);
				PanelDatos.add(lblNewLabel_2);
			}
			{
				JLabel lblNewLabel_3 = new JLabel("Direccion:");
				lblNewLabel_3.setBounds(10, 158, 65, 14);
				PanelDatos.add(lblNewLabel_3);
			}

			txtRNC = new JTextField();
			txtRNC.addKeyListener(new KeyAdapter()
			{
				@Override
				public void keyTyped(KeyEvent e)
				{
					char c = e.getKeyChar();
					if ((!Character.isDigit(c)) && (c != KeyEvent.VK_BACK_SPACE))
						e.consume();
				}
			});
			txtRNC.setBounds(76, 26, 163, 20);
			PanelDatos.add(txtRNC);

			txtRNC.setColumns(10);
			{
				txtNombre = new JTextField();
				txtNombre.setEditable(false);
				txtNombre.addKeyListener(new KeyAdapter()
				{
					@Override
					public void keyTyped(KeyEvent e)
					{
						char c = e.getKeyChar();
						if ((!Character.isAlphabetic(c) && !(Character.isDigit(c))) && (c != KeyEvent.VK_BACK_SPACE)
								&& (c != KeyEvent.VK_SPACE))
							e.consume();
					}
				});
				txtNombre.setBounds(76, 69, 210, 20);
				PanelDatos.add(txtNombre);
				txtNombre.setColumns(10);
			}
			{
				txtTelefono = new JTextField();
				txtTelefono.setEditable(false);
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
				txtTelefono.setBounds(76, 112, 163, 20);
				PanelDatos.add(txtTelefono);
				txtTelefono.setColumns(10);
			}
			{
				txtDireccion = new JTextField();
				txtDireccion.setEditable(false);
				txtDireccion.setBounds(76, 155, 300, 20);
				PanelDatos.add(txtDireccion);
				txtDireccion.setColumns(10);
			}
			{
				JButton btnBuscar = new JButton("Buscar");
				btnBuscar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						auxEmp = Bolsa.getInstance().buscarEmpresaByRNC(txtRNC.getText());
						if (auxEmp != null)
						{

							txtNombre.setText(auxEmp.getNombre());
							txtTelefono.setText(auxEmp.getTelefono());
							txtDireccion.setText(auxEmp.getDireccion());
						}
						else
						{
							txtNombre.setEditable(true);
							txtTelefono.setEditable(true);
							txtDireccion.setEditable(true);
						}
					}
				});
				btnBuscar.setBounds(439, 25, 89, 23);
				PanelDatos.add(btnBuscar);
			}
			{
				JPanel PanelDatosSolicitud = new JPanel();
				PanelDatosSolicitud.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
						"Datos de Solicitud:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				PanelDatosSolicitud.setBounds(10, 225, 561, 252);
				panel.add(PanelDatosSolicitud);
				PanelDatosSolicitud.setLayout(null);
				{
					JLabel lblNewLabel_4 = new JLabel("Contrato:");
					lblNewLabel_4.setBounds(10, 46, 59, 14);
					PanelDatosSolicitud.add(lblNewLabel_4);
				}
				{
					JLabel lblNewLabel_5 = new JLabel("Salario:");
					lblNewLabel_5.setBounds(10, 115, 46, 14);
					PanelDatosSolicitud.add(lblNewLabel_5);
				}

				spnSalario = new JSpinner();
				spnSalario.setModel(new SpinnerNumberModel(new Float(1000), new Float(0), null, new Float(500)));
				spnSalario.setBounds(66, 112, 93, 20);
				PanelDatosSolicitud.add(spnSalario);
				{
					JLabel lblNewLabel_7 = new JLabel("Ciudad:");
					lblNewLabel_7.setBounds(10, 189, 46, 14);
					PanelDatosSolicitud.add(lblNewLabel_7);
				}
				{
					JLabel lblNewLabel_8 = new JLabel("Puede Mudarse:");
					lblNewLabel_8.setBounds(281, 112, 93, 14);
					PanelDatosSolicitud.add(lblNewLabel_8);
				}
				{
					rdbtnMudarseSi = new JRadioButton("Si");
					rdbtnMudarseSi.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							rdbtnMudarseNo.setSelected(false);
						}
					});
					rdbtnMudarseSi.setBounds(374, 108, 59, 23);
					PanelDatosSolicitud.add(rdbtnMudarseSi);
				}
				{
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
					rdbtnMudarseNo.setBounds(435, 108, 52, 23);
					PanelDatosSolicitud.add(rdbtnMudarseNo);
				}
				{
					JLabel lblNewLabel_9 = new JLabel("Licencia:");
					lblNewLabel_9.setBounds(293, 186, 59, 14);
					PanelDatosSolicitud.add(lblNewLabel_9);
				}
				{
					JRadioButton radioButton = new JRadioButton("Si");
					radioButton.setBounds(6, 347, 52, 23);
					PanelDatosSolicitud.add(radioButton);
				}
				{
					JRadioButton radioButton = new JRadioButton("No");
					radioButton.setBounds(60, 347, 52, 23);
					PanelDatosSolicitud.add(radioButton);
				}
				{
					cbxCiudad = new JComboBox();
					ciudades.add(0, "<Seleccionar>");
					cbxCiudad.setModel(new DefaultComboBoxModel<>(ciudades.toArray(new String[0])));
					cbxCiudad.setBounds(66, 186, 147, 20);
					PanelDatosSolicitud.add(cbxCiudad);
				}
				{
					cbxContrato = new JComboBox();
					cbxContrato.setModel(new DefaultComboBoxModel(
							new String[] { "<Selecionar>", "Jornada Completa", "Media Jornada", "Jornada Mixta" }));
					cbxContrato.setBounds(66, 43, 147, 20);
					PanelDatosSolicitud.add(cbxContrato);
				}
				{
					rdbtnLicenciaSi = new JRadioButton("Si");
					rdbtnLicenciaSi.addMouseListener(new MouseAdapter()
					{
						@Override
						public void mouseClicked(MouseEvent e)
						{
							rdbtnLicenciaNo.setSelected(false);
						}
					});
					rdbtnLicenciaSi.setBounds(349, 182, 59, 23);
					PanelDatosSolicitud.add(rdbtnLicenciaSi);
				}
				{
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
					rdbtnLicenciaNo.setBounds(410, 182, 52, 23);
					PanelDatosSolicitud.add(rdbtnLicenciaNo);
				}
				{
					JLabel lblNewLabel_12 = new JLabel("Idioma:");
					lblNewLabel_12.setBounds(293, 43, 59, 14);
					PanelDatosSolicitud.add(lblNewLabel_12);
				}
				{
					cbxIdiomas = new JComboBox();
					idiomas.add(0, "<Seleccionar>");
					cbxIdiomas.setModel(new DefaultComboBoxModel<>(idiomas.toArray(new String[0])));
					cbxIdiomas.setBounds(367, 39, 120, 22);
					PanelDatosSolicitud.add(cbxIdiomas);
				}
			}
			{
				JPanel PanelTipoSolicitud = new JPanel();
				PanelTipoSolicitud.setBorder(
						new TitledBorder(null, "Tipo de Solicitud:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				PanelTipoSolicitud.setBounds(10, 483, 561, 72);
				panel.add(PanelTipoSolicitud);
				PanelTipoSolicitud.setLayout(null);
				//========
				//=======
				rdbtnUniversitario = new JRadioButton("Universitario");
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
						lblagnos.setVisible(true);
						spnAgnos.setVisible(true);
						cbxCarrera.setEnabled(true);
						cbxCarrera.setModel(new DefaultComboBoxModel<>(carreras.toArray(new String[0])));

					}
				});
				rdbtnUniversitario.setSelected(true);
				rdbtnUniversitario.setBounds(134, 27, 108, 23);
				PanelTipoSolicitud.add(rdbtnUniversitario);

				rdbtnTecnico = new JRadioButton("Tecnico");
				rdbtnTecnico.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						rdbtnUniversitario.setSelected(false);
						lblcarrera.setVisible(false);
						cbxCarrera.setVisible(false);
						lblarea.setVisible(true);
						cbxArea.setModel(new DefaultComboBoxModel<>(areas.toArray(new String[0])));
						cbxArea.setVisible(true);

					}
				});
				rdbtnTecnico.setBounds(340, 27, 81, 23);
				PanelTipoSolicitud.add(rdbtnTecnico);
			}

			JPanel PanelAptidutes = new JPanel();
			PanelAptidutes.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Aptitudes:",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			PanelAptidutes.setBounds(10, 566, 561, 186);
			panel.add(PanelAptidutes);
			PanelAptidutes.setLayout(null);

			lblcarrera = new JLabel("Carrera:");
			lblcarrera.setBounds(11, 88, 58, 14);
			PanelAptidutes.add(lblcarrera);
			{
				lblarea = new JLabel("Area:");
				lblarea.setBounds(11, 88, 46, 14);
				PanelAptidutes.add(lblarea);
			}

			cbxArea = new JComboBox();
			cbxArea.setVisible(false);
			areas.add(0, "<Seleccionar>");
			carreras.add(0, "<Seleccionar>");

			cbxArea.setBounds(68, 85, 208, 20);
			PanelAptidutes.add(cbxArea);
			{
				cbxCarrera = new JComboBox();
				cbxCarrera.setEnabled(false);
				cbxCarrera.setVisible(false);
				cbxCarrera.setBounds(67, 85, 208, 20);
				PanelAptidutes.add(cbxCarrera);
			}

			lblagnos = new JLabel("Años:");
			lblagnos.setBounds(329, 85, 46, 14);
			PanelAptidutes.add(lblagnos);

			spnAgnos = new JSpinner();
			spnAgnos.setModel(new SpinnerNumberModel(0, 0, 100, 1));
			spnAgnos.setBounds(385, 82, 110, 20);
			PanelAptidutes.add(spnAgnos);
			{

				ModelActividades = new DefaultListModel<String>();
			}

			JPanel PanelCantidad = new JPanel();
			PanelCantidad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Porcentaje Match:", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			PanelCantidad.setBounds(10, 759, 561, 72);
			panel.add(PanelCantidad);
			PanelCantidad.setLayout(null);

			JLabel lblNewLabel_16 = new JLabel("Porcentaje:");
			lblNewLabel_16.setBounds(200, 42, 78, 14);
			PanelCantidad.add(lblNewLabel_16);
			{
				spnPorcentaje = new JSpinner();
				spnPorcentaje.setModel(new SpinnerNumberModel(new Float(10), new Float(1), new Float(100), new Float(10)));
				spnPorcentaje.setBounds(275, 39, 110, 20);
				PanelCantidad.add(spnPorcentaje);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnSolicitar = new JButton("Solicitar");
				if (aux != null) // mod
					btnSolicitar.setText("Modificar");
				btnSolicitar.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e)
					{
						if (aux == null) // mod
						{
							if (validar())
							{
								Solicitud solicitudNew = null;
								if (!Bolsa.getInstance().existeEmpresa(txtRNC.getText()))
								{
									try
									{
										PreparedStatement queryEmp = conexion.prepareStatement(insertEmp);

										queryEmp.setString(1, txtRNC.getText());
										queryEmp.setString(2, txtNombre.getText());
										queryEmp.setString(3, txtTelefono.getText());
										queryEmp.setString(4, txtDireccion.getText());
										queryEmp.setString(5, cbxCiudad.getSelectedItem().toString().substring(0, cbxCiudad.getSelectedItem().toString().indexOf(" ")));
										int filasInsertadas = queryEmp.executeUpdate();
									}
									catch (SQLException e2)
									{
										System.err.println("Error al conectar con SQL Server: " + e2.getMessage());

									}
								}
								if (rdbtnMudarseSi.isSelected())
									mobilidadStr = "Si";

								if (rdbtnLicenciaSi.isSelected())
									licenciaStr = "Si";

								try
								{
									if (rdbtnUniversitario.isSelected())
									{
										PreparedStatement querySoliP = conexion.prepareStatement(insertSoli);

										querySoliP.setString(1, mobilidadStr);
										querySoliP.setString(2, cbxContrato.getSelectedItem().toString());
										querySoliP.setString(3, licenciaStr);
										querySoliP.setString(4, "Universitario");
										querySoliP.setFloat(5, Float.valueOf(spnSalario.getValue().toString()));
										querySoliP.setString(6, "Si");
										querySoliP.setString(7, txtRNC.getText());
										querySoliP.setString(8, cbxCarrera.getSelectedItem().toString().substring(0, cbxCarrera.getSelectedItem().toString().indexOf(" ")));
										querySoliP.setString(9, null);
										querySoliP.setString(10, cbxIdiomas.getSelectedItem().toString().substring(0, cbxIdiomas.getSelectedItem().toString().indexOf(" ")));
										querySoliP.setString(11, cbxCiudad.getSelectedItem().toString().substring(0, cbxCiudad.getSelectedItem().toString().indexOf(" ")));
										querySoliP.setShort(12, Short.valueOf(spnAgnos.getValue().toString()));
										querySoliP.setFloat(13, Float.valueOf(spnPorcentaje.getValue().toString()));

										int filasInsertadas = querySoliP.executeUpdate();
									}

									else if(rdbtnTecnico.isSelected()) {
										PreparedStatement querySoliP = conexion.prepareStatement(insertSoli);

										querySoliP.setString(1, mobilidadStr);
										querySoliP.setString(2, cbxContrato.getSelectedItem().toString());
										querySoliP.setString(3, licenciaStr);
										querySoliP.setString(4, "Tecnico");
										querySoliP.setFloat(5, Float.valueOf(spnSalario.getValue().toString()));
										querySoliP.setString(6, "Si");
										querySoliP.setString(7, txtRNC.getText());
										querySoliP.setString(8, null);
										querySoliP.setString(9, cbxArea.getSelectedItem().toString().substring(0, cbxArea.getSelectedItem().toString().indexOf(" ")));
										querySoliP.setString(10, cbxIdiomas.getSelectedItem().toString().substring(0, cbxIdiomas.getSelectedItem().toString().indexOf(" ")));
										querySoliP.setString(11, cbxCiudad.getSelectedItem().toString().substring(0, cbxCiudad.getSelectedItem().toString().indexOf(" ")));
										querySoliP.setShort(12, Short.valueOf(spnAgnos.getValue().toString()));
										querySoliP.setFloat(13, Float.valueOf(spnPorcentaje.getValue().toString()));

										int filasInsertadas = querySoliP.executeUpdate();
									}
								}
								catch (SQLException e2)
								{
									System.err.println("Error al crear Solicitud: " + e2.getMessage());

								}


								JOptionPane.showMessageDialog(null, "Solicitud Ingresada", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);
								clean();
							}
							else
								JOptionPane.showMessageDialog(null, "Solicitud Incompleta", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);
						}
						else //mod, todo el else
						{
							if (validar())
							{
								if (rdbtnMudarseSi.isSelected()) {
									mov = true;
									mobilidadStr = "Si";
									}else {
										mobilidadStr = "No";
									}
								if (rdbtnLicenciaSi.isSelected()) {
									lic = true;
									licenciaStr = "Si";
								}else {
									licenciaStr = "No";
								}
									
								Connection conn = null;
								PreparedStatement pstmt = null;
								 try {
							            // Establecer la conexión con la base de datos (reemplaza los valores adecuadamente)
							            conn = DriverManager.getConnection(Bolsa.getDbUrl(),Bolsa.getUsername(),Bolsa.getPassword());
							            pstmt = conn.prepareStatement(updateSoli);
							            int filasActualizadas = 0; 
	
							            
							            if(rdbtnUniversitario.isSelected()) {
											PreparedStatement querySoliP = conexion.prepareStatement(updateSoli);
											    pstmt.setInt(14,Integer.valueOf(aux.getCodigo()));
											 	pstmt.setString(1,mobilidadStr);
									            pstmt.setString(2, cbxContrato.getSelectedItem().toString());
									            pstmt.setString(3, licenciaStr);
									            pstmt.setString(4, "Universitario");
									            pstmt.setFloat(5,Float.valueOf(spnSalario.getValue().toString()));
									            pstmt.setString(6, "Si");
									            pstmt.setString(7, txtRNC.getText());
									            pstmt.setString(8, cbxCarrera.getSelectedItem().toString().substring(0, cbxCarrera.getSelectedItem().toString().indexOf(" ")));
									            pstmt.setString(9, null);
									            pstmt.setString(10, cbxIdiomas.getSelectedItem().toString().substring(0, cbxIdiomas.getSelectedItem().toString().indexOf(" ")));
									            pstmt.setString(11, cbxCiudad.getSelectedItem().toString().substring(0, cbxCiudad.getSelectedItem().toString().indexOf(" ")));
									            pstmt.setShort(12, Short.valueOf(spnAgnos.getValue().toString()));
									            pstmt.setFloat(13, Float.valueOf(spnPorcentaje.getValue().toString()));
									            filasActualizadas = pstmt.executeUpdate();
							            }
										else if(rdbtnTecnico.isSelected()) {
											PreparedStatement querySoliP = conexion.prepareStatement(updateSoli);
											
											pstmt.setInt(14,Integer.valueOf(aux.getCodigo()));
											pstmt.setString(1,mobilidadStr);
								            pstmt.setString(2, cbxContrato.getSelectedItem().toString());
								            pstmt.setString(3, licenciaStr);
								            pstmt.setString(4, "Tecnico");
								            pstmt.setFloat(5,Float.valueOf(spnSalario.getValue().toString()));
								            pstmt.setString(6, "Si");
								            pstmt.setString(7, txtRNC.getText());
								            pstmt.setString(8, null);
								            pstmt.setString(9, cbxArea.getSelectedItem().toString().substring(0, cbxArea.getSelectedItem().toString().indexOf(" ")));
								            pstmt.setString(10, cbxIdiomas.getSelectedItem().toString().substring(0, cbxIdiomas.getSelectedItem().toString().indexOf(" ")));
								            pstmt.setString(11, cbxCiudad.getSelectedItem().toString().substring(0, cbxCiudad.getSelectedItem().toString().indexOf(" ")));
								            pstmt.setShort(12, Short.valueOf(spnAgnos.getValue().toString()));
								            pstmt.setFloat(13, Float.valueOf(spnPorcentaje.getValue().toString()));
								            filasActualizadas = pstmt.executeUpdate();
											
										}
//"update Oferta_Empresa set Mobilidad = ?, Contrato = ?, Licencia = ? , Nivel_Educativo_Deseado = ?, Sueldo = ?, Activa = ?, 
//RNC = ? , id_carrera = ?, id_area = ?, id_idioma = ?, id_ciudad = ?, Agnos_Experiencia = ?, Porcentaje_Match = ? where Codigo = ?";
							            if (filasActualizadas > 0) {
							                System.out.println("La Oferta ha sido actualizada correctamente.");
							            } else {
							                System.out.println("No se encontró ninguna Oferta con el codigo . No se realizó ninguna actualización.");
							            }
							        } catch (SQLException e1) {
							            e1.printStackTrace();
							        } finally {
							            // Cerrar recursos (prepared statement y conexión)
							            try {
							                if (pstmt != null) pstmt.close();
							                if (conn != null) conn.close();
							            } catch (SQLException e1) {
							                e1.printStackTrace();
							            }
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

		//mod, todo el if
		if (aux != null)
		{
			txtRNC.setText(aux.getRnc());

			Empresa empresa = Bolsa.getInstance().buscarEmpresaByRNC(txtRNC.getText());
			if (empresa != null)
			{
				txtNombre.setText(empresa.getNombre());
				txtTelefono.setText(empresa.getTelefono());
				txtDireccion.setText(empresa.getDireccion());
			}
			txtRNC.setEditable(false);
		}
		loadSolicitud(aux); // mod
	}

	private void loadSolicitud(SoliEmpresa solicitud) //mod, la funcion completa
	{
		if ( solicitud != null)
		{
			Empresa empresa = Bolsa.getInstance().buscarEmpresaByRNC(solicitud.getRnc());
			txtRNC.setText(solicitud.getRnc());
			txtNombre.setText(empresa.getNombre());
			txtTelefono.setText(empresa.getTelefono());
			txtDireccion.setText(empresa.getDireccion());
			if (solicitud.getContrato().equalsIgnoreCase("Jornada Completa"))
				cbxContrato.setSelectedIndex(1);
			else if (solicitud.getContrato().equalsIgnoreCase("Media Jornada"))
				cbxContrato.setSelectedIndex(2);
			else if (solicitud.getContrato().equalsIgnoreCase("Jornada Mixta"))
				cbxContrato.setSelectedIndex(3);

			//txtCodigo.setText(solicitud.getCodigo());
			spnSalario.setValue(solicitud.getSueldo());
			//txtIdiomas.setText("");

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

			if (solicitud instanceof EmpUniversitario)
			{
				rdbtnUniversitario.setSelected(true);
				rdbtnTecnico.setSelected(false);
				rdbtnTecnico.setEnabled(false);
			}
			else if (solicitud instanceof EmpTecnico)
			{
				rdbtnUniversitario.setSelected(false);
				rdbtnUniversitario.setEnabled(false);
				rdbtnTecnico.setSelected(true);
			}

			if (rdbtnUniversitario.isSelected() || rdbtnTecnico.isSelected())
				//cbxArea.setSelectedIndex(0);

			cbxCiudad.setSelectedIndex(0);

			spnAgnos.setValue(new Integer("0"));
			if (solicitud instanceof EmpUniversitario)
				spnAgnos.setValue(((EmpUniversitario) solicitud).getAngos());
			else if (solicitud instanceof EmpTecnico)
				spnAgnos.setValue(((EmpTecnico) solicitud).getAngos());

			//txtIdiomas.setText(solicitud.getIdiomas());
			ModelActividades.removeAllElements();
			spnPorcentaje.setValue(solicitud.getPorcentajeMacth());
		}
	}

	private void clean()
	{
		txtRNC.setText("");
		txtNombre.setText("");
		txtTelefono.setText("");
		txtDireccion.setText("");
		txtNombre.setEditable(false);
		txtTelefono.setEditable(false);
		txtDireccion.setEditable(false);
		cbxContrato.setSelectedIndex(0);
		spnSalario.setValue(new Float("1000"));
		//txtIdiomas.setText("");
		rdbtnLicenciaNo.setSelected(true);
		rdbtnLicenciaSi.setSelected(false);
		rdbtnMudarseSi.setSelected(false);
		rdbtnMudarseNo.setSelected(true);
		rdbtnTecnico.setSelected(false);
		rdbtnUniversitario.setSelected(true);
		if (rdbtnUniversitario.isSelected() || rdbtnTecnico.isSelected())
			cbxArea.setSelectedIndex(0);
		if (rdbtnUniversitario.isSelected())
			cbxCarrera.setSelectedIndex(0);
		cbxCiudad.setSelectedIndex(0);
		spnAgnos.setValue(new Integer("0"));
		ModelActividades.removeAllElements();
		spnPorcentaje.setValue(new Float("10"));
	}

	private boolean validar()
	{

		boolean validado = false;

		if (rdbtnUniversitario.isSelected() && (((txtRNC.getText().length() > 1) && (txtNombre.getText().length() > 1)
				&& (txtTelefono.getText().length() > 1) && (txtDireccion.getText().length() > 1)
				&& (cbxContrato.getSelectedIndex() > 0) && (cbxIdiomas.getSelectedIndex() > 0) && (cbxCarrera.getSelectedIndex() > 0)
				&& (cbxCiudad.getSelectedIndex() > 0))))
			validado = true;
		else if (rdbtnTecnico.isSelected() && (((txtRNC.getText().length() > 1) && (txtNombre.getText().length() > 1)
				&& (txtTelefono.getText().length() > 1) && (txtDireccion.getText().length() > 1)
				&& (cbxContrato.getSelectedIndex() > 0) && (cbxIdiomas.getSelectedIndex() > 0) && (cbxArea.getSelectedIndex() > 0) && (cbxCiudad.getSelectedIndex() > 0))))
			validado = true;

		return validado;
	}
	private ArrayList<String> obtenerCiudadesDesdeBaseDeDatos() {
		ArrayList<String> ciudades = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = Bolsa.abrirConexion();

			stmt = conn.createStatement();


			String sql = "select id_ciudad, nombre_ciudad from Ciudad"; 
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
	private ArrayList<String> obteneridiomasDesdeBaseDeDatos() {
		ArrayList<String> carreras = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			// Obtener la conexión con la base de datos usando tu función para abrir la conexión
			conn = Bolsa.abrirConexion();

			stmt = conn.createStatement();

			// Ejecutar la consulta SQL para obtener las ciudades desde la tabla correspondiente
			String sql = "select id_idioma,nombre_idioma from Idioma"; // Reemplaza "tabla_ciudades" por el nombre de tu tabla
			rs = stmt.executeQuery(sql);

			// Recopilar los nombres de las ciudades en el ArrayList
			while (rs.next()) {
				String id = rs.getString("id_idioma");
				String nombre = rs.getString("nombre_idioma");
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
