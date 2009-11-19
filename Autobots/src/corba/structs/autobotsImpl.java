package corba.structs;

import server.heuristica.RastreadorCaminho;
import server.heuristica.UnitMover;


import com.enumeration.TipoTerreno;
import com.structs.Caminho;
import com.structs.GameMap;
import com.structs.Passo;

import corba.structs.autobots.autobotsPOA;
import corba.structs.autobots.boxAndGoalConfig;

public class autobotsImpl extends autobotsPOA {  

	private String serverhost;
	private int botPosX, botPosY,posX,posY;
	
	public boxAndGoalConfig getBoxPosition( String caminho, short botPosX , short botPosY) {
	
		int boxPosX, boxPosY;
		int[] xy = new int[2];
		
		boxAndGoalConfig bAg = new boxAndGoalConfig();
		String map[] = caminho.split("\n");
		
		//POSICAO DA CAIXA
		xy = getClearXY(map);
		
		while(xy[0] == botPosX && xy[1] == botPosY){
			xy = getClearXY(map);
		}
		
		boxPosX = xy[0];
		boxPosY = xy[1];
		
		//POSICAO DO TARGET
		xy = getClearXY(map);
		
		while((xy[0] == botPosX && xy[1] == botPosY) || (xy[0] == boxPosX && xy[1] == boxPosY)){
			xy = getClearXY(map);
		}
		
		bAg.boxPosX =  new Integer(boxPosX).shortValue();
		bAg.boxPosY =  new Integer(boxPosY).shortValue();
		bAg.goalPosX = new Integer(xy[0]).shortValue();
		bAg.goalPosY = new Integer(xy[1]).shortValue();
		
		return bAg;
	};
	
	private int[] getClearXY(String map[]){
		
		String mapLine;
		int randomX,randomY,randomPos;
		int[] xy = new int[2];
		boolean validpos = false;
		
		while(!validpos){
			
			randomX = (int)(Math.random() * map.length);
			mapLine = map[randomX].replaceAll("\\s", "");
			randomY = (int)(Math.random() * mapLine.length());
			
			randomPos = Integer.parseInt(Character.toString(mapLine.charAt(randomY)));
			
			if(randomPos == TipoTerreno.GRASS.getType()){
				xy[0] = randomX;
				xy[1] = randomY;
				validpos = true;
			}
		}		
		
		return xy;
	}

	public void getPathToBox(String serverhost, short botPosX,short botPosY, short boxPosX, short boxPosY, org.omg.CORBA.StringHolder ret) {
		
		this.serverhost = serverhost;
		this.botPosX = botPosX;
		this.botPosY = botPosY;
		this.posX = boxPosX;
		this.posY = boxPosY;
		
		ret.value =  getStepsString( true );
		
	}

	public void getPathToTarget(String serverhost, short botPosX,short botPosY, short targetPosX, short targetPosY,org.omg.CORBA.StringHolder ret) {
		
		this.serverhost = serverhost;
		this.botPosX = botPosX;
		this.botPosY = botPosY;
		this.posX = targetPosX;
		this.posY = targetPosY;		
		
		ret.value =  getStepsString( false );
	}
	
	private String getStepsString( boolean allowDiagMovement ){
		
		Caminho c = getPath( allowDiagMovement );
		String steps = "";
		
		for (Passo p : c.getSteps()) {
			steps += p.getX() + "," + p.getY() + ";";
		}
		
		return steps;
	}
	
	private Caminho getPath( boolean allowDiagMovement ){
		
		GameMap map = new GameMap(this.serverhost);
		
		RastreadorCaminho finder = new RastreadorCaminho(map, 500, true);
		
		//inverte X e Y do destino
		return finder.findPath( new UnitMover(TipoTerreno.ROBOT.getType()), this.botPosX, this.botPosY, this.posY, this.posX, allowDiagMovement);		
		
	}
	
}