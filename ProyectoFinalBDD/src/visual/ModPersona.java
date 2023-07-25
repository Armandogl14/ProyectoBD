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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Bolsa;
import logico.Persona;

@SuppressWarnings("serial")
public class ModPersona extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private Persona person = null;
	private JButton btnMod;
	private JButton btnCancelar;
	private JTextField txtCedula;
	private JTextField txtNom;
	private JTextField txtTelf;
	private JTextField txtDir;
	private JRadioButton rdbtnSi;
	private JRadioButton rdbtnNo;

	public ModPersona(Persona aux)
	{
		person = aux;
		setTitle("Modificar persona: " + person.getNombre());
		setBounds(100, 100, 432, 352);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JLabel lblNewLabel = new JLabel("Cedula:");
			lblNewLabel.setBounds(10, 27, 46, 14);
			panel.add(lblNewLabel);

			txtCedula = new JTextField();
			txtCedula.setBounds(80, 24, 151, 20);
			panel.add(txtCedula);
			txtCedula.setColumns(10);
			txtCedula.setText(person.getId());

			JLabel lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setBounds(10, 78, 74, 14);
			panel.add(lblNewLabel_1);

			txtNom = new JTextField();
			txtNom.setBounds(80, 75, 151, 20);
			panel.add(txtNom);
			txtNom.setColumns(10);
			txtNom.setText(person.getNombre());

			JLabel lblNewLabel_2 = new JLabel("Telefono:");
			lblNewLabel_2.setBounds(10, 133, 74, 14);
			panel.add(lblNewLabel_2);

			txtTelf = new JTextField();
			txtTelf.setBounds(80, 130, 151, 20);
			panel.add(txtTelf);
			txtTelf.setColumns(10);
			txtTelf.setText(person.getTelefono());

			JLabel lblNewLabel_3 = new JLabel("Direccion:");
			lblNewLabel_3.setBounds(10, 192, 74, 14);
			panel.add(lblNewLabel_3);

			txtDir = new JTextField();
			txtDir.setBounds(80, 189, 227, 20);
			panel.add(txtDir);
			txtDir.setColumns(10);
			txtDir.setText(person.getDireccion());

			JLabel lblNewLabel_4 = new JLabel("Trabaja:");
			lblNewLabel_4.setBounds(10, 233, 46, 14);
			panel.add(lblNewLabel_4);

			rdbtnSi = new JRadioButton("Si");
			rdbtnSi.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					rdbtnNo.setSelected(false);
					person.setContratado(true);
				}
			});
			rdbtnSi.setBounds(80, 229, 46, 23);
			panel.add(rdbtnSi);

			rdbtnNo = new JRadioButton("No");
			rdbtnNo.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					rdbtnSi.setSelected(false);
					person.setContratado(false);
				}
			});
			rdbtnNo.setBounds(141, 229, 46, 23);
			panel.add(rdbtnNo);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnMod = new JButton("Modificar");
				btnMod.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String antiguo = person.getId();
						person.setId(txtCedula.getText());
						person.setNombre(txtNom.getText());
						person.setTelefono(txtTelf.getText());
						person.setDireccion(txtDir.getText());
						JOptionPane.showMessageDialog(null, "Persona Modificada", "Informacion",
								JOptionPane.INFORMATION_MESSAGE);
						ListPersonas.loadPersons();
						Bolsa.getInstance().cambiarCedula(antiguo, txtCedula.getText());
						
						if(rdbtnSi.isSelected())
						{
							Bolsa.getInstance().desactivarSoliPersona(person.getId());
							Bolsa.getInstance().contrarPersona(person.getId());
						}
						
						else
						{
							Bolsa.getInstance().reactivarSoliPersona(person.getId());
							Bolsa.getInstance().desemplearPersona(person.getId());
						}
					}
				});
				btnMod.setActionCommand("OK");
				buttonPane.add(btnMod);
				getRootPane().setDefaultButton(btnMod);
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

				if (person.isContratado())
					rdbtnSi.setSelected(true);
				else
					rdbtnNo.setSelected(true);

			}
		}
	}
}
