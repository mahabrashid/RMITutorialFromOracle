package engine;

import java.rmi.RemoteException;

import compute.Compute;
import compute.Task;

public class ComputeEngine
    implements Compute {

    @Override
    public <T> T executeTask(Task<T> t)
        throws RemoteException {
        System.out.println("got compute task: " + t);
        return t.execute();
    }
}
