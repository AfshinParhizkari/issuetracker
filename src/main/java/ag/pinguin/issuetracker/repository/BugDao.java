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
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface BugDao extends JpaRepository<Bug, Integer>,IssueDao<Bug> {
	List<Bug> findByPriority(String priority);
	List<Bug> findByStatus(String status);

}