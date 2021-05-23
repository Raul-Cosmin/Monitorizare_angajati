package monitoring.service;

import monitoring.domain.Task;
import monitoring.domain.Team;
import monitoring.domain.TeamDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IMonitoringEmployeeObserver extends Remote {
    void notifyNewTeamIsCreated(List<TeamDTO> teamList) throws RemoteException;
    void notifyNewTaskIsAssigned(List<Task> taskList) throws RemoteException;
}
