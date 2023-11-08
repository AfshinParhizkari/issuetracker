package ag.pinguin.issuetracker.repository;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 1:35 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DeveloperDao extends JpaRepository<Developer, Integer>{
	List<Developer> findByDevid(Integer developerID);
	List<Developer> findByDevname(String developerName);
	List<Developer> findByDevnameLike(String developerName);
}