package monitoring.service;

import monitoring.domain.Employee;
import monitoring.domain.Task;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IMonitoringEmployerObserver extends Remote {
    void notifyEmployeeChangeStatus(List<Employee> employees) throws RemoteException;
    void notifyChangeTaskStatus(List<Task> tasks) throws  RemoteException;
}
