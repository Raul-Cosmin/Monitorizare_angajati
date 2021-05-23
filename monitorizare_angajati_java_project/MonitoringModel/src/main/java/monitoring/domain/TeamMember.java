package monitoring.domain;


public class TeamMember extends Entity<Integer>{
    private int idTeam;
    private int idEmployee;
    private MemberRole memberRole;

    public TeamMember(){}

    public TeamMember(Integer integer, int idTeam, int idEmployee, MemberRole teamRole) {
        super(integer);
        this.idTeam = idTeam;
        this.idEmployee = idEmployee;
        this.memberRole = teamRole;
    }
    public TeamMember(int idTeam, int idEmployee, MemberRole teamRole) {
        super(0);
        this.idTeam = idTeam;
        this.idEmployee = idEmployee;
        this.memberRole = teamRole;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public MemberRole getMemberRole() {
        return memberRole;
    }

    public void setMemberRole(MemberRole memberRole) {
        this.memberRole = memberRole;
    }
}
