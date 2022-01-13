package ag.pinguin.issuetracker.entity;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 13
 * @Time 10:01 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IssueDTO {
    public Integer assignedev;
    public Integer count;

}