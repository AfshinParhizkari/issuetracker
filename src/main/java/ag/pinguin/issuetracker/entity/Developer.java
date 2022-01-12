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
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Developer {
    private String devname;
    private Collection<Issue> issuesByDevname;

    @Id
    @Column(name = "devname")
    //@NotEmpty(message = "Developer name is required")
    public String getDevname() {
        return devname;
    }
    public void setDevname(String devname) {
        this.devname = devname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return Objects.equals(devname, developer.devname);
    }
    @Override
    public int hashCode() {
        return Objects.hash(devname);
    }

    @OneToMany(mappedBy = "developerByAssignedev")
    @JsonIgnore
    public Collection<Issue> getIssuesByIddeveloper() {
        return issuesByDevname;
    }
    public void setIssuesByIddeveloper(Collection<Issue> issuesByIddeveloper) {
        this.issuesByDevname = issuesByIddeveloper;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "devname='" + devname + '\'' +
                '}';
    }
}
