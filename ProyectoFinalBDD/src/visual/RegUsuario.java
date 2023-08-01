package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Bolsa;
import logico.Usuario;

@SuppressWarnings("serial")
public class RegUsuario extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;
	private JPasswordField pswPassword;
	private JPasswordField pswConfirm;
	private Connection  conexion = Bolsa.abrirConexion();
	private String insertUser = "Insert into Usuario (username, contrasenia) values (?, ?)";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RegUsuario()
	{
		setTitle("Registrar Usuario");
		setBounds(100, 100, 441, 246);
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
			{
				JLabel lblNewLabel = new JLabel("Username:");
				lblNewLabel.setBounds(57, 64, 81, 14);
				panel.add(lblNewLabel);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Password:");
				lblNewLabel_1.setBounds(218, 37, 81, 14);
				panel.add(lblNewLabel_1);
			}
			{
				JLabel lblNewLabel_2 = new JLabel("Confirm Password:");
				lblNewLabel_2.setBounds(218, 98, 110, 14);
				panel.add(lblNewLabel_2);
			}

			txtUsername = new JTextField();
			txtUsername.setBounds(57, 81, 110, 20);
			panel.add(txtUsername);
			txtUsername.setColumns(10);

			pswPassword = new JPasswordField();
			pswPassword.setBounds(218, 54, 161, 20);
			panel.add(pswPassword);

			pswConfirm = new JPasswordField();
			pswConfirm.setBounds(218, 117, 161, 20);
			panel.add(pswConfirm);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");

				btnRegistrar.addActionListener(new ActionListener()
				{
					@SuppressWarnings("deprecation")
					public void actionPerformed(ActionEvent e)
					{	
						if (String.valueOf(pswPassword.getPassword()).equals(String.valueOf(pswConfirm.getPassword())))
						{
							if (!Bolsa.getInstance().existeUsuario(txtUsername.getText()))
							{
								try (Connection connection1 = DriverManager.getConnection(Bolsa.getDbUrl(), Bolsa.getUsername(), Bolsa.getPassword());
										PreparedStatement queryUser = conexion.prepareStatement(insertUser);){
	
									queryUser.setString(1, txtUsername.getText());
									queryUser.setString(2, String.valueOf(pswPassword.getPassword()));
									int filasInsertadas = queryUser.executeUpdate();
								} catch (SQLException e1){
									System.err.println("Error.");
								}
								clean();
								JOptionPane.showMessageDialog(null, "Usuario Ingresado", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);
							}
							else
								JOptionPane.showMessageDialog(null, "Ese Usuario no esta disponible", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);

						}
						else
							JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);	
					}
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void clean()
	{
		txtUsername.setText("");
		pswPassword.setText("");
		pswConfirm.setText("");
	}
}
