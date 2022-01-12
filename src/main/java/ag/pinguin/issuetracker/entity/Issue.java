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
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;
@Entity
public class Issue {
    private Integer issueid;
    private Integer issuetype;
    private String title;
    private String description;
    private Timestamp creationdate;
    private String assignedev;
    private Bug bugByIssueid;
    private Developer developerByAssignedev;
    private Story storyByIssueid;

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
    @Column(name = "issuetype")
    @NotNull(message = "issuetype is required")
    @Max(value = 1,message = "issuetype is 0=story or 1=bug") // must be an integer value lower than or equal to the number in the value element.
    public Integer getIssuetype() {
        return issuetype;
    }
    public void setIssuetype(Integer issuetype) {
        this.issuetype = issuetype;
    }

    @Basic
    @Column(name = "title")
    @NotEmpty(message = "Developer name is required")
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "creationdate")
    @CreationTimestamp
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Tehran")
    public Timestamp getCreationdate() {
        return creationdate;
    }
    public void setCreationdate(Timestamp creationdate) {
        this.creationdate = creationdate;
    }

    @Basic
    @Column(name = "assignedev")
    public String getAssignedev() {
        return assignedev;
    }
    public void setAssignedev(String assignedev) {
        this.assignedev = assignedev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(issueid, issue.issueid) && Objects.equals(issuetype, issue.issuetype) && Objects.equals(title, issue.title) && Objects.equals(description, issue.description) && Objects.equals(creationdate, issue.creationdate) && Objects.equals(assignedev, issue.assignedev);
    }
    @Override
    public int hashCode() {
        return Objects.hash(issueid, issuetype, title, description, creationdate, assignedev);
    }

    @OneToOne(mappedBy = "issueByIssueid")
    @JsonIgnore
    public Bug getBugByIssueid() {
        return bugByIssueid;
    }
    public void setBugByIssueid(Bug bugByIssueid) {
        this.bugByIssueid = bugByIssueid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignedev", referencedColumnName = "devname",insertable = false,updatable = false)
    @JsonIgnore
    public Developer getDeveloperByAssignedev() {
        return developerByAssignedev;
    }
    public void setDeveloperByAssignedev(Developer developerByAssignedev) {
        this.developerByAssignedev = developerByAssignedev;
    }

    @OneToOne(mappedBy = "issueByIssueid")
    public Story getStoryByIssueid() {
        return storyByIssueid;
    }
    public void setStoryByIssueid(Story storyByIssueid) {
        this.storyByIssueid = storyByIssueid;
    }
}
