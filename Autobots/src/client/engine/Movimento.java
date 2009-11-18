package client.engine;

import com.structs.Caminho;
import com.structs.GameMap;
import com.structs.Passo;

import client.engine.view.GameView;

public class Movimento extends Thread {
	
	private Caminho path =null;
	private GameView gameView = null;
	private GameMap map = null;
	private int X=0;
	private int Y=0;
	
	public Movimento(GameMap map, Caminho path, GameView gameView, int X, int Y){
		this.path = path;
		this.gameView = gameView;
		this.map = map;
		this.Y = Y;
		this.X = X;
	}
	

	public void run(){
		
		this.gameView.repaint(0);

		for(Passo step : path.getSteps()){
			path.appenStepOld(X, Y);
			int unit = map.getUnit(X, Y);
			map.setUnit(X, Y, 0);
			map.setUnit(step.getX(),step.getY(),unit);

			X = step.getX();
			Y = step.getY();

			try{
			Thread.sleep(250);
			}
			catch (Exception err) {}

			this.gameView.repaint(0);
		}
	}
	
	public int getX() {
		return X;
	}
	public int getY() {
		return Y;
	}
}
