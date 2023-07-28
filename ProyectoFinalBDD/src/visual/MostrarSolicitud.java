package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import logico.Bolsa;
import logico.EmpTecnico;
import logico.EmpUniversitario;
import logico.Persona;
import logico.SoliEmpresa;
import logico.SoliPersona;
import logico.Solicitud;
import logico.Tecnico;
import logico.Universitario;

@SuppressWarnings("serial")
public class MostrarSolicitud extends JDialog
{

	private final JPanel contentPanel = new JPanel();
	private JTextField txtIdentificacion;
	private JTextField txtPorcentaje;
	private JTextField txtNombre;
	private JTextField txtTipo;
	private JTextField txtCarrera_Area;
	private JTextField txtAgnos;
	private JTextField txtTipoSueldo;
	private JTextField txtContrato;
	private JTextField txtSueldo;
	private JTextField txtCiudad;
	private JTextField txtMovilidad;
	private JTextField txtLicencia;
	private Solicitud solicitud = null;
	private Persona persona = null;
	private JLabel lblNewLabel_1;
	private JLabel lblPorcentaje;
	private JLabel lblagnos;
	private JLabel lblTipoSueldo;
	private JLabel lblNewLabel_2;

	@SuppressWarnings({ })
	public MostrarSolicitud(Solicitud aux)
	{
		solicitud = aux;
		if (solicitud instanceof SoliPersona)
			persona = Bolsa.getInstance().buscarPersonaByCedula(((SoliPersona) solicitud).getCedula());

		setTitle("Solicitud: " + solicitud.getCodigo());
		setResizable(false);
		setBounds(100, 100, 585, 471);
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

			JPanel panelDatos = new JPanel();
			panelDatos.setBorder(new TitledBorder(null, "Datos", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panelDatos.setBounds(10, 11, 543, 643);
			panel.add(panelDatos);
			panelDatos.setLayout(null);

			JLabel lblrnc_cedula = new JLabel("Identif:");
			lblrnc_cedula.setBounds(12, 30, 80, 14);
			panelDatos.add(lblrnc_cedula);

			lblPorcentaje = new JLabel("Porcentaje:");
			lblPorcentaje.setBounds(409, 363, 73, 14);
			panelDatos.add(lblPorcentaje);

			txtIdentificacion = new JTextField();
			txtIdentificacion.setEditable(false);
			txtIdentificacion.setBounds(68, 27, 109, 20);
			panelDatos.add(txtIdentificacion);
			txtIdentificacion.setColumns(10);
			if (solicitud instanceof SoliPersona)
				txtIdentificacion.setText(((SoliPersona) solicitud).getCedula());
			else if (solicitud instanceof SoliEmpresa)
				txtIdentificacion.setText(((SoliEmpresa) solicitud).getRnc());

			txtPorcentaje = new JTextField();
			txtPorcentaje.setEditable(false);
			txtPorcentaje.setBounds(485, 360, 46, 20);
			panelDatos.add(txtPorcentaje);
			txtPorcentaje.setColumns(10);
			if (solicitud instanceof SoliEmpresa)
				txtPorcentaje.setText(String.valueOf(((SoliEmpresa) solicitud).getPorcentajeMacth()));

			lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setBounds(253, 30, 73, 14);
			panelDatos.add(lblNewLabel_1);

			JLabel lblNewLabel_5 = new JLabel("Tipo:");
			lblNewLabel_5.setBounds(10, 103, 46, 14);
			panelDatos.add(lblNewLabel_5);

			if (solicitud instanceof EmpUniversitario || persona instanceof Universitario)
			{
				lblNewLabel_2 = new JLabel("Carrera:");
				lblNewLabel_2.setBounds(10, 182, 61, 14);
				panelDatos.add(lblNewLabel_2);
			}
			else
			{
				lblNewLabel_2 = new JLabel("Area:");
				lblNewLabel_2.setBounds(10, 182, 61, 14);
				panelDatos.add(lblNewLabel_2);
			}

			lblagnos = new JLabel("Años:");
			lblagnos.setBounds(253, 106, 46, 14);
			panelDatos.add(lblagnos);

			JLabel lblNewLabel_8 = new JLabel("Contrato:");
			lblNewLabel_8.setBounds(10, 176, 80, 14);
			panelDatos.add(lblNewLabel_8);

			JLabel lblNewLabel_9 = new JLabel("Sueldo:");
			lblNewLabel_9.setBounds(12, 247, 46, 14);
			panelDatos.add(lblNewLabel_9);

			lblTipoSueldo = new JLabel("Tipo:");
			lblTipoSueldo.setBounds(253, 176, 96, 14);
			panelDatos.add(lblTipoSueldo);

			JLabel lblNewLabel_12 = new JLabel("Ciudad:");
			lblNewLabel_12.setBounds(253, 244, 46, 14);
			panelDatos.add(lblNewLabel_12);

			JLabel lblNewLabel_13 = new JLabel("Movil:");
			lblNewLabel_13.setBounds(12, 316, 73, 14);
			panelDatos.add(lblNewLabel_13);

			JLabel lblNewLabel_14 = new JLabel("Licencia:");
			lblNewLabel_14.setBounds(253, 319, 61, 14);
			panelDatos.add(lblNewLabel_14);

			txtNombre = new JTextField();
			txtNombre.setEditable(false);
			txtNombre.setBounds(316, 27, 185, 20);
			panelDatos.add(txtNombre);
			txtNombre.setColumns(10);
			if (solicitud instanceof SoliPersona)
				txtNombre.setText(
						Bolsa.getInstance().buscarPersonaByCedula(((SoliPersona) solicitud).getCedula()).getNombre());
			else if (solicitud instanceof SoliEmpresa)
				txtNombre.setText(Bolsa.getInstance().buscarEmpresaByRNC(((SoliEmpresa) solicitud).getRnc()).getNombre());

			txtTipo = new JTextField();
			txtTipo.setEditable(false);
			txtTipo.setBounds(66, 100, 166, 20);
			panelDatos.add(txtTipo);
			txtTipo.setColumns(10);
			if (solicitud instanceof EmpUniversitario || persona instanceof Universitario)
				txtTipo.setText("Universitario");
			else if (solicitud instanceof EmpTecnico || persona instanceof Tecnico)
				txtTipo.setText("Tecnico");
			if (solicitud instanceof SoliEmpresa)

			txtCarrera_Area = new JTextField();
			txtCarrera_Area.setEditable(false);
			txtCarrera_Area.setBounds(66, 179, 166, 20);
			panelDatos.add(txtCarrera_Area);
			txtCarrera_Area.setColumns(10);

			if (solicitud instanceof SoliEmpresa)
			{
				if (solicitud instanceof EmpUniversitario)
					txtCarrera_Area.setText(((EmpUniversitario) solicitud).getCarrera());
				else if (solicitud instanceof EmpTecnico)
					txtCarrera_Area.setText(((EmpTecnico) solicitud).getArea());
			}
			else if (solicitud instanceof SoliPersona)
			{
				if (persona instanceof Universitario)
					txtCarrera_Area.setText(((Universitario) persona).getCarrera());
				else if (persona instanceof Tecnico)
					txtCarrera_Area.setText(((Tecnico) persona).getArea());
			}

			txtAgnos = new JTextField();
			txtAgnos.setEditable(false);
			txtAgnos.setBounds(316, 103, 61, 20);
			panelDatos.add(txtAgnos);
			txtAgnos.setColumns(10);
			if (solicitud instanceof SoliEmpresa)
			{
				if (solicitud instanceof EmpUniversitario)
					txtAgnos.setText(String.valueOf(((EmpUniversitario) solicitud).getAgnos()));
				else if (solicitud instanceof EmpTecnico)
					txtAgnos.setText(String.valueOf(((EmpTecnico) solicitud).getAgnos()));
			}
			else if (solicitud instanceof SoliPersona)
			{
				if (persona instanceof Universitario)
					txtAgnos.setText(String.valueOf(((Universitario) persona).getAgnos()));
				else if (persona instanceof Tecnico)
					txtAgnos.setText(String.valueOf(((Tecnico) persona).getAgnos()));
			}

			txtTipoSueldo = new JTextField();
			txtTipoSueldo.setEditable(false);
			txtTipoSueldo.setBounds(316, 173, 126, 20);
			panelDatos.add(txtTipoSueldo);
			txtTipoSueldo.setColumns(10);
			if (solicitud instanceof SoliEmpresa)
				txtTipoSueldo.setText(((SoliEmpresa) solicitud).getTipoSalario());

			txtContrato = new JTextField();
			txtContrato.setEditable(false);
			txtContrato.setBounds(66, 173, 166, 20);
			panelDatos.add(txtContrato);
			txtContrato.setColumns(10);
			txtContrato.setText(solicitud.getContrato());

			txtSueldo = new JTextField();
			txtSueldo.setEditable(false);
			txtSueldo.setBounds(68, 244, 109, 20);
			panelDatos.add(txtSueldo);
			txtSueldo.setColumns(10);
			txtSueldo.setText(String.valueOf(solicitud.getSueldo()));

			txtCiudad = new JTextField();
			txtCiudad.setEditable(false);
			txtCiudad.setBounds(316, 241, 109, 20);
			panelDatos.add(txtCiudad);
			txtCiudad.setColumns(10);
			txtCiudad.setText(solicitud.getCuidad());

			txtMovilidad = new JTextField();
			txtMovilidad.setEditable(false);
			txtMovilidad.setBounds(66, 313, 54, 20);
			panelDatos.add(txtMovilidad);
			txtMovilidad.setColumns(10);
			txtMovilidad.setText(solicitud.isMovilidad() ? "Si" : "No");

			txtLicencia = new JTextField();
			txtLicencia.setEditable(false);
			txtLicencia.setBounds(316, 316, 54, 20);
			panelDatos.add(txtLicencia);
			txtLicencia.setColumns(10);
			txtLicencia.setText(solicitud.isLicencia() ? "Si" : "No");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
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
		if (solicitud instanceof SoliPersona)
		{
			lblPorcentaje.setVisible(false);
			txtPorcentaje.setVisible(false);
			txtTipoSueldo.setVisible(false);
			lblTipoSueldo.setVisible(false);

		}
	}
}
