package client;

import client.view.RequestServers;

public final class Main {
	
	
	private Main() {
		super();
	}

	/**
	 * Inicializa a a janela (JFrame)
	 * com as opções de comunicação.
	 * @param argv
	 */
	public static void main(String[] argv) 
	{
		
		RequestServers servers = new RequestServers();
		servers.setLocationRelativeTo(null);
		servers.setVisible(true);
	}
	
}
