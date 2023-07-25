package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	@SuppressWarnings("rawtypes")
	private JComboBox cbxTipoUsuario;
	private Usuario usuario = null;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public RegUsuario(Usuario aux)
	{
		usuario = aux;
		setTitle("Registrar Usuario");
		if (usuario != null)
			setTitle("Modificar Usuario: " + usuario.getUsername());
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
				lblNewLabel.setBounds(42, 37, 81, 14);
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

			cbxTipoUsuario = new JComboBox();
			cbxTipoUsuario.setModel(new DefaultComboBoxModel(new String[] { "Administrador", "Secretaria" }));
			cbxTipoUsuario.setBounds(43, 117, 109, 20);
			panel.add(cbxTipoUsuario);

			txtUsername = new JTextField();
			txtUsername.setBounds(42, 54, 110, 20);
			panel.add(txtUsername);
			txtUsername.setColumns(10);
			{
				JLabel lblNewLabel_3 = new JLabel("Tipo de Usuario:");
				lblNewLabel_3.setBounds(40, 98, 95, 14);
				panel.add(lblNewLabel_3);
			}

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
				if (usuario != null)
					btnRegistrar.setText("Modidicar");

				btnRegistrar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (usuario == null)
						{
							if (String.valueOf(pswPassword.getPassword()).equals(String.valueOf(pswConfirm.getPassword())))
							{
								if (!Bolsa.getInstance().existeUsuario(txtUsername.getText()))
								{
									Usuario newUser = new Usuario(txtUsername.getText(),
											String.valueOf(pswPassword.getPassword()),
											cbxTipoUsuario.getSelectedItem().toString());
									Bolsa.getInstance().addUsuario(newUser);
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
						else if (usuario != null)
						{
							if (String.valueOf(pswPassword.getPassword()).equals(String.valueOf(pswConfirm.getPassword())))
							{
								if (!Bolsa.getInstance().existeUsuario(txtUsername.getText()))
								{
									usuario.setUsername(txtUsername.getText());
									usuario.setPassword(String.valueOf(pswPassword.getPassword()));
									usuario.setTipo(cbxTipoUsuario.getSelectedItem().toString());

									JOptionPane.showMessageDialog(null, "Usuario modificado con exito", "Informacion",
											JOptionPane.INFORMATION_MESSAGE);
									dispose();
								}
								else
									JOptionPane.showMessageDialog(null, "Ese Usuario no esta disponible", "Informacion",
											JOptionPane.INFORMATION_MESSAGE);
							}
						}
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
		if (usuario != null && usuario.isSecret())
		{
			cbxTipoUsuario.setEditable(false);
		}
		loadUsuario();
	}

	private void clean()
	{
		txtUsername.setText("");
		cbxTipoUsuario.setSelectedIndex(0);
		pswPassword.setText("");
		pswConfirm.setText("");
	}

	private void loadUsuario()
	{
		if (usuario != null)
			txtUsername.setText(usuario.getUsername());

	}
}
