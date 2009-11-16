package corba.structs;

import org.omg.CORBA.ShortHolder;

import com.enumeration.TipoTerreno;

import corba.structs.autobots.autobotsPOA;

public class autobotsImpl extends autobotsPOA {  

	public boolean getBoxPosition(String caminho, short botPosX, short botPosY,
			ShortHolder boxPosX, ShortHolder boxPosY) {
		
		String map[] = caminho.split("\n");
		
		int randomX,randomY,randomPos;
		String mapLine;
		boolean validpos = false;
		
		while(!validpos){
			
			randomX = (int)(Math.random() * 30);
			mapLine = map[randomX].replaceAll("\\s", "");
			randomY = (int)(Math.random() * 30);
			
			randomPos = Integer.parseInt(Character.toString(mapLine.charAt(randomY)));
			
			if(randomPos == TipoTerreno.GRASS.getType()){
				
				boxPosX.value = new Integer(randomX).shortValue();
				boxPosY.value = new Integer(randomY).shortValue();
				return true;
			}
		}
		return false;
	};

}