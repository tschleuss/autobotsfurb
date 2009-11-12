package corba.structs;

import autobotsPOA;

class autobotsImpl extends autobotsPOA {  

  public boolean soma_int (short p1, short p2, org.omg.CORBA.ShortHolder ret) {
	  ret.value = new Integer(p1 + p2).shortValue();
	  System.out.println("Executada Soma");
	  return true;
  };

}