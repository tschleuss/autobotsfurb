package server.heuristica;
import rmi.interfaces.Mover;
import rmi.interfaces.Map;
import server.interfaces.HeuristicaRastreio;


public class HeuristicaManhattan implements HeuristicaRastreio {
	private int minimumCost;
	
	public HeuristicaManhattan(int minimumCost) {
		this.minimumCost = minimumCost;
	}
	
	public float getCost(Map map, Mover mover, int x, int y, int tx,
			int ty) {
		return minimumCost * (Math.abs(x-tx) + Math.abs(y-ty));
	}

}
