package server;


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import com.enumeration.TipoTerreno;

import rmi.interfaces.Autobots;
import rmi.interfaces.Map;
import rmi.interfaces.Mover;
import rmi.structs.Caminho;
import rmi.structs.Passo;
import server.heuristica.RastreadorCaminho;
import server.heuristica.UnitMover;
import server.heuristica.busca.BuscaElemento;

public class SrvAutobotRMI extends UnicastRemoteObject implements Autobots {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Passo> caminhosPercorridos = new ArrayList<Passo>();
	
   public SrvAutobotRMI() throws RemoteException {
      super();
   }
	
   public static void main(String[] args) {
	   try {
		  SrvAutobotRMI obj = new SrvAutobotRMI();
		  Naming.rebind("//localhost/SrvAutobotRMI", obj);
		  System.out.println("Servidor AUTOBOTS RMI iniciado.");
	   } catch (Exception ex) {
	      System.out.println("Erro SrvAutobotRMI: " + ex.getMessage());
	   }    
	}
	
	public Caminho getFasterWay(TipoTerreno type, Map map, int x, int y) throws RemoteException{

		BuscaElemento be = new BuscaElemento();
		Passo posXY = be.buscaElemento(type, map, x, y);

		RastreadorCaminho finder = new RastreadorCaminho(map, 500, true);
		return finder.findPath( new UnitMover(TipoTerreno.ROBOT.getType()), x, y, posXY.getX(), posXY.getY());
	}
	
	public Caminho getPath(Map map, Mover mover, int selectedx, int selectedy, int x, int y){
		RastreadorCaminho finder = new RastreadorCaminho(map, 500, true);
		Caminho caminho = finder.findPath(mover, selectedx, selectedy, x, y);
		
		if (caminho != null)
			caminhosPercorridos.add(caminho.getLastStep());
		
		return caminho;
	}
	
	public ArrayList<Passo> getPathsTraveled() throws RemoteException {
		return caminhosPercorridos;
	}
	
	
}
