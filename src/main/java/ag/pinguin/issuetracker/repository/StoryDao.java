package ag.pinguin.issuetracker.repository;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 1:30 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */

import ag.pinguin.issuetracker.entity.Bug;
import ag.pinguin.issuetracker.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface StoryDao extends JpaRepository<Story,String>,IssueDao<Story>{
	List<Story> findByEstimatedpoint(Integer estimatedPointValue);
	List<Story> findByStatus(String status);
}