package client.engine;

import client.engine.view.Janela;



public final class Main {
	
	
	private Main() {
		super();
	}

	/**
	 * Inicializa a a janela (JFrame)
	 * com as op��es de comunica��o.
	 * @param argv
	 */
	public static void main(String[] argv) 
	{
		Janela j = new Janela();		//Instancia a jenela
		j.setLocationRelativeTo(null);	//Centraliza a janela
		j.setVisible(true);				//Exibe a janela
	}
	
}
