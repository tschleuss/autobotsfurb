package client;

import java.rmi.Naming;
import java.util.List;

import com.enumeration.TipoTerreno;
import com.structs.Caminho;
import com.structs.GameMap;
import com.structs.Passo;

import rmi.interfaces.Autobots;
import rmi.interfaces.Map;
import server.heuristica.UnitMover;

public class CliAutobotsRMI {
	
	
	private Autobots bot;
	private Map map;
	
	public CliAutobotsRMI(Map map, String serverHost ){
        try {
			bot = (Autobots)Naming.lookup("//" + serverHost + "/SrvAutobotRMI");
			this.map = map;
        } catch (Exception ex) {
            System.out.println("Erro CliAutobotsRMI: " + ex.getMessage());
         } 
	}
	
	public Caminho getFasterWay(TipoTerreno type, Map map, int x, int y) {
		try{
			return bot.getFasterWay(type, map, x, y);
		} catch (Exception ex) {
			System.out.println("Erro CliAutobotsRMI: " + ex.getMessage());
	    }
		
		return null;
	}

	public Caminho getPath(int type, int selectedx, int selectedy, int x, int y) {
		try {
			return bot.getPath(map, new UnitMover(type), selectedx, selectedy, x, y);
		} catch (Exception ex) {
			System.out.println("Erro CliAutobotsRMI: " + ex.getMessage());
	    }
		
		return null;
	}


	public List<Passo> getPathsTraveled(){
		try {
			return bot.getPathsTraveled();
		} catch (Exception ex) {
			System.out.println("Erro CliAutobotsRMI: " + ex.getMessage());
	    }
		
		return null;
	}
}
