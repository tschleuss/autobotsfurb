package rmi.interfaces;

import client.CliAutobotsRPC;



public interface Map {

	int getWidthInTiles();
	int getHeightInTiles();
	void pathFinderVisited(int x, int y);
	int[][] getTerrain();
	int getTerrain(int x, int y);
	int[][] getUnits();
	boolean[][] getVisited();
	boolean blocked(Mover mover, int x, int y);
	float getCost(Mover mover, int sx, int sy, int tx, int ty);
	CliAutobotsRPC getClienteRPC();
}
