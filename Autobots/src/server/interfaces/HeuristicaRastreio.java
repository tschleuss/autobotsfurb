package server.interfaces;
import rmi.interfaces.Mover;
import rmi.interfaces.Map;


public interface HeuristicaRastreio {

	public float getCost(Map map, Mover mover, int x, int y, int tx, int ty);
}

