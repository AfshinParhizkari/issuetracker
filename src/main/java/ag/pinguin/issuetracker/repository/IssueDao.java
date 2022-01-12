package ag.pinguin.issuetracker.repository;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 1:40 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import java.sql.Timestamp;
import java.util.List;

public interface IssueDao extends JpaRepository<Issue, Integer>{
	Issue findByIssueid(Integer issueID);
	List<Issue> findByAssignedev(String assigneDeveloper);
	List<Issue> findByIssuetype(Integer issueType);
	List<Issue> findByTitleLike(String title);
	List<Issue> findByCreationdateBetween(Timestamp fromDate,Timestamp toDate);
}