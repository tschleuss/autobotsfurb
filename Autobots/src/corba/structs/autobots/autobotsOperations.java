package corba.structs.autobots;


/**
* autobots/autobotsOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from corba.structs.autobots.idl
* Quarta-feira, 18 de Novembro de 2009 00h18min47s BRST
*/

public interface autobotsOperations 
{
  corba.structs.autobots.boxAndGoalConfig getBoxPosition (String map, short botPosX, short botPosY);
  void getPathToBox (String serverhost, short botPosX, short botPosY, short boxPosX, short boxPosY, org.omg.CORBA.StringHolder ret);
  void getPathToTarget (String serverhost, short botPosX, short botPosY, short targetPosX, short targetPosY, org.omg.CORBA.StringHolder ret);
} // interface autobotsOperations
