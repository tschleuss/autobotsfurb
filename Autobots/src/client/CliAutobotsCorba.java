package client;

import org.omg.CORBA.ORB;
import org.omg.CORBA.StringHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import com.structs.Caminho;

import corba.structs.autobotsa.autobots;
import corba.structs.autobotsa.autobotsHelper;
import corba.structs.autobotsa.boxAndGoalConfig;

public class CliAutobotsCorba {

	private autobots autobot;
	private String serverhost;
	
	public CliAutobotsCorba(String serverhost){
		
	    try {
	    	
	    	this.serverhost = serverhost;
	    	
	    	String[] args = {"-ORBInitialPort", "2000","-ORBInitialHost",serverhost};
	    	
	        // Cria e inicializa o ORB
	        ORB orb = ORB.init(args, null);

	        // Obtem referencia para o servico de nomes
	        org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
	        NamingContextExt namecontextRef = NamingContextExtHelper.narrow(objRef);
	   
	        // Obtem referencia para o servidor
	        String name = "SrvAutobotsCorba";
	        this.autobot = autobotsHelper.narrow(namecontextRef.resolve_str(name));

	      } catch (Exception e) {
	          System.out.println("ERROR : " + e) ;
	          e.printStackTrace(System.out);
	      }	
	}
	
	public boxAndGoalConfig getBoxPosition(String map, String x, String y){
		
		short botPosX = new Short(x).shortValue();
		short botPosY = new Short(y).shortValue();
		
		return autobot.getBoxPosition(map, botPosX, botPosY);
	}
	
	public Caminho getPathToBox(String x, String y, String boxX, String boxY){
		
		short botPosX = new Short(x).shortValue();
		short botPosY = new Short(y).shortValue();
		short boxPosX = new Short(boxX).shortValue();
		short boxPosY = new Short(boxY).shortValue();		
		
		StringHolder ret = new StringHolder();
		
		autobot.getPathToBox(serverhost, botPosX, botPosY, boxPosX, boxPosY, ret);

		String steps[] = ret.value.split(";");
		String step[];
		
		Caminho c = new Caminho();
		
		for (String XY : steps) {
			step = XY.split(",");
			c.appendStep(Integer.parseInt(step[0]), Integer.parseInt(step[1]));
		}
		
		return c;
	}	
	
}
