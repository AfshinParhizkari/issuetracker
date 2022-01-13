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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Developer {
    private Integer devid;
    private String devname;

    @Id
    @Column(name = "devid")
    @NotNull(message = "Developer ID is required")
    public Integer getDevid() {return devid;}
    public void setDevid(Integer devid) {this.devid = devid;}

    @Column(name = "devname")
    @NotEmpty(message = "Developer name is required")
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
        return Objects.equals(devid, developer.devid) && Objects.equals(devname, developer.devname);
    }
    @Override
    public int hashCode() {return Objects.hash(devid, devname);}

    @Override
    public String toString() {
        return "Developer{" +
                "devid=" + devid +
                ", devname='" + devname + '\'' +
                '}';
    }
}
