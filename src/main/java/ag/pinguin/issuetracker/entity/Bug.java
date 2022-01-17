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

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "bug")
public class Bug extends Issue {
    private String priority;
    private String status;

    @Column(name = "priority")
    public String getPriority() {
        return priority;
    }
    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Column(name = "status")
    //@NotEmpty(message = "status is required")
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
        return Objects.equals(priority, bug.priority) && Objects.equals(status, bug.status);
    }
    @Override
    public int hashCode() {
        return Objects.hash(priority, status);
    }

    @Override
    public String toString() {
        return "Bug{" +
                "priority='" + priority + '\'' +
                ", status='" + status + '\'' +
                '}'+super.toString();
    }
}