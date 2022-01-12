package ag.pinguin.issuetracker.entity;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 1:17 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity//subclass
@PrimaryKeyJoinColumn(name = "issueid")
public class Bug extends Issue {
    private Integer issueid;
    private String priority;
    private String status;
    private Issue issueByIssueid;

    @Id
    @Column(name = "issueid")
    @NotNull(message = "issueid is required")
    public Integer getIssueid() {
        return issueid;
    }
    public void setIssueid(Integer issueid) {
        this.issueid = issueid;
    }

    @Basic
    @Column(name = "priority")
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "status")
    @NotEmpty(message = "status is required")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bug bug = (Bug) o;
        return Objects.equals(issueid, bug.issueid) && Objects.equals(priority, bug.priority) && Objects.equals(status, bug.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(issueid, priority, status);
    }

    @OneToOne
    @JoinColumn(name = "issueid", referencedColumnName = "issueid", nullable = false)
    public Issue getIssueByIssueid() {
        return issueByIssueid;
    }
    public void setIssueByIssueid(Issue issueByIssueid) {
        this.issueByIssueid = issueByIssueid;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "issueid=" + issueid +
                ", priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                ", issueByIssueid=" + issueByIssueid +
                '}';
    }
}
