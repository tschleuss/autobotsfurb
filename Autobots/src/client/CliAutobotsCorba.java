package client;

import org.omg.CORBA.ORB;
import org.omg.CORBA.ShortHolder;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import corba.structs.autobots.autobots;
import corba.structs.autobots.autobotsHelper;
import corba.structs.autobots.boxAndGoalConfig;

public class CliAutobotsCorba {

	private autobots autobot;
	
	public CliAutobotsCorba(String serverHost){
		
	    try {
	    	String[] args = {"-ORBInitialPort", "2000","-ORBInitialHost",serverHost};
	    	
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
	
}
