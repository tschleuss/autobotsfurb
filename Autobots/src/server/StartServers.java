package server;
import rpc.portMapper.pmapsvc;


public class StartServers {

	
	//	INICIANDO SERVIDOR RPC
	//	1 - src/rpc/portMapper/pmapsvc.java
	//	2 - src/server/SrvAutobotRPC.java
	//
	//	INICIANDO SERVIDOR RMI
	//	3 - src/rmi/cmd/registry.bat
	//	4 - src/server/SrvAutobotRMI.java
	//
	//  EXECUTAR CORBA
	//  5 - src/corba/cmd/orb.bat
	//  6 - src/server/SrvAutobotCorba.java

	public static void main(String[] args) {
		
		try {
			
			new Thread() {
				@Override
				public void run() {
					try {
						new pmapsvc().main(new String[] {});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			
			new Thread(){
				@Override
				public void run() {
					try {
						new SrvAutobotRPC().main( new String[]{} );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			
			new Thread(){
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec("rmiregistry");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			
			new Thread(){
				@Override
				public void run() {
					try {
						new SrvAutobotRMI().main( new String[]{} );	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			
			new Thread(){
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec("orbd -ORBInitialPort 2000");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			
			new Thread(){
				@Override
				public void run() {
					try {
						new SrvAutobotCorba().main( new String[]{} );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
