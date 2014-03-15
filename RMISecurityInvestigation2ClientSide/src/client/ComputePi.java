package client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.math.BigDecimal;

import compute.Compute;

public class ComputePi {
	
    public static void main(String args[]) {
    	/*Following will cause client to throw ClassNotFoundException
    	 * System.out.println("useCodebaseOnly before: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        System.setProperty("java.rmi.server.useCodebaseOnly", "false");
    	System.out.println("useCodebaseOnly after: " + System.getProperty("java.rmi.server.useCodebaseOnly"));
        */
    	System.out.println("Before setting codebase: " + System.getProperty("java.rmi.server.codebase"));
    	System.setProperty("java.rmi.server.codebase", Pi.class.getProtectionDomain().getCodeSource().getLocation().toString());
    	System.out.println("After setting codebase: " + System.getProperty("java.rmi.server.codebase"));
    	
    	System.out.println("policy before:" + System.getProperty("java.security.policy"));
    	System.setProperty("java.security.policy", "security/clientAllPermission.policy");
        System.out.println("policy after:" + System.getProperty("java.security.policy"));
        
        System.out.println("Security manger before: " + System.getSecurityManager());
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        System.out.println("Security manger after: " + System.getSecurityManager());
        
        try {
            String name = "Compute";
            Registry registry = LocateRegistry.getRegistry();
            System.out.println("Registry: " + registry.toString());
            Compute comp = (Compute) registry.lookup(name);
            Pi task = new Pi(50);
            BigDecimal pi = comp.executeTask(task);
            System.out.println(pi);
        } catch (Exception e) {
            System.err.println("ComputePi exception:");
            e.printStackTrace();
        }
    }    
}

