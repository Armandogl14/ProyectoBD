package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Bolsa;
import logico.Usuario;

@SuppressWarnings("serial")
public class BuscarUsuario extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtUsername;

	public BuscarUsuario()
	{
		setResizable(false);
		setTitle("Buscar Usuario");
		setBounds(100, 100, 353, 218);
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

			JLabel lblNewLabel = new JLabel("Username:");
			lblNewLabel.setBounds(10, 47, 74, 14);
			panel.add(lblNewLabel);

			txtUsername = new JTextField();
			txtUsername.setBounds(10, 72, 277, 20);
			panel.add(txtUsername);
			txtUsername.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnBuscar = new JButton("Buscar");
				btnBuscar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						Usuario usuario = Bolsa.getInstance().buscarUsuarioByUsername(txtUsername.getText());
						if (usuario != null)
						{
							RegUsuario reg = new RegUsuario(usuario);
							reg.setModal(true);
							reg.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(null, "El usuario no existe", "Informacion",
									JOptionPane.INFORMATION_MESSAGE);
					}
				});
				btnBuscar.setActionCommand("OK");
				buttonPane.add(btnBuscar);
				getRootPane().setDefaultButton(btnBuscar);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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
}
