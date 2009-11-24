package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RequestServers extends JFrame {

	private String serverRMI ="";
	private String serverRPC ="";
	private String serverCorba ="";
	
	private JTextField txtServerRPC;
	private JTextField txtServerRMI;
	private JTextField txtServerCorba;
	private JButton botaoOK;
	private JButton botaoCancel;

	public RequestServers(){
		super("Servidores");
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);		
		super.setSize(270, 170);
		super.setResizable(true);
		this.initComponents();		
	}
	
	private void initComponents(){
	
		JPanel panelTop = new JPanel(new GridLayout(2,1));
		JPanel panelServers = new JPanel(new GridLayout(4,2));
		JPanel panelBotoes = new JPanel(new GridLayout(1, 2));

		panelTop.add(new JLabel("Informe os endereços dos servidores"));
		getContentPane().add(BorderLayout.NORTH, panelTop);
		
		txtServerRPC = new JTextField("localhost");
		panelServers.add(new JLabel("Servidor RPC:"));
		panelServers.add(txtServerRPC);
		
		txtServerRMI = new JTextField("localhost");
		panelServers.add(new JLabel("Servidor RMI:"));
		panelServers.add(txtServerRMI);
		
		txtServerCorba = new JTextField("localhost");
		panelServers.add(new JLabel("Servidor Corba:"));
		panelServers.add(txtServerCorba);

		getContentPane().add(BorderLayout.CENTER, panelServers);

		botaoOK = new JButton();
		botaoOK.setText("Ok");

		botaoCancel = new JButton();
		botaoCancel.setText("Cancelar");

		panelBotoes.add(botaoOK);
		panelBotoes.add(botaoCancel);

		getContentPane().add(BorderLayout.SOUTH, panelBotoes);

		botaoOK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					HostsServers h = new HostsServers();
					h.hostCorba = txtServerCorba.getText();
					h.hostRMI 	= txtServerRMI.getText();
					h.hostRPC	= txtServerRPC.getText();

					Janela j = new Janela(h);
					j.setLocationRelativeTo(null);
					j.setVisible(true);

					dispose();
					
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
		
		botaoCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				try {
					dispose();
					
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getServerCorba() {
		return serverCorba;
	}
	public String getServerRMI() {
		return serverRMI;
	}
	public String getServerRPC() {
		return serverRPC;
	}
	
	public class HostsServers{
		public String hostCorba ="";
		public String hostRPC ="";
		public String hostRMI ="";
	}
	
	
}