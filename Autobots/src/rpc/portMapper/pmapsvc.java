package rpc.portMapper;

import netbula.ORPC.*;

//Portmapper in Java, using the Pmapsvc class in netbula.ORPC

public class pmapsvc {
      public static void main(String argv[]) throws rpc_err{
           System.out.println("Servidor Pmapsvc em execução");
           new Pmapsvc().run();
           System.out.println("Servidor Pmapsvc encerrado");
           
      }
}
