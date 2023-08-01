package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;


import logico.Bolsa;

@SuppressWarnings({ "serial", "unused" })
public class Principal extends JFrame
{

	private JPanel contentPane;
	private Dimension dim = null;
	private JMenu menuAdministracion;
	
	/*public static void main(String[] args) throws ClassNotFoundException, IOException
	{
		try
		{
			Principal dialog = new Principal();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/

	public Principal()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/images/Icon.png")));
		setTitle("Bolsa de Trabajo");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(dim.width, dim.height - 40);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBackground(new Color(222, 184, 135));
		setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("Solicitudes");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("Ingresar de Empresa");
		mntmNewMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SolEmpresa solEmp = new SolEmpresa(null);
				solEmp.setModal(true);
				solEmp.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Ingresar de Persona");
		mntmNewMenuItem_5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				SolPersona solPer = new SolPersona(null);
				solPer.setModal(true);
				solPer.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_5);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Listar Solicitudes");
		mntmNewMenuItem_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListSolicitudes listSol = new ListSolicitudes(false);
				listSol.setModal(true);
				listSol.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Listar Ofertas");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List_oferta ofertas = new List_oferta(false);
				ofertas.setModal(true);
				ofertas.setVisible(true);
			}
		});
		mnNewMenu.add(mntmNewMenuItem_7);

		JMenu mnNewMenu_1 = new JMenu("Personas");
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Listar");
		mntmNewMenuItem_2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListPersonas listPer = new ListPersonas();
				listPer.setModal(true);
				listPer.setVisible(true);
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);

		JMenu mnNewMenu_2 = new JMenu("Empresas");
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Listar");
		mntmNewMenuItem_3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ListEmpresa listEmp = new ListEmpresa();
				listEmp.setModal(true);
				listEmp.setVisible(true);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);

		menuAdministracion = new JMenu("Administracion");
		menuBar.add(menuAdministracion);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Match");
		mntmNewMenuItem_4.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				List_oferta listSol = new List_oferta(true);
				listSol.setModal(true);
				listSol.setVisible(true);
			}
		});
		menuAdministracion.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Agregar Usuario");
		mntmNewMenuItem_6.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				RegUsuario usuario = new RegUsuario();
				usuario.setModal(true);
				usuario.setVisible(true);
			}
		});
		menuAdministracion.add(mntmNewMenuItem_6);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel Foto = new JLabel();
		Foto.setIcon(new ImageIcon(Principal.class.getResource("/images/Fondo Principal.png")));
		Foto.setBounds(0, 0, 1910, 985);
		panel.add(Foto);
	}
}
