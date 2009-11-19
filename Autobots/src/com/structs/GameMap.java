package com.structs;

import java.io.Serializable;

import com.enumeration.TipoTerreno;

import netbula.ORPC.rpc_err;
import rmi.interfaces.Mover;
import rmi.interfaces.Map;
import rpc.structs.botPosition;
import server.heuristica.UnitMover;
import client.CliAutobotsRPC;

public class GameMap implements Map, Serializable {

	public static final int WIDTH = 30;

	public static final int HEIGHT = 30;
	

	private int[][] terrain = new int[WIDTH][HEIGHT];

	private int[][] units = new int[WIDTH][HEIGHT];

	private boolean[][] visited = new boolean[WIDTH][HEIGHT];
	
	private CliAutobotsRPC autobotsRPC_cln;

	private botPosition bp;
	
	public GameMap(String serverHost) {
		
		this.autobotsRPC_cln = new CliAutobotsRPC(serverHost);
		
		loadMap();
		loadBotPosition();
		
	}

	//RMI
	//{
		private void loadBotPosition() {
			
			try {
				this.bp = this.autobotsRPC_cln.getBot();
				units[this.bp.y][this.bp.x] = TipoTerreno.ROBOT.getType();
			} catch (rpc_err e) {
				e.printStackTrace();
			}
		}
	
		private void loadMap() {
		
			try {
				this.terrain = this.autobotsRPC_cln.getMap();
			} catch (rpc_err e) {
				e.printStackTrace();
			}
		}
		
		public CliAutobotsRPC getClienteRPC(){
			return this.autobotsRPC_cln;
		}		
	//}
		
	public botPosition getBotInitialPosition(){
		return this.bp;
	}

	public void clearVisited() {
		for (int x=0;x<getWidthInTiles();x++) {
			for (int y=0;y<getHeightInTiles();y++) {
				visited[x][y] = false;
			}
		}
	}

	public boolean visited(int x, int y) {
		return visited[x][y];
	}

	public int getTerrain(int x, int y) {
		//inverte x e y
		return terrain[y][x];
	}

	public int getUnit(int x, int y) {
		return units[x][y];
	}
	public void setUnit(int x, int y, int unit) {
		units[x][y] = unit;
	}
	
	public boolean blocked(Mover mover, int x, int y) {

		if (getUnit(x,y) != 0) {
			return true;
		}
		
		int unit = ((UnitMover) mover).getType();
		
		if (unit == TipoTerreno.ROBOT.getType()) {
			return getTerrain(x,y) != TipoTerreno.GRASS.getType();
		}

		return true;
	}

	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		return 1;
	}

	public int getHeightInTiles() {
		return HEIGHT;
	}

	public int getWidthInTiles() {
		return WIDTH;
	}

	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}

	public int[][] getTerrain() {
		return terrain;
	}

	public int[][] getUnits() {
		return units;
	}

	public boolean[][] getVisited() {
		return visited;
	}
}
