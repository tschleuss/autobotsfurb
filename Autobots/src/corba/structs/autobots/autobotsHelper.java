package corba.structs.autobots;


/**
* autobots/autobotsHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from corba.structs.autobots.idl
* Segunda-feira, 16 de Novembro de 2009 19h49min14s BRST
*/

abstract public class autobotsHelper
{
  private static String  _id = "IDL:autobots/autobots:1.0";

  public static void insert (org.omg.CORBA.Any a, corba.structs.autobots.autobots that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static corba.structs.autobots.autobots extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (corba.structs.autobots.autobotsHelper.id (), "autobots");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static corba.structs.autobots.autobots read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_autobotsStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, corba.structs.autobots.autobots value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static corba.structs.autobots.autobots narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.structs.autobots.autobots)
      return (corba.structs.autobots.autobots)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.structs.autobots._autobotsStub stub = new corba.structs.autobots._autobotsStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static corba.structs.autobots.autobots unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof corba.structs.autobots.autobots)
      return (corba.structs.autobots.autobots)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      corba.structs.autobots._autobotsStub stub = new corba.structs.autobots._autobotsStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
