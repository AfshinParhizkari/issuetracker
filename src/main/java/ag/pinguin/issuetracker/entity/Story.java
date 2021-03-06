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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity//subclass
@Table(name = "Story")
public class Story extends Issue {
    private Integer estimatedpoint;
    private String status;
    private Integer sprint;

    @Column(name = "estimatedpoint")
    @NotNull(message = "estimatedpoint is required")
    //@Max(value = 10,message = "estimatedpoint between 0=Low to 10=High")
    public Integer getEstimatedpoint() {
        return estimatedpoint;
    }
    public void setEstimatedpoint(Integer estimatedpoint) {
        this.estimatedpoint = estimatedpoint;
    }

    @Column(name = "status")
    //@NotEmpty(message = "status is required")
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "sprint")
    public Integer getSprint() {return sprint;}
    public void setSprint(Integer sprint) {this.sprint = sprint;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Story story = (Story) o;
        return Objects.equals(estimatedpoint, story.estimatedpoint) && Objects.equals(status, story.status) && Objects.equals(sprint, story.sprint);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), estimatedpoint, status, sprint);
    }

    @Override
    public String toString() {
        return "Story{" +
                "estimatedpoint=" + estimatedpoint +
                ", status='" + status + '\'' +
                ", sprint=" + sprint +
                '}'+super.toString();
    }
}