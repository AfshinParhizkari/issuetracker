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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.sql.Timestamp;
import java.util.Objects;

@MappedSuperclass
public class Issue {
    private Integer issueid;
    private String title;
    private String description;
    private Timestamp creationdate;
    private Integer assignedev;
    private Developer developerByAssignedev;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) //specify the generation strategy used for the primary key.
    @Column(name = "issueid")
    //@NotNull(message = "issueid is required")
    public Integer getIssueid() {
        return issueid;
    }
    public void setIssueid(Integer issueid) {
        this.issueid = issueid;
    }

    @Column(name = "title")
    @NotEmpty(message = "Developer name is required")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "creationdate")
    @CreationTimestamp
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Tehran")
    public Timestamp getCreationdate() {
        return creationdate;
    }
    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    @Column(name = "assignedev")
    public Integer getAssignedev() {return assignedev;}
    public void setAssignedev(Integer assignedev) {this.assignedev = assignedev;}

    @ManyToOne
    @JoinColumn(name = "assignedev", referencedColumnName = "devid",insertable = false,updatable = false)
    @JsonIgnore
    public Developer getDeveloperByAssignedev() {
        return developerByAssignedev;
    }
    public void setDeveloperByAssignedev(Developer developerByAssignedev) {
        this.developerByAssignedev = developerByAssignedev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(issueid, issue.issueid) && Objects.equals(title, issue.title) && Objects.equals(description, issue.description) && Objects.equals(creationdate, issue.creationdate) && Objects.equals(assignedev, issue.assignedev) && Objects.equals(developerByAssignedev, issue.developerByAssignedev);
    }
    @Override
    public int hashCode() {
        return Objects.hash(issueid, title, description, creationdate, assignedev, developerByAssignedev);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "issueid=" + issueid +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationdate=" + creationdate +
                ", assignedev=" + assignedev +
                '}';
    }
}