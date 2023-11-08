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
import java.sql.Timestamp;
import java.util.List;

public interface IssueDao<T extends Issue>{
	T findByIssueid(String issueID);
	List<T> findByAssignedev(String assigneDeveloper);
	List<T> findByTitleLike(String title);
	List<T> findByCreationdateBetween(Timestamp fromDate,Timestamp toDate);
}