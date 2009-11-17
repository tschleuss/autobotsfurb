package client.engine.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import com.enumeration.TipoTerreno;

import corba.structs.autobots.boxAndGoalConfig;

import client.CliAutobotsCorba;

import netbula.ORPC.rpc_err;
import rmi.structs.Caminho;
import rmi.structs.Passo;
import rpc.structs.mapLayoutPercent;

public class Janela extends JFrame {
	
	public static final int SCREEN_WIDTH = 510;
	public static final int SCREEN_HEIGHT = 569;
	
	private GameView gameView = null;
	private JButton botaoCaixa = null;
	private JButton botaoAgua = null;
	private JButton botaoArvore = null;
	private JButton botaoCaminhos = null;
	private JButton botaoMapPercent = null;
	
	
	public Janela() 
	{
		super("AUTOBOTS");
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		super.setPreferredSize( new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT) );
		super.setResizable(false);
		this.initComponents();
		this.bindingListeners();
	}
	
	/**
	 * Inicializa os componentes graficos
	 * para o usuário.
	 */
	private void initComponents() 
	{
		gameView	= new GameView();	//JPanel onde é desenhado o mapa
		botaoCaixa	= new JButton();
        botaoAgua	= new JButton();	//Botoes da tela
        botaoArvore = new JButton();	//Botoes da tela
        botaoCaminhos	= new JButton();	//Botoes da tela
        botaoMapPercent	= new JButton();	//Botoes da tela        

        botaoCaixa.setText("Criar caixa");
        botaoAgua.setText("Buscar Água");
        botaoArvore.setText("Buscar Árvore");
        botaoCaminhos.setText("Casas visitadas");
        botaoMapPercent.setText("Config do Mapa");
        
        gameView.setBackground( new Color(153, 153, 153) );

        GroupLayout gameViewLayout = new GroupLayout(gameView);
        gameView.setLayout(gameViewLayout);
        gameViewLayout.setHorizontalGroup(gameViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 539, Short.MAX_VALUE));
        gameViewLayout.setVerticalGroup(gameViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 385, Short.MAX_VALUE));

        GroupLayout layout = new GroupLayout(this.getContentPane());
        getContentPane().setLayout(layout);
        
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(gameView,GroupLayout.DEFAULT_SIZE,GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    	.addComponent(botaoMapPercent)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoCaminhos)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoArvore)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoAgua))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botaoCaixa))                        
                	)                    
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gameView, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(botaoAgua)
                    .addComponent(botaoArvore)
                    .addComponent(botaoCaminhos)
                    .addComponent(botaoMapPercent))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                	.addComponent(botaoCaixa))          
                .addContainerGap())
        );

        super.pack();
	}
	
	/**
	 * Adiciona os listeners aos botoes,
	 * chamando seus respectivos metodos
	 * ao serem clicados
	 */
	private void bindingListeners() {
		
		botaoCaixa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoCaixaHandler(evt);
            }
        });		
		
		botaoAgua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoAguaHandler(evt);
            }
        });
		
		botaoArvore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoArvoreHandler(evt);
            }
        });
		
		botaoCaminhos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoCaminhosHandler(evt);
            }
        });
		
		botaoMapPercent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoMapPercentHandler(evt);
            }
        });		
	}
	
	/**
	 * Handler para tratar os cliques
	 * efetuados no botão "botaoAgua"
	 * @param evt
	 */
	private void botaoAguaHandler(ActionEvent evt) 
	{
		//TODO
		//CHAMA AS FUNCOES DO BOTAO AGUA
		JOptionPane.showMessageDialog(this, "Procurando agua !");
		getFasterWay(TipoTerreno.WATER);
	}
	
	/**
	 * Handler para tratar os cliques
	 * efetuados no botão "botaoArvore"
	 * @param evt
	 */
	private void botaoArvoreHandler(ActionEvent evt) 
	{
		//TODO
		//CHAMA AS FUNCOES DO BOTAO ARVORE
		JOptionPane.showMessageDialog(this, "Procurando arvore !");
		getFasterWay(TipoTerreno.TREES);
	}
	
	
	private void botaoCaixaHandler(ActionEvent evt)
	{ 
		CliAutobotsCorba cliente	= this.gameView.getClienteCorba();
		String mapString			= this.gameView.getMap().getClienteRPC().getMapString();
		
		boxAndGoalConfig bAg = cliente.getBoxPosition(mapString, String.valueOf(this.gameView.getX()), String.valueOf(this.gameView.getY()));
		
		this.gameView.setBox(bAg.boxPosX, bAg.boxPosY);
		this.gameView.setGoal(bAg.goalPosX, bAg.goalPosY);
		this.gameView.repaint(0);
	}
	
	private void getFasterWay(TipoTerreno type){
		
		Caminho c = this.gameView.getClienteRMI().getFasterWay(type, gameView.getMap() ,this.gameView.currentX(), this.gameView.currentY());
		this.gameView.setCaminhoTerrenoSelected(c);
		this.gameView.repaint(0);
	}
	
	/**
	 * Handler para tratar os cliques
	 * efetuados no botão "botaoEtc"
	 * @param evt
	 */	
	private void botaoCaminhosHandler(ActionEvent evt) 
	{
		List<Passo> steps = this.gameView.getClienteRMI().getPathsTraveled();
		this.gameView.setPathsTraveled(steps);
		this.gameView.repaint(0);
	}
	
	/**
	 * Handler para tratar os cliques
	 * efetuados no botão "botaoMapPercent"
	 * @param evt
	 */	
	private void botaoMapPercentHandler(ActionEvent evt) 
	{
		
		try {
			mapLayoutPercent mlp = this.gameView.getMap().getClienteRPC().getLayout();
			
			JOptionPane.showMessageDialog(this, "GRAMA: " + mlp.grassPercent + "% "+
												" / ÁGUA: " + mlp.waterPercent + "% "+ 
												" / ÁRVORES: " + mlp.treesPercent + "%");
			
		} catch (rpc_err e) {
			e.printStackTrace();
		}
		
	}	
}
