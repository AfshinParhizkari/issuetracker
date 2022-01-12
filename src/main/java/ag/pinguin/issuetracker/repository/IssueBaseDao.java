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

public interface IssueBaseDao<T extends Issue> extends JpaRepository<T, Integer>{
	T findByIssueid(Integer issueID);
	List<T> findByAssignedev(String assigneDeveloper);
	List<T> findByIssuetype(Integer issueType);
	List<T> findByTitleLike(String title);
	List<T> findByCreationdateBetween(Timestamp fromDate,Timestamp toDate);
}