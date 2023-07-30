package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Bolsa;
import logico.Persona;
import logico.SoliEmpresa;
import logico.SoliPersona;

@SuppressWarnings("serial")
public class MostrarMatch extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private Persona persona = null;
	private float porcentaje = 0;
	private JTextField txtNombre;
	private JTextField txtPorcentaje;
	Connection conexion = Bolsa.abrirConexion();
	private String insertContrato = "insert into Empresa_Contrata (RNC, Cedula) values(?,?)";

	public MostrarMatch(SoliPersona solicitudPersona, SoliEmpresa solicitudEmpresa)
	{
		persona = Bolsa.getInstance().buscarPersonaByCedula(solicitudPersona.getCedula());
		porcentaje = Bolsa.getInstance().match(solicitudEmpresa, solicitudPersona);

		if (porcentaje > 100) //solo ocurre con los idiomas repetidos
			porcentaje = 100;

		setTitle("Contratar a: " + persona.getNombre());
		setBounds(100, 100, 500, 439);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			txtNombre = new JTextField();
			txtNombre.setEditable(false);
			txtNombre.setBounds(158, 41, 159, 20);
			panel.add(txtNombre);
			txtNombre.setColumns(10);
			txtNombre.setText(persona.getNombre());

			JLabel lblNewLabel = new JLabel("El empleado:");
			lblNewLabel.setBounds(200, 22, 93, 14);
			panel.add(lblNewLabel);
			{
				JLabel lblNewLabel_1 = new JLabel("    Tiene un:");
				lblNewLabel_1.setBounds(200, 80, 93, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_2 = new JLabel("");
				if (porcentaje == 100)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/100.png")));
				else if (porcentaje >= 90)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/90.png")));
				else if (porcentaje >= 80)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/80.png")));
				else if (porcentaje >= 70)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/70.png")));
				else if (porcentaje >= 60)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/60.png")));
				else if (porcentaje >= 50)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/50.png")));
				else if (porcentaje >= 40)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/40.png")));
				else if (porcentaje >= 30)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/30.png")));
				else if (porcentaje >= 20)
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/20.png")));
				else
					lblNewLabel_2.setIcon(new ImageIcon(MostrarMatch.class.getResource("/images/10.png")));

				lblNewLabel_2.setBounds(133, 138, 210, 210);
				panel.add(lblNewLabel_2);
			}
			{
				txtPorcentaje = new JTextField();
				txtPorcentaje.setEditable(false);
				txtPorcentaje.setBounds(156, 101, 159, 20);
				panel.add(txtPorcentaje);
				txtPorcentaje.setColumns(10);
				txtPorcentaje.setText(String.valueOf(porcentaje) + "% de los requisitos");
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnContratar = new JButton("Contratar");
				btnContratar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						try
						{
							PreparedStatement queryCont = conexion.prepareStatement(insertContrato);
							
							queryCont.setString(1, solicitudEmpresa.getRnc());
							queryCont.setString(2, solicitudPersona.getCedula());
							
							@SuppressWarnings("unused")
							int filasInsertadas = queryCont.executeUpdate();
							
							CallableStatement procCont = conexion.prepareCall("{call Contratar_Persona(?)}");
							procCont.setString(1, solicitudPersona.getCedula());
							procCont.execute();
							
							CallableStatement procSol = conexion.prepareCall("{call Desactivar_Solicitud_Persona(?)}");
							procSol.setString(1, solicitudPersona.getCodigo());
							procSol.execute();
							
							CallableStatement procOf = conexion.prepareCall("{call Desactivar_Oferta_Empresa(?)}");
							procOf.setString(1, solicitudEmpresa.getCodigo());
							procOf.execute();
							
						}
						catch (SQLException e2)
						{
							e2.printStackTrace();
						}
						
						//Bolsa.getInstance().contrarPersona(persona.getId());
						//Bolsa.getInstance().desactivarSoliPersona(persona.getId());
						JOptionPane.showMessageDialog(null, "Persona contratada exitosamente.", "Informacion",
								JOptionPane.INFORMATION_MESSAGE);
						dispose();

					}
				});
				btnContratar.setActionCommand("OK");
				buttonPane.add(btnContratar);
				getRootPane().setDefaultButton(btnContratar);
			}
			{
				JButton btnSalir = new JButton("Salir");
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
	}
}
