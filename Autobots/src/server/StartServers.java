package server;
import java.util.ArrayList;
import java.util.List;

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
		
		List<Thread> list = new ArrayList<Thread>();
		
		try {
			
			Thread t1 = new Thread() {
				@Override
				public void run() {
					try {
						new pmapsvc().main(new String[] {});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread t2 = new Thread(){
				@Override
				public void run() {
					try {
						new SrvAutobotRPC().main( new String[]{} );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread t3 = new Thread(){
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec("rmiregistry");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread t4 = new Thread(){
				@Override
				public void run() {
					try {
						new SrvAutobotRMI().main( new String[]{} );	
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread t5 = new Thread(){
				@Override
				public void run() {
					try {
						Runtime.getRuntime().exec("orbd -ORBInitialPort 2000");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			
			Thread t6 = new Thread(){
				@Override
				public void run() {
					try {
						new SrvAutobotCorba().main( new String[]{} );
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};

			list.add(t1);
			list.add(t2);
			list.add(t3);
			list.add(t4);
			list.add(t5);
			list.add(t6);
			
			synchronized (list) {
				for( Thread t : list ) {
					t.start();
					Thread.sleep(100);
				}
			}

			System.err.println("[SERVIDORES INICIADOS COM SUCESSO !]");
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
