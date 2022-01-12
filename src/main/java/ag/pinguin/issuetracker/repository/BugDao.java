package ag.pinguin.issuetracker.repository;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 1:45 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */

import ag.pinguin.issuetracker.entity.Bug;
import java.util.List;

public interface BugDao extends IssueBaseDao<Bug>{
	Bug findByIssueid(Integer issueID);
	List<Bug> findByPriority(String priority);
	List<Bug> findByStatus(String status);
}