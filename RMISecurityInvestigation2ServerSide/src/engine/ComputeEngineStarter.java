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
    	System.out.println("useCodebaseOnly before: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    	System.out.println("useCodebaseOnly after: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        
    	System.out.println("Before setting codebase: " + System.getProperty("java.rmi.server.codebase"));
    	System.setProperty("java.rmi.server.codebase", Compute.class.getProtectionDomain().getCodeSource().toString());
    	System.out.println("After setting codebase: " + System.getProperty("java.rmi.server.codebase"));
    	
    	System.out.println("policy before:" + System.getProperty("java.security.policy"));
    	System.setProperty("java.security.policy", "security/server.policy");
        System.out.println("policy after:" + System.getProperty("java.security.policy"));
        
        System.out.println("Security manger before: " + System.getSecurityManager());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security manger after: " + System.getSecurityManager());
        
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
//        	Registry registry = LocateRegistry.getRegistry();
            System.out.println("Registry: " + registry.toString());
            
            String name = "Compute";
            Compute engine = new ComputeEngine();
            Compute stub =
                (Compute) UnicastRemoteObject.exportObject(engine, 0);
            System.out.println(stub.toString());
            registry.rebind(name, stub);
            System.out.println("ComputeEngine bound");
        } catch (Exception e) {
            System.err.println("ComputeEngine exception:");
            e.printStackTrace();
        }
    }
}
