package monitoring.domain;

import java.util.ArrayList;
import java.util.List;

public class Team extends Entity<Integer>{

    private String teamName;
    private List<Task> receivedTasks = new ArrayList<>();
    private List<TeamMember> members = new ArrayList<>();
    private Integer noMembers;



    public Team(){}

    public Team(String teamName,Integer noMembers){
        super(0);
        this.teamName = teamName;
        this.noMembers = noMembers;
    }
    public Team(Integer integer, String teamName, Integer noMembers) {
        super(integer);
        this.teamName = teamName;
        this.noMembers = noMembers;

    }


    public Integer getNoMembers() {
        return noMembers;
    }

    public void setNoMembers(Integer noMembers) {
        this.noMembers = noMembers;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public List<Task> getReceivedTasks() {
        return receivedTasks;
    }

    public void setReceivedTasks(List<Task> receivedTasks) {
        this.receivedTasks = receivedTasks;
    }
}
