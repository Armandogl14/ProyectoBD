package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import logico.Bolsa;

@SuppressWarnings("serial")
public class login extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;
	private JPasswordField pswPassword;

	public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		try
		{
			login dialog = new login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public login()
	{
		setTitle("Iniciar Sesion");
		setBounds(100, 100, 495, 346);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				pswPassword = new JPasswordField();
				pswPassword.setBounds(40, 163, 148, 20);
				panel.add(pswPassword);
			}

			txtUsername = new JTextField();
			txtUsername.setBounds(40, 94, 148, 20);
			panel.add(txtUsername);
			txtUsername.setColumns(10);
			{
				JLabel lblNewLabel_2 = new JLabel("Password:");
				lblNewLabel_2.setBounds(40, 138, 70, 14);
				panel.add(lblNewLabel_2);
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Username:");
				lblNewLabel_1.setBounds(40, 78, 70, 14);
				panel.add(lblNewLabel_1);
			}

			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(login.class.getResource("/images/Login.png")));
			lblNewLabel.setBounds(0, 0, 485, 280);
			panel.add(lblNewLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(new Color(222, 184, 135));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnIngresar = new JButton("Ingresar");
				btnIngresar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if (Bolsa.getInstance().confirmarLogin(txtUsername.getText(),
								String.valueOf(pswPassword.getPassword())))
						{
							Principal principal = new Principal(Bolsa.getLoginUser().isAdmin());
							dispose();
							principal.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(null, "Parametros Incorrectos", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);

					}
				});
				btnIngresar.setActionCommand("OK");
				buttonPane.add(btnIngresar);
				getRootPane().setDefaultButton(btnIngresar);
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
