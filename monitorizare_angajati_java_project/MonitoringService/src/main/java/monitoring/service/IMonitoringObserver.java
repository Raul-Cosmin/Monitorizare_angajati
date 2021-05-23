package monitoring.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMonitoringObserver extends Remote {
    void hello() throws RemoteException;
}
