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
import logico.Empresa;

@SuppressWarnings("serial")
public class ModEmpresa extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private Empresa empresa = null;
	private JTextField txtRNC;
	private JTextField txtTelefono;
	private JTextField txtNombre;
	private JTextField txtDireccion;

	public ModEmpresa(Empresa aux)
	{
		empresa = aux;
		setTitle("Modificar Empresa: " + empresa.getNombre());
		setBounds(100, 100, 432, 310);
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

			JLabel lblNewLabel = new JLabel("RNC:");
			lblNewLabel.setBounds(10, 27, 46, 14);
			panel.add(lblNewLabel);

			txtRNC = new JTextField();
			txtRNC.setBounds(80, 24, 151, 20);
			panel.add(txtRNC);
			txtRNC.setColumns(10);
			txtRNC.setText(empresa.getRnc());

			JLabel lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setBounds(10, 78, 74, 14);
			panel.add(lblNewLabel_1);

			txtNombre = new JTextField();
			txtNombre.setBounds(80, 75, 151, 20);
			panel.add(txtNombre);
			txtNombre.setColumns(10);
			txtNombre.setText(empresa.getNombre());

			JLabel lblNewLabel_2 = new JLabel("Telefono:");
			lblNewLabel_2.setBounds(10, 133, 74, 14);
			panel.add(lblNewLabel_2);

			txtTelefono = new JTextField();
			txtTelefono.setBounds(80, 130, 151, 20);
			panel.add(txtTelefono);
			txtTelefono.setColumns(10);
			txtTelefono.setText(empresa.getTelefono());

			JLabel lblNewLabel_3 = new JLabel("Direccion:");
			lblNewLabel_3.setBounds(10, 192, 74, 14);
			panel.add(lblNewLabel_3);

			txtDireccion = new JTextField();
			txtDireccion.setBounds(80, 189, 227, 20);
			panel.add(txtDireccion);
			txtDireccion.setColumns(10);
			txtDireccion.setText(empresa.getDireccion());
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnModificar = new JButton("Modifcar");
				btnModificar.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String antiguo = empresa.getRnc();
						empresa.setRnc(txtRNC.getText());
						empresa.setNombre(txtNombre.getText());
						empresa.setTelefono(txtTelefono.getText());
						empresa.setDireccion(txtDireccion.getText());
						JOptionPane.showMessageDialog(null, "Empresa Modificada", "Informacion",
								JOptionPane.INFORMATION_MESSAGE);
						ListEmpresa.loadEmpresas();
						Bolsa.getInstance().cambiarRNC(antiguo, txtRNC.getText());
					}
				});
				btnModificar.setActionCommand("OK");
				buttonPane.add(btnModificar);
				getRootPane().setDefaultButton(btnModificar);
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
