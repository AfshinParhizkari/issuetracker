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

import ag.pinguin.issuetracker.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface StoryDao extends JpaRepository<Story,String>,IssueDao<Story>{
	Page<Story> findByStatusNotContains(Pageable pageable,StoryStatus status);
	List<Story> findByEstimatedpoint(Integer estimatedPointValue);
	List<Story> findByStatusNotContains(StoryStatus status);
	long countByStatusNotContains(StoryStatus status);

	@Query(value = "SELECT devid as assignedev,"+
	"(SELECT IFNULL(count(assignedev) ,0) from story where devid=assignedev and  status <> 'Completed' ) as count "+
	"FROM developer", nativeQuery = true)
	ArrayList<Object[]> getCountOfDeveloperTasks();

	@Query(value = "SELECT devid as assignedev,"+
			"(SELECT IFNULL(sum(estimatedpoint) ,0) from story where devid=assignedev and  status <> 'Completed' ) as capacity "+
			"FROM developer", nativeQuery = true)
	ArrayList<Object[]> getSumOfDeveloperEPV();

}