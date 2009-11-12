package server.heuristica;
import rmi.interfaces.Mover;
import rmi.interfaces.Map;
import server.interfaces.HeuristicaRastreio;


public class HeuristicaMenorCustoVizinho implements HeuristicaRastreio {

	public float getCost(Map map, Mover mover, int x, int y, int tx, int ty) {		
		float dx = tx - x;
		float dy = ty - y;
		
		return ((dx*dx)+(dy*dy));
	}

}
