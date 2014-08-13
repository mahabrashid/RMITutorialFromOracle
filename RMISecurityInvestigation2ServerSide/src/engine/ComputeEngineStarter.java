package engine;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import compute.Compute;

public class ComputeEngineStarter extends UnicastRemoteObject {

    protected ComputeEngineStarter() throws RemoteException {
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		/*
		 * When an RMI Sever and Client applications are run within the same machine (local
		 * machine), there's no need to use the CodeBase feature. Only the Server application needs
		 * prior knowledge about any Client object(s) that would be passed into it through a Remote
		 * Interface's method parameter(s), 
		 * for example: in this instance the Client's Pi object is
		 * passed into the Server side through 'executeTask(Task<T> t)' method of the Remote
		 * Interface 'Compute'. This can be achieved by putting Pi.class in the Server application's
		 * classpath, there's no need to use CodeBase feature.
		 */
    	
		/*
		 * When the required classes can't be made available on the server application's classpath,
		 * then use CodeBase feature as below:
		 */
		/*
    	System.out.println("useCodebaseOnly before: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    	System.out.println("useCodebaseOnly after: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        
    	System.out.println("Before setting codebase: " + System.getProperty("java.rmi.server.codebase"));
//    	System.setProperty("java.rmi.server.codebase", "http://stuweb.cms.gre.ac.uk/~rm950/RMIFiles/"); //for classes made available from a web server through http.
//    	System.setProperty("java.rmi.server.codebase", Compute.class.getProtectionDomain().getCodeSource().toString()); //don't need this at all as compute.jar is on the classpath anyway.
		System.setProperty("java.rmi.server.codebase",
				"file:../RMISecurityInvestigation2ClientSide/bin/"); // Pi.class and all other required
																		// classes on the Client are
																		// to be available to the
																		// Server application on
																		// start unless this is
																		// availed on the classpath
																		// through configuration or
																		// command line parameter.
    	System.out.println("After setting codebase: " + System.getProperty("java.rmi.server.codebase"));
    	*/
    	
    	System.setProperty("java.security.policy", "security/server.policy");
        
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
            String name = "Compute";
            Compute engine = new ComputeEngine();

            Compute stub =
                (Compute) UnicastRemoteObject.exportObject(engine, 0);
            System.out.println("Server side stub: "+ stub.toString());
            
            Registry registry = LocateRegistry.createRegistry(1099);
//        	Registry registry = LocateRegistry.getRegistry();
            System.out.println("Registry: " + registry.toString());

            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}
