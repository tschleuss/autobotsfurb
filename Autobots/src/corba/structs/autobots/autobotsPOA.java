package corba.structs.autobots;


/**
* autobots/autobotsPOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from corba.structs.autobots.idl
* Quinta-feira, 19 de Novembro de 2009 00h09min50s BRST
*/

public abstract class autobotsPOA extends org.omg.PortableServer.Servant
 implements corba.structs.autobots.autobotsOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getBoxPosition", new java.lang.Integer (0));
    _methods.put ("getPathToBox", new java.lang.Integer (1));
    _methods.put ("getPathToTarget", new java.lang.Integer (2));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // autobots/autobots/getBoxPosition
       {
         String map = in.read_string ();
         short botPosX = in.read_short ();
         short botPosY = in.read_short ();
         corba.structs.autobots.boxAndGoalConfig $result = null;
         $result = this.getBoxPosition (map, botPosX, botPosY);
         out = $rh.createReply();
         corba.structs.autobots.boxAndGoalConfigHelper.write (out, $result);
         break;
       }

       case 1:  // autobots/autobots/getPathToBox
       {
         String serverhost = in.read_string ();
         short botPosX = in.read_short ();
         short botPosY = in.read_short ();
         short boxPosX = in.read_short ();
         short boxPosY = in.read_short ();
         org.omg.CORBA.StringHolder ret = new org.omg.CORBA.StringHolder ();
         this.getPathToBox (serverhost, botPosX, botPosY, boxPosX, boxPosY, ret);
         out = $rh.createReply();
         out.write_string (ret.value);
         break;
       }

       case 2:  // autobots/autobots/getPathToTarget
       {
         String serverhost = in.read_string ();
         short botPosX = in.read_short ();
         short botPosY = in.read_short ();
         short targetPosX = in.read_short ();
         short targetPosY = in.read_short ();
         org.omg.CORBA.StringHolder ret = new org.omg.CORBA.StringHolder ();
         this.getPathToTarget (serverhost, botPosX, botPosY, targetPosX, targetPosY, ret);
         out = $rh.createReply();
         out.write_string (ret.value);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:autobots/autobots:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public autobots _this() 
  {
    return autobotsHelper.narrow(
    super._this_object());
  }

  public autobots _this(org.omg.CORBA.ORB orb) 
  {
    return autobotsHelper.narrow(
    super._this_object(orb));
  }


} // class autobotsPOA
