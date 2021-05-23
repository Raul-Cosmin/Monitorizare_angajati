package monitoring.domain;

public class TeamDTO extends Entity<Integer>{
    private String teamName;
    private MemberRole memberRole;

    public TeamDTO(Integer integer, String teamName, MemberRole memberRole) {
        super(integer);
        this.teamName = teamName;
        this.memberRole = memberRole;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public MemberRole getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }
}
