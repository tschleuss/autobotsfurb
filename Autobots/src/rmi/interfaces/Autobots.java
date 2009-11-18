package rmi.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.enumeration.TipoTerreno;
import com.structs.Caminho;
import com.structs.GameMap;
import com.structs.Passo;


public interface Autobots extends Remote {
	
   public Caminho getFasterWay(TipoTerreno type, Map map, int x, int y)  throws RemoteException;
   public Caminho getPath(Map map, Mover mover, int selectedx, int selectedy, int x, int y)  throws RemoteException;
   public List<Passo> getPathsTraveled()  throws RemoteException;
   
}
