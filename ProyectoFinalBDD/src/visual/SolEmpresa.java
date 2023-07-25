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

@SuppressWarnings("serial")
public class SolEmpresa extends JDialog
{
	private final JPanel contentPanel = new JPanel();
	private JTextField txtRNC;
	private JTextField txtNombre;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private JTextField txtCodigo;
	private JTextField txtIdiomas;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxArea;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxCarrera;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxCiudad;
	@SuppressWarnings("rawtypes")
	private JComboBox cbxTipoSalario;
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
	private JSpinner spnCantidad;
	private boolean mov = false;
	private boolean lic = false;
	private ArrayList<String> idiomasAux;
	private DefaultListModel<String> ModelActividades;
	private JButton btnAgregarIdioma;
	private SoliEmpresa solicitud = null; // mod

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SolEmpresa(SoliEmpresa aux)
	{
		solicitud = aux;
		idiomasAux = new ArrayList<String>();
		setResizable(false);
		setTitle("Registrar Solicitud de Empresa");
		if (solicitud != null) // mod
			setTitle("Modificar Solicitud de " + solicitud.getRnc()); // mod
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
						Empresa aux = Bolsa.getInstance().buscarEmpresaByRNC(txtRNC.getText());
						if (aux != null)
						{
							txtNombre.setText(aux.getNombre());
							txtTelefono.setText(aux.getTelefono());
							txtDireccion.setText(aux.getDireccion());
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
					lblNewLabel_4.setBounds(10, 39, 59, 14);
					PanelDatosSolicitud.add(lblNewLabel_4);
				}
				{
					JLabel lblNewLabel_5 = new JLabel("Salario:");
					lblNewLabel_5.setBounds(10, 92, 46, 14);
					PanelDatosSolicitud.add(lblNewLabel_5);
				}

				spnSalario = new JSpinner();
				spnSalario.setModel(new SpinnerNumberModel(new Float(1000), new Float(0), null, new Float(500)));
				spnSalario.setBounds(66, 89, 93, 20);
				PanelDatosSolicitud.add(spnSalario);
				{
					JLabel lblNewLabel_6 = new JLabel("Tipo de Salario:");
					lblNewLabel_6.setBounds(286, 92, 127, 14);
					PanelDatosSolicitud.add(lblNewLabel_6);
				}
				{
					JLabel lblNewLabel_7 = new JLabel("Ciudad:");
					lblNewLabel_7.setBounds(10, 145, 46, 14);
					PanelDatosSolicitud.add(lblNewLabel_7);
				}
				{
					JLabel lblNewLabel_8 = new JLabel("Puede Mudarse:");
					lblNewLabel_8.setBounds(286, 198, 93, 14);
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
					rdbtnMudarseSi.setBounds(379, 194, 59, 23);
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
					rdbtnMudarseNo.setBounds(440, 194, 52, 23);
					PanelDatosSolicitud.add(rdbtnMudarseNo);
				}
				{
					JLabel lblNewLabel_9 = new JLabel("Licencia:");
					lblNewLabel_9.setBounds(10, 198, 59, 14);
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
					cbxCiudad.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Azua", "Bahoruco",
							"Barahona", "Dajabon", "Distrito Nacional", "Duarte", "El Seybo", "Elias Piña", "Espaillat",
							"Hato Mayor", "Hermanas Mirabal", "Independencia", "La Altagracia", "La Romana", "La Vega",
							"Maria Trinidad Sanchez", "Monseñor Nouel", "Monte Plata", "Montecristi", "Pedernales", "Peravia",
							"Puerto Plata", "Samana", "San Cristobal", "San Jose De Ocoa", "San Juan", "San Pedro De Macoris",
							"Sanchez Ramirez", "Santiago", "Santiago Rodriguez", "Santo Domingo", "Valverde" }));
					cbxCiudad.setBounds(66, 142, 147, 20);
					PanelDatosSolicitud.add(cbxCiudad);
				}
				{
					cbxContrato = new JComboBox();
					cbxContrato.setModel(new DefaultComboBoxModel(
							new String[] { "<Selecionar>", "Jornada Completa", "Media Jornada", "Jornada Mixta" }));
					cbxContrato.setBounds(66, 36, 147, 20);
					PanelDatosSolicitud.add(cbxContrato);
				}
				{
					cbxTipoSalario = new JComboBox();
					cbxTipoSalario.setModel(new DefaultComboBoxModel(
							new String[] { "<Selecionar>", "Quincenal", "Mensual", "Semanal", "Diario" }));
					cbxTipoSalario.setBounds(379, 89, 123, 20);
					PanelDatosSolicitud.add(cbxTipoSalario);
				}
				{
					JLabel lblNewLabel_10 = new JLabel("Codigo:");
					lblNewLabel_10.setBounds(286, 39, 46, 14);
					PanelDatosSolicitud.add(lblNewLabel_10);
				}
				{
					txtCodigo = new JTextField();
					txtCodigo.setEditable(false);
					txtCodigo.setBounds(379, 36, 123, 20);
					txtCodigo.setText("SOL-" + Bolsa.getInstance().getGenSol());
					PanelDatosSolicitud.add(txtCodigo);
					txtCodigo.setColumns(10);
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
					rdbtnLicenciaSi.setBounds(66, 194, 59, 23);
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
					rdbtnLicenciaNo.setBounds(127, 194, 52, 23);
					PanelDatosSolicitud.add(rdbtnLicenciaNo);
				}
				{
					JLabel lblNewLabel_12 = new JLabel("Idiomas:");
					lblNewLabel_12.setBounds(286, 145, 59, 14);
					PanelDatosSolicitud.add(lblNewLabel_12);
				}
				{
					txtIdiomas = new JTextField();
					txtIdiomas.addKeyListener(new KeyAdapter()
					{
						@Override
						public void keyTyped(KeyEvent e)
						{
							char c = e.getKeyChar();
							if (!Character.isAlphabetic(c) && (c != KeyEvent.VK_BACK_SPACE) && (c != KeyEvent.VK_SPACE))
								e.consume();

							if (txtIdiomas.getText().length() > 1)
								btnAgregarIdioma.setEnabled(true);
						}
					});
					txtIdiomas.setBounds(342, 142, 86, 20);
					PanelDatosSolicitud.add(txtIdiomas);
					txtIdiomas.setColumns(10);
				}

				btnAgregarIdioma = new JButton("Agregar");
				btnAgregarIdioma.setEnabled(false);
				btnAgregarIdioma.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						idiomasAux.add(txtIdiomas.getText());
						txtIdiomas.setText("");
						btnAgregarIdioma.setEnabled(false);
					}
				});
				btnAgregarIdioma.setBounds(449, 141, 86, 23);
				PanelDatosSolicitud.add(btnAgregarIdioma);
			}
			{
				JPanel PanelTipoSolicitud = new JPanel();
				PanelTipoSolicitud.setBorder(
						new TitledBorder(null, "Tipo de Solicitud:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				PanelTipoSolicitud.setBounds(10, 483, 561, 72);
				panel.add(PanelTipoSolicitud);
				PanelTipoSolicitud.setLayout(null);

				rdbtnUniversitario = new JRadioButton("Universatario");
				rdbtnUniversitario.addMouseListener(new MouseAdapter()
				{
					@Override
					public void mouseClicked(MouseEvent e)
					{
						rdbtnTecnico.setSelected(false);
						lblarea.setVisible(true);
						cbxArea.setVisible(true);
						lblcarrera.setVisible(true);
						cbxCarrera.setVisible(true);
						lblagnos.setVisible(true);
						spnAgnos.setVisible(true);
						cbxArea.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Ciencias de la Salud",
								"Ciencias e Ingenieria", "Ciencias Administrativas", "Ciencias Humanidades y Artes" }));
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
						cbxArea.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Amd. de Peq. Empresas",
								"Artes Culinarias", "Automatizacion", "Diseño Grafico", "Enfermeria", "Gestion Social",
								"Mercadeo", "Microfinanzas", "Publicidad y Medios Digistales", "Redes de Datos", }));
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
			lblcarrera.setBounds(11, 118, 58, 14);
			PanelAptidutes.add(lblcarrera);
			{
				lblarea = new JLabel("Area:");
				lblarea.setBounds(10, 52, 46, 14);
				PanelAptidutes.add(lblarea);
			}

			cbxArea = new JComboBox();
			cbxArea.addItemListener(new ItemListener()
			{
				public void itemStateChanged(ItemEvent e)
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
								new String[] { "<Selecionar>", "Direccion Empresarial", "Administracion Hotelera", "Economia",
										"Gesttion Financiera y Adutoria", "Marketing", "Hospitalida y Turismo" }));

					else if (cbxArea.getSelectedItem().toString().equalsIgnoreCase("Ciencias Humanidades y Artes"))
						cbxCarrera.setModel(
								new DefaultComboBoxModel(new String[] { "<Selecionar>", "Arquitectura", "Comunicacion Social",
										"Derecho", "Diseño e Interiorismo", "Educacion", "Filosofia", "Trabajo Social" }));

					else
						cbxCarrera.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>" }));

				}
			});
			cbxArea.setModel(new DefaultComboBoxModel(new String[] { "<Selecionar>", "Ciencias de la Salud",
					"Ciencias e Ingenieria", "Ciencias Administrativas", "Ciencias Humanidades y Artes" }));
			cbxArea.setBounds(67, 49, 208, 20);
			PanelAptidutes.add(cbxArea);
			{
				cbxCarrera = new JComboBox();
				cbxCarrera.setEnabled(false);
				cbxCarrera.setBounds(67, 115, 208, 20);
				PanelAptidutes.add(cbxCarrera);
			}

			lblagnos = new JLabel("Años:");
			lblagnos.setBounds(328, 52, 46, 14);
			PanelAptidutes.add(lblagnos);

			spnAgnos = new JSpinner();
			spnAgnos.setModel(new SpinnerNumberModel(0, 0, 100, 1));
			spnAgnos.setBounds(384, 49, 110, 20);
			PanelAptidutes.add(spnAgnos);
			{

				ModelActividades = new DefaultListModel<String>();
			}

			JPanel PanelCantidad = new JPanel();
			PanelCantidad.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Cantidades:",
					TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			PanelCantidad.setBounds(10, 759, 561, 72);
			panel.add(PanelCantidad);
			PanelCantidad.setLayout(null);

			JLabel lblNewLabel_15 = new JLabel("Cantidad:");
			lblNewLabel_15.setBounds(10, 35, 64, 14);
			PanelCantidad.add(lblNewLabel_15);

			spnCantidad = new JSpinner();
			spnCantidad.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spnCantidad.setBounds(78, 32, 110, 20);
			PanelCantidad.add(spnCantidad);

			JLabel lblNewLabel_16 = new JLabel("Porcentaje:");
			lblNewLabel_16.setBounds(324, 35, 78, 14);
			PanelCantidad.add(lblNewLabel_16);
			{
				spnPorcentaje = new JSpinner();
				spnPorcentaje.setModel(new SpinnerNumberModel(new Float(10), new Float(1), new Float(100), new Float(10)));
				spnPorcentaje.setBounds(399, 32, 110, 20);
				PanelCantidad.add(spnPorcentaje);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnSolicitar = new JButton("Solicitar");
				if (solicitud != null) // mod
					btnSolicitar.setText("Modificar");
				btnSolicitar.addActionListener(new ActionListener()
				{

					public void actionPerformed(ActionEvent e)
					{
						if (solicitud == null) // mod
						{
							if (validar())
							{
								Solicitud solicitudNew = null;
								Empresa empresa = Bolsa.getInstance().buscarEmpresaByRNC(txtRNC.getText());
								if (empresa == null)
								{
									empresa = new Empresa(txtRNC.getText(), txtNombre.getText(), txtTelefono.getText(),
											txtDireccion.getText());
									Bolsa.getInstance().addEmpresa(empresa);
								}
								if (rdbtnMudarseSi.isSelected())
									mov = true;

								if (rdbtnLicenciaSi.isSelected())
									lic = true;

								if (rdbtnUniversitario.isSelected())
								{
									solicitudNew = new EmpUniversitario(txtCodigo.getText(), mov,
											cbxContrato.getSelectedItem().toString(), lic, cbxCiudad.getSelectedItem().toString(),
											txtRNC.getText(), Float.valueOf(spnPorcentaje.getValue().toString()),
											cbxTipoSalario.getSelectedItem().toString(),
											Float.valueOf(spnSalario.getValue().toString()), idiomasAux,
											Integer.valueOf(spnCantidad.getValue().toString()),
											cbxCarrera.getSelectedItem().toString(),
											Integer.valueOf(spnAgnos.getValue().toString()));
								}
								else if (rdbtnTecnico.isSelected())
								{
									solicitudNew = new EmpTecnico(txtCodigo.getText(), mov,
											cbxContrato.getSelectedItem().toString(), lic, cbxCiudad.getSelectedItem().toString(),
											txtRNC.getText(), Float.valueOf(spnPorcentaje.getValue().toString()),
											cbxTipoSalario.getSelectedItem().toString(),
											Float.valueOf(spnSalario.getValue().toString()), idiomasAux,
											Integer.valueOf(spnCantidad.getValue().toString()),
											cbxArea.getSelectedItem().toString(), Integer.valueOf(spnAgnos.getValue().toString()));
								}
						
								Bolsa.getInstance().addSolicitud(solicitudNew);
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
								if (rdbtnMudarseSi.isSelected())
									mov = true;

								if (rdbtnLicenciaSi.isSelected())
									lic = true;

								solicitud.setContrato(cbxContrato.getSelectedItem().toString());
								solicitud.setSueldo(Float.valueOf(spnSalario.getValue().toString()));
								solicitud.setTipoSalario(cbxTipoSalario.getSelectedItem().toString());
								solicitud.setCuidad(cbxCiudad.getSelectedItem().toString());
								solicitud.setIdiomas(idiomasAux);
								solicitud.setLicencia(lic);
								solicitud.setCantidad(Integer.valueOf(spnCantidad.getValue().toString()));
								solicitud.setPorcentajeMacth(Float.valueOf(spnPorcentaje.getValue().toString()));
								if (solicitud instanceof EmpUniversitario)
								{
									((EmpUniversitario) solicitud).setCarrera(cbxCarrera.getSelectedItem().toString());
									((EmpUniversitario) solicitud).setAgnos(Integer.valueOf(spnAgnos.getValue().toString()));

								}
								else if (solicitud instanceof EmpTecnico)
								{
									((EmpTecnico) solicitud).setArea(cbxArea.getSelectedItem().toString());
									((EmpTecnico) solicitud).setAgnos(Integer.valueOf(spnAgnos.getValue().toString()));
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
		if (solicitud != null)
		{
			txtRNC.setText(solicitud.getRnc());

			Empresa empresa = Bolsa.getInstance().buscarEmpresaByRNC(txtRNC.getText());
			if (empresa != null)
			{
				txtNombre.setText(empresa.getNombre());
				txtTelefono.setText(empresa.getTelefono());
				txtDireccion.setText(empresa.getDireccion());
			}
			txtRNC.setEditable(false);
		}
		loadSolicitud(); // mod
	}

	private void loadSolicitud() //mod, la funcion completa
	{
		if (solicitud != null)
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

			txtCodigo.setText(solicitud.getCodigo());
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
			
			btnAgregarIdioma.setEnabled(false);

			if (rdbtnUniversitario.isSelected() || rdbtnTecnico.isSelected())
				cbxArea.setSelectedIndex(0);

			cbxCiudad.setSelectedIndex(0);

			if (solicitud.getTipoSalario().equalsIgnoreCase("Quincenal"))
				cbxTipoSalario.setSelectedIndex(1);
			else if (solicitud.getTipoSalario().equalsIgnoreCase("Mensual"))
				cbxTipoSalario.setSelectedIndex(2);
			else if (solicitud.getTipoSalario().equalsIgnoreCase("Semanal"))
				cbxTipoSalario.setSelectedIndex(3);
			else if (solicitud.getTipoSalario().equalsIgnoreCase("Diario"))
				cbxTipoSalario.setSelectedIndex(4);

			spnAgnos.setValue(new Integer("0"));
			if (solicitud instanceof EmpUniversitario)
				spnAgnos.setValue(((EmpUniversitario) solicitud).getAgnos());
			else if (solicitud instanceof EmpTecnico)
				spnAgnos.setValue(((EmpTecnico) solicitud).getAgnos());

			idiomasAux = solicitud.getIdiomas();
			ModelActividades.removeAllElements();
			spnPorcentaje.setValue(solicitud.getPorcentajeMacth());
			spnCantidad.setValue(solicitud.getCantidad());
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
		txtCodigo.setText("SOL-" + Bolsa.getInstance().getGenSol());
		spnSalario.setValue(new Float("1000"));
		txtIdiomas.setText("");
		rdbtnLicenciaNo.setSelected(true);
		rdbtnLicenciaSi.setSelected(false);
		rdbtnMudarseSi.setSelected(false);
		rdbtnMudarseNo.setSelected(true);
		rdbtnTecnico.setSelected(false);
		btnAgregarIdioma.setEnabled(false);
		rdbtnUniversitario.setSelected(true);
		if (rdbtnUniversitario.isSelected() || rdbtnTecnico.isSelected())
			cbxArea.setSelectedIndex(0);
		if (rdbtnUniversitario.isSelected())
			cbxCarrera.setSelectedIndex(0);
		cbxCiudad.setSelectedIndex(0);
		cbxTipoSalario.setSelectedIndex(0);
		spnAgnos.setValue(new Integer("0"));
		idiomasAux = new ArrayList<String>();
		ModelActividades.removeAllElements();
		spnPorcentaje.setValue(new Float("10"));
		spnCantidad.setValue(new Float("1"));
	}

	private boolean validar()
	{

		boolean validado = false;

		if (rdbtnUniversitario.isSelected() && (((txtRNC.getText().length() > 1) && (txtNombre.getText().length() > 1)
				&& (txtTelefono.getText().length() > 1) && (txtDireccion.getText().length() > 1)
				&& (cbxContrato.getSelectedIndex() > 0) && (cbxTipoSalario.getSelectedIndex() > 0)
				&& (idiomasAux.size() > 0) && (cbxArea.getSelectedIndex() > 0) && (cbxCarrera.getSelectedIndex() > 0)
				&& (cbxCiudad.getSelectedIndex() > 0))))
			validado = true;
		else if (rdbtnTecnico.isSelected() && (((txtRNC.getText().length() > 1) && (txtNombre.getText().length() > 1)
				&& (txtTelefono.getText().length() > 1) && (txtDireccion.getText().length() > 1)
				&& (cbxContrato.getSelectedIndex() > 0) && (cbxTipoSalario.getSelectedIndex() > 0)
				&& (idiomasAux.size() > 0) && (cbxArea.getSelectedIndex() > 0) && (cbxCiudad.getSelectedIndex() > 0))))
			validado = true;

		return validado;
	}

}
