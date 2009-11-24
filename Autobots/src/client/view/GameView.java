package client.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import rmi.interfaces.Map;
import rpc.structs.botPosition;
import client.CliAutobotsCorba;
import client.CliAutobotsRMI;
import client.view.RequestServers.HostsServers;

import com.enumeration.TipoTerreno;
import com.structs.Caminho;
import com.structs.GameMap;
import com.structs.Movimento;
import com.structs.Passo;

import corba.structs.autobots.boxAndGoalConfig;

public class GameView extends JPanel {

	private static final long serialVersionUID = 2890569888473739137L;
	
	public static final int START_X = 0;
	public static final int START_Y = 0;
	public static final int ICON_WIDTH = 16;
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 600;

	private GameMap map;					// O mapa onde a unidades irão se mover
	private Caminho path;						// O ultimo caminho encontrado para o robo
	private Image[] tiles = new Image[7];	// Lista das imagens para renderizar
	private Image buffer;					// Buffer para renderização
	private int selectedx = -1;				// Coordenadas X do robo selecionado ou -1 para nada selecionado
	private int selectedy = -1;				// Coordenadas Y do robo selecionado ou -1 para nada selecionado
	private CliAutobotsRMI autobotsRMI_cln;
	private CliAutobotsCorba autobotsCORBA_cln;
	private Movimento mov;
	
	private Caminho caminhoTerrenoSelected;
	private boxAndGoalConfig bAg = null;
	private Passo box;
	private Passo goal;
	
	private HostsServers hosts =null;
	
	private boolean isHoldingBox;
	
	private List<Passo> pathsTraveled;

	public GameView(HostsServers hosts) 
	{
		super.setBackground( new Color(153, 153, 153) );
		this.hosts = hosts;
		this.initComponents();
		this.initBotsComponents();
	}
	
	/**
	 * Inicializa os componentes dos Robos
	 * Buscando o mapa, a localização do robo, etc.
	 */
	private void initBotsComponents() 
	{
		try 
		{
			tiles[TipoTerreno.TREES.getType()] = ImageIO.read(getResource("client/view/resource/trees.png"));
			tiles[TipoTerreno.GRASS.getType()] = ImageIO.read(getResource("client/view/resource/grass.png"));
			tiles[TipoTerreno.WATER.getType()] = ImageIO.read(getResource("client/view/resource/water.png"));
			tiles[TipoTerreno.ROBOT.getType()] = ImageIO.read(getResource("client/view/resource/megaman.png"));
			tiles[TipoTerreno.BOX.getType()] = ImageIO.read(getResource("client/view/resource/box.png"));
			tiles[TipoTerreno.GOAL.getType()] = ImageIO.read(getResource("client/view/resource/goal.png"));
			tiles[TipoTerreno.ROBOT_BOX.getType()] = ImageIO.read(getResource("client/view/resource/megaman_box.png"));
		} 
		catch (IOException e) {
			System.err.println("Falhou ao carregar os recursos: " + e.getMessage() );
			System.exit(0);
		}
		
		boolean isRequested = requestServerInfo();
		
		if( isRequested ) 
		{			
			map = new GameMap(hosts.hostRPC);
			
			botPosition bp = map.getBotInitialPosition();
			
			selectedx = bp.y;
			selectedy = bp.x;
			
			autobotsRMI_cln = new CliAutobotsRMI(map, hosts.hostRMI);
			autobotsCORBA_cln = new CliAutobotsCorba(hosts.hostCorba);
		} 
		else {
			
			System.exit(0);
		}
		
	}
	
	/**
	 * Recupera algumas informações
	 * com o usuário.
	 * @return
	 */
	private boolean requestServerInfo() 
	{
		
		//servers.setSize(400, 600);
		//servers.show();
		
		/*
		int userRet = JOptionPane.showConfirmDialog(null, 
			"Pressione o botão 'OK' para requisitar\nas informações do servidor", "Autobots", JOptionPane.OK_CANCEL_OPTION 
		);
		
		if( userRet == JOptionPane.CANCEL_OPTION ) {
			return false;
		}*/
		
		return true;
	}
	
	
	/**
	 * Carrega as imagens com base na sua localização
	 * nos pacotes do projeto
	 * @param ref
	 * @return
	 * @throws IOException
	 */
	private InputStream getResource(String ref) throws IOException 
	{
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(ref);
		
		if ( in != null ) {
			return in;
		}
		
		return new FileInputStream(ref);
	}
	
	/**
	 * Realiza o bindind dos eventos
	 * que serão disparados ao mover o
	 * mouse ou clicar nos seus botões
	 */
	private void initComponents() 
	{
		this.addMouseListener(new MouseAdapter() 
		{
			public void mousePressed(MouseEvent e) 
			{
				handleMousePressed(e.getX(), e.getY());
			}
		});
	}
	
	/**
	 * Handler para tratar os movimentos do mouse
	 * quando pressionado
	 * @param x - Coordenada X do mouse na tela
	 * @param y - Coordenada Y do mouse na tela
	 */
	private void handleMousePressed(int x, int y) {
		
		caminhoTerrenoSelected = null;
		
		x -= 0;
		y -= 0;
		x /= ICON_WIDTH;
		y /= ICON_WIDTH;
		
		repaint(0);
		
		if ((x < 0) || (y < 0) || (x >= map.getWidthInTiles()) || (y >= map.getHeightInTiles())) {
			return;
		}
		
		if (map.getUnit(x, y) != 0) {
			selectedx = x;
			selectedy = y;
		} else {
			if (selectedx != -1) {
				map.clearVisited();
				
				path = autobotsRMI_cln.getPath(map.getUnit(selectedx, selectedy),selectedx, selectedy, x, y );
				
				if (path != null) {

					mov = new Movimento(map, path, this, selectedx, selectedy);
					mov.start();

					selectedx = x;
					selectedy = y;
				}
			}
		}
		
		repaint(0);
	}
	
	public void moveToPath( Caminho path , int x , int y ) {
		
		x -= 0;
		y -= 0;
		x /= ICON_WIDTH;
		y /= ICON_WIDTH;
		
		repaint(0);
		map.clearVisited();
		
		this.path = path;
		mov = new Movimento(map, path, this, selectedx, selectedy);
		mov.start();
		
		selectedx = path.getLastStep().getX();
		selectedy = path.getLastStep().getY();
		
		repaint(0);
	}

	/**
	 * Metodo utilizado para pintar
	 * as imagens na tela
	 */
	public void paint(Graphics graphics) 
	{
		if (buffer == null) {
			buffer = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);			
		}
		Graphics g = buffer.getGraphics();

		g.clearRect(0,0,SCREEN_WIDTH,SCREEN_HEIGHT);
		g.translate(0,0);

		for ( int x = 0; x < map.getWidthInTiles(); x++ ) 
		{
			for ( int y = 0; y < map.getHeightInTiles(); y++ ) 
			{
				g.drawImage( tiles[map.getTerrain(x, y)] , x * ICON_WIDTH , y * ICON_WIDTH , null );
				
				if ( map.getUnit(x, y) != 0 ) {
					
					g.drawImage( tiles[map.getUnit(x, y)] , x * ICON_WIDTH , y * ICON_WIDTH , null );
				} 
				else {
					
					if ( path != null ) {
						
						if ( path.contains(x, y) && !path.containsOld(x, y) ) {
							
							if ( mov != null ){
								
								g.setColor(Color.blue);
								g.fillRect((x*ICON_WIDTH)+4, (y*ICON_WIDTH)+4,7,7);
							}
						}
					}	
				}
			}
		}

		if (selectedx != -1) {
			g.setColor(Color.black);
			g.drawRect(selectedx*ICON_WIDTH, selectedy*ICON_WIDTH, 15, 15);
			g.drawRect((selectedx*ICON_WIDTH)-2, (selectedy*ICON_WIDTH)-2, 19, 19);
			g.setColor(Color.white);
			g.drawRect((selectedx*ICON_WIDTH)-1, (selectedy*ICON_WIDTH)-1, 17, 17);
		}
		
		//Desenha, se necessario, o elemento encontrado
		if(caminhoTerrenoSelected != null){
			int caminhoMax = caminhoTerrenoSelected.getLength()-1;
			g.setColor(Color.red);
			g.fillRect((caminhoTerrenoSelected.getX(caminhoMax)*ICON_WIDTH)+4, (caminhoTerrenoSelected.getY(caminhoMax)*ICON_WIDTH)+4,7,7);			
		}
		
		if( box != null && !isHoldingBox ) {
			
			if ( map.getUnit( box.getX() , box.getY() ) != 0 || isHoldingBox ) {
				
				Image tmpRobot = tiles[TipoTerreno.ROBOT.getType()];
				Image tmpRBox  = tiles[TipoTerreno.ROBOT_BOX.getType()];
				
				tiles[TipoTerreno.ROBOT.getType()] = tmpRBox;
				tiles[TipoTerreno.ROBOT_BOX.getType()] = tmpRobot;
				
				isHoldingBox = true;
				
				g.drawImage( tiles[TipoTerreno.ROBOT.getType()] , box.getX() * ICON_WIDTH , box.getY() * ICON_WIDTH , null );
				
			} else {
				
				g.drawImage(tiles[TipoTerreno.BOX.getType()],box.getX()*ICON_WIDTH,box.getY()*ICON_WIDTH,null);
			}
		}
		
		if( goal != null ) {
			
			if ( map.getUnit( goal.getX() , goal.getY() ) != 0 && isHoldingBox ) {
				
				Image tmpRobot = tiles[TipoTerreno.ROBOT.getType()];
				Image tmpRBox  = tiles[TipoTerreno.ROBOT_BOX.getType()];
				
				tiles[TipoTerreno.ROBOT.getType()] = tmpRBox;
				tiles[TipoTerreno.ROBOT_BOX.getType()] = tmpRobot;
				
				isHoldingBox = false;
				
				g.drawImage( tiles[TipoTerreno.ROBOT.getType()] , box.getX() * ICON_WIDTH , box.getY() * ICON_WIDTH , null );
				repaint(0);
				
				//Reseta tudo ao acabar
				goal = null;
				box = null;
				bAg = null;
				
			} else {
				
				g.drawImage(tiles[TipoTerreno.GOAL.getType()],goal.getX()*ICON_WIDTH,goal.getY()*ICON_WIDTH,null);
			}
		}		
		
		//Desenha, se necessario, os caminhos acessados
		if( pathsTraveled != null ) {
			
			for( Passo s : pathsTraveled ) 
			{
				g.setColor(Color.BLACK);
				g.fillRect((s.getX()*ICON_WIDTH)+4, (s.getY()*ICON_WIDTH)+4,7,7);	
			}
			
			pathsTraveled.clear();
		}

		graphics.drawImage(buffer, 0, 0, null);
	}
	
	public void resetBoxAndGoal() {
		
		if( isHoldingBox ) {
			
			Image tmpRobot = tiles[TipoTerreno.ROBOT.getType()];
			Image tmpRBox  = tiles[TipoTerreno.ROBOT_BOX.getType()];
			
			tiles[TipoTerreno.ROBOT.getType()] = tmpRBox;
			tiles[TipoTerreno.ROBOT_BOX.getType()] = tmpRobot;
			
			box = null;
			goal = null;
			isHoldingBox = false;
			
			repaint(0);
		}
		
	}
	
	public CliAutobotsRMI getClienteRMI(){
		return this.autobotsRMI_cln;
	}
	
	public CliAutobotsCorba getClienteCorba(){
		return this.autobotsCORBA_cln;
	}	
	
	public int currentX(){
		return this.selectedx;
	}
	
	public int currentY(){
		return this.selectedy;
	}
	
	public Map getMap(){
		return this.map;
	}

	public void setCaminhoTerrenoSelected(Caminho caminhoTerrenoSelected) {
		this.caminhoTerrenoSelected = caminhoTerrenoSelected;
	}
	
	public void setBox(int x, int y){
		this.box = new Passo(y, x);
	}

	public void setGoal(int x, int y) {
		this.goal = new Passo(y, x);
	}	

	public Caminho getCaminhoTerrenoSelected() {
		return caminhoTerrenoSelected;
	}

	public void setPathsTraveled(List<Passo> pathsTraveled) {
		this.pathsTraveled = pathsTraveled;
	}

	public final boolean isHoldingBox() {
		return isHoldingBox;
	}

	public final void setHoldingBox(boolean isHoldingBox) {
		this.isHoldingBox = isHoldingBox;
	}

	public final boxAndGoalConfig getbAg() {
		return bAg;
	}

	public final void setbAg(boxAndGoalConfig bAg) {
		this.bAg = bAg;
	}
}
