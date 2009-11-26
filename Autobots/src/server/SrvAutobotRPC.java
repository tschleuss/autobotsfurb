package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

import com.enumeration.TipoTerreno;

import rpc.structs.*;
import netbula.ORPC.*;

//classe que estende o stub e implementa os métodos
class autobotsServer extends Autobots_svcb {

	mapConfig mc = null;
	
	String[]map = null;
	
    public mapConfig getMap() {
    	
    	System.out.println("RECUPERAR MAPA");
    	
    	this.map = readMap();
    	String printMap = "";
    	
    	int qtdLinhas = 0;
    	int qtdColunas = 0;
    	
    	if(this.map != null){
    		for (String mapLine : this.map) {
    			
    			mapLine = mapLine.replaceAll("\\s", "");
    			
    			if(!mapLine.equals("")){
    				printMap += mapLine + "\n";
    				qtdLinhas++;
    				
    				if(mapLine.length() > qtdColunas)
    					qtdColunas = mapLine.length();
    			}
			}
    	}
    	
    	this.mc = new mapConfig();
    	
    	mc.caminho = printMap;
    	mc.linhas = qtdLinhas;
    	mc.colunas = qtdColunas;
    	
        return mc;
    }
    
	private String[] readMap() {
		String[] selectedMap = null;
		
		try {
			StringBuffer lines = new StringBuffer("");
			URL url = new URL("file:src/server/resource/maps.list");
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			String line;
			
			while(null != (line = in.readLine())) {
				if (line.length() > 0 && line.charAt(0) != '/') {
					lines.append(line).append("\n");
				}
			}
			
			String[] maps = lines.toString().split("MAP");
			int mapID = 1 + (int)(Math.random() * maps.length-1); 
			
			selectedMap = maps[mapID].split("\n");
			
		} catch(Exception e) {
			System.err.println("Arquivo maps.list não encontrado. Exception: "+e);
		}

		return selectedMap;
	} 

	@Override
	public botPosition getBotPosition() {
		
		System.out.println("RECUPERAR CAMINHO DO ROBO");
		
		String mapLine;
		int randomX,randomY,randomPos;
		boolean validpos = false;
		
		botPosition bp = null;
		
		Random r = new Random();
		
		while(!validpos){
			
			randomX = r.nextInt( this.mc.linhas -1);
			mapLine = this.map[randomX].replaceAll("\\s", "");
			randomY = r.nextInt(this.mc.colunas-1);
			
			randomPos = Integer.parseInt(Character.toString(mapLine.charAt(randomY)));
			
			if(randomPos == TipoTerreno.GRASS.getType()){
				bp = new botPosition();
				
				bp.x = randomX;
				bp.y = randomY;
				validpos = true;
				System.out.println("X: " + bp.x + " | Y: "+ bp.y);
				
			}
		}
		return bp;
	}

	@Override
	public mapLayoutPercent getLayout() {
		
		System.out.println("RECUPERAR PORCENTAGEM DE ELEMENTOS NO MAPA");
		
		mapLayoutPercent mlp = new mapLayoutPercent();
		
		int qtdGrass=0, qtdWater=0, qtdTrees = 0, qtdTotal = 0;
		
		String mapLine = "";
		
		for(int i = 1; i < map.length; i++){
			
			mapLine = map[i].replaceAll("\\s", "");
			
			qtdTotal += mapLine.length();
			qtdGrass += mapLine.replaceAll("[^"+ TipoTerreno.GRASS.getType() +"]","").length();
			qtdWater += mapLine.replaceAll("[^"+ TipoTerreno.WATER.getType() +"]","").length();
			qtdTrees += mapLine.replaceAll("[^"+ TipoTerreno.TREES.getType() +"]","").length();
		}
		
		mlp.grassPercent = Math.round((float) qtdGrass * 100 / qtdTotal);
		mlp.waterPercent = Math.round((float)qtdWater * 100 / qtdTotal);
		mlp.treesPercent = Math.round((float)qtdTrees * 100 / qtdTotal);

		return mlp;
	}    	
    
}

public class SrvAutobotRPC {

    public static void main(String args[]) {
        try {
        	System.out.println("Servidor AUTOBOTS RPC iniciado.");
            new autobotsServer().run();
        } catch (rpc_err e) {
            System.out.println("Erro SrvAutobotRPC" + e.toString());
        }
    }
	
}
