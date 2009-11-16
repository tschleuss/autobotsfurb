package client;

import java.io.Serializable;

import rpc.structs.*;
import netbula.ORPC.*;

public class CliAutobotsRPC implements Serializable{
    
	private Autobots_cln bot;
	
	private mapConfig mc;
	private botPosition bp;
	private mapLayoutPercent mlp;

	public CliAutobotsRPC(String serverhost) {
        
        // inicializa o stub do cliente
    	try {
			this.bot = new Autobots_cln(serverhost, "tcp");
		} catch (rpc_err e) {
			e.printStackTrace();
		}
    }	
	
    public int[][] getMap() throws rpc_err {
       
    	this.mc = bot.getMap();
    	
    	String[] mapVetor = this.mc.caminho.split("\n");
    	
    	int[][]map = new int[this.mc.linhas][this.mc.colunas];
    	
    	int lineNumber = 0;

		for (String line : mapVetor) {
			for(int i = 0; i < this.mc.colunas;i++ ){
				map[lineNumber][i] = Integer.parseInt(Character.toString(line.charAt(i)));
			}
			lineNumber++;
		}    	
    	
		return map;	
    }
    
    public String getMapString(){
    	return this.mc.caminho;
    }
    	
    public botPosition getBot() throws rpc_err{
		this.bp = bot.getBotPosition();
		return this.bp;
	}
    
    public mapLayoutPercent getLayout() throws rpc_err{
		this.mlp = bot.getLayout();
		
		return this.mlp;
	}    
	
}
