package client.view;

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
import com.structs.Caminho;
import com.structs.Passo;


import client.CliAutobotsCorba;
import client.view.RequestServers.HostsServers;

import netbula.ORPC.rpc_err;
import rpc.structs.mapLayoutPercent;

public class Janela extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int SCREEN_WIDTH = 510;
	public static final int SCREEN_HEIGHT = 569;

	private GameView gameView = null;
	private JButton botaoCaminhoBox = null;
	private JButton botaoCaminhoTarget = null;
	private JButton botaoBoxTarget = null;
	private JButton botaoAgua = null;
	private JButton botaoArvore = null;
	private JButton botaoCaminhos = null;
	private JButton botaoMapPercent = null;
	private HostsServers hosts = null;

	CliAutobotsCorba autobotsCORBA_cln = null;

	public Janela(HostsServers hosts) 
	{		
		super("AUTOBOTS");
		super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		super.setPreferredSize( new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT) );
		super.setResizable(false);
		this.hosts = hosts;
		this.initComponents();
		this.bindingListeners();
	}
	
	/**
	 * Inicializa os componentes graficos
	 * para o usuário.
	 */
	private void initComponents() 
	{
		gameView	= new GameView(hosts);	//JPanel onde é desenhado o mapa
		this.autobotsCORBA_cln	= this.gameView.getClienteCorba();		
		
		botaoBoxTarget	= new JButton();
		botaoCaminhoBox = new JButton();
		botaoCaminhoTarget = new JButton();
        botaoAgua	= new JButton();	//Botoes da tela
        botaoArvore = new JButton();	//Botoes da tela
        botaoCaminhos	= new JButton();	//Botoes da tela
        botaoMapPercent	= new JButton();	//Botoes da tela        

        botaoBoxTarget.setText("Criar caixa e alvo");
        botaoCaminhoBox.setText("Caminhar até a caixa");
        botaoCaminhoTarget.setText("Guardar a caixa");
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
                        .addComponent(botaoBoxTarget)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoCaminhoBox)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botaoCaminhoTarget)                        
                        )                      
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
                	.addComponent(botaoBoxTarget)
                	.addComponent(botaoCaminhoBox)
                	.addComponent(botaoCaminhoTarget)
                	)          
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
		
		botaoBoxTarget.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoBoxTargetHandler(evt);
            }
        });		

		botaoCaminhoBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoCaminhoBoxHandler(evt);
            }
        });	
		
		botaoCaminhoTarget.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	botaoCaminhoTargetHandler(evt);
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
		JOptionPane.showMessageDialog(this, "Procurando arvore !");
		getFasterWay(TipoTerreno.TREES);
	}
	
	
	private void botaoBoxTargetHandler(ActionEvent evt)
	{ 
		String mapString = this.gameView.getMap().getClienteRPC().getMapString();
		this.gameView.resetBoxAndGoal();
		this.gameView.setbAg( autobotsCORBA_cln.getBoxPosition(mapString, String.valueOf(this.gameView.currentX()), String.valueOf(this.gameView.currentY())) );
		this.gameView.setBox(this.gameView.getbAg().boxPosX, this.gameView.getbAg().boxPosY);
		this.gameView.setGoal(this.gameView.getbAg().goalPosX, this.gameView.getbAg().goalPosY);
		this.gameView.repaint(0);
	}
	
	
	private void botaoCaminhoBoxHandler(ActionEvent evt) {
		if(this.gameView.getbAg() != null){
			Caminho c = autobotsCORBA_cln.getPathToBox(String.valueOf(this.gameView.currentX()), String.valueOf(this.gameView.currentY()), String.valueOf(this.gameView.getbAg().boxPosX), String.valueOf(this.gameView.getbAg().boxPosY));
			this.gameView.moveToPath(c,this.gameView.getbAg().boxPosX, this.gameView.getbAg().boxPosY);
		} else {
			JOptionPane.showMessageDialog(this, "Crie a caixa e o alvo antes de traçar a rota.");			
		}
	}

	private void botaoCaminhoTargetHandler(ActionEvent evt) {
		if( this.gameView.getbAg() != null ) {
			if( this.gameView.isHoldingBox() ) {
				Caminho c = autobotsCORBA_cln.getPathToTarget(String.valueOf(this.gameView.currentX()), String.valueOf(this.gameView.currentY()), String.valueOf(this.gameView.getbAg().goalPosX), String.valueOf(this.gameView.getbAg().goalPosY));
				this.gameView.moveToPath(c,this.gameView.getbAg().goalPosX,this.gameView.getbAg().goalPosY);
			} else {
				JOptionPane.showMessageDialog(this, "Você não possui a caixa ainda !");
			}
		}else{
			JOptionPane.showMessageDialog(this, "Crie a caixa e o alvo antes de guardar a caixa.");			
		}
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
