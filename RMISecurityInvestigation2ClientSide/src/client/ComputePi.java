package client;

import java.math.BigDecimal;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import compute.Compute;

public class ComputePi {
	
    public static void main(String args[]) {
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
    	
    	/* The Client application should not need to use CodeBase feature at all, only the Server would. */
    	/* 
    	System.out.println("useCodebaseOnly before: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    	System.out.println("useCodebaseOnly after: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        
    	System.out.println("Before setting codebase: " + System.getProperty("java.rmi.server.codebase"));
    	System.setProperty("java.rmi.server.codebase", Pi.class.getProtectionDomain().getCodeSource().getLocation().toString());
    	System.out.println("After setting codebase: " + System.getProperty("java.rmi.server.codebase"));
    	*/
    	
    	System.setProperty("java.security.policy", "security/clientAllPermission.policy");
        
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("Registry: " + registry.toString());
            Compute comp = (Compute) registry.lookup(name);
			
			Pi task = new Pi(50); // task is a client's Pi object which implements Task, hence also
									// an instance of Task object.
			BigDecimal pi = comp.executeTask(task); // client's Pi object in Task wrapper is passed
													// to the Server side through executeTask. Since
													// the Pi object is also a Task object (as it
													// implements Task interface) which the
													// server has knowledge of, the server happily
													// accepts the passed Pi object. However, when
													// it comes to invoking the execute() method on
													// the passed
													// Task object which is actually implemented in
													// the Pi class on the client side, the Server
													// application also demands to have knowledge
													// about Pi. This knowledge can be provided to
													// the server in 2 ways:
													//
													// 1. (When both Server and Client applications
													// are local) By making the Pi.class
													// available on the Server application's
													// classpath.
													//
													// 2. (When the applications are in remote
													// machines) By putting the class and any other
													// necessary jar(s) in a web server accessible
													// via http and referring to that location in
													// the Server application's 'CodeBase' feature.
													// For local applications, we can also apply the
													// same logic or have a dedicated local
													// directory
													// holding all such classes and jars and
													// referring to that directory in the Server
													// applications's CodeBase feature
			System.out.println(pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }    
}

