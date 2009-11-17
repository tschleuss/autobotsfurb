package corba.structs;

import org.omg.CORBA.ShortHolder;

import com.enumeration.TipoTerreno;

import corba.structs.autobots.autobotsPOA;
import corba.structs.autobots.boxAndGoalConfig;

public class autobotsImpl extends autobotsPOA {  

	/*
	public boolean getBoxPosition(String caminho, short botPosX, short botPosY,
			ShortHolder boxPosX, ShortHolder boxPosY) {
		
		String map[] = caminho.split("\n");
		
		int randomX,randomY,randomPos;
		String mapLine;
		boolean validpos = false;
		
		while(!validpos){
			
			randomX = (int)(Math.random() * map.length);
			mapLine = map[randomX].replaceAll("\\s", "");
			randomY = (int)(Math.random() * mapLine.length());
			
			randomPos = Integer.parseInt(Character.toString(mapLine.charAt(randomY)));
			
			if(randomPos == TipoTerreno.GRASS.getType()){
				
				boxPosX.value = new Integer(randomX).shortValue();
				boxPosY.value = new Integer(randomY).shortValue();
				return true;
			}
		}
		return false;
	}
	*/

	public boxAndGoalConfig getBoxPosition(String caminho, short botPosX,
			short botPosY) {
	
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

}