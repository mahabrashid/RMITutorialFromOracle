package engine;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;

import compute.Compute;
import compute.Task;

public class ComputeEngine implements Compute {

	@Override
	public <T> T executeTask(Task<T> t) throws RemoteException {
		try {
			System.out.println("Server side received a compute task from client machine: " + RemoteServer.getClientHost());
		} catch (ServerNotActiveException e) {
			e.printStackTrace();
		}
		System.out
				.println("The task is being carried out on server machine: " + getIPAddress());
		return t.execute();
	}

	private String getIPAddress() {
		String ipAddrss = null;
		try {
			ipAddrss = Inet4Address.getLocalHost().toString();
//			System.out.println("method one, IP: " + Inet4Address.getLocalHost().getHostAddress());
//			System.out.println("method two, IP: " + Inet4Address.getLocalHost().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ipAddrss;
	}
}
