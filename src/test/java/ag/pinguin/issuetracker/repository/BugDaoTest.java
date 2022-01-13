package ag.pinguin.issuetracker.repository;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 10:21 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.IssuetrackerApplication;
import ag.pinguin.issuetracker.entity.Bug;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={IssuetrackerApplication.class})
@ContextConfiguration(classes={Bug.class, BugDao.class})
@ActiveProfiles("test")
public class BugDaoTest {
    @Autowired Bug bug;
    @Autowired BugDao dao;
    String issueID="6b24ba48-52cd-4e1f-a2d7-beba1d7f456f";

    @Test
    public void findAll() {
        List<Bug> bugList= dao.findAll();
        bugList.forEach(System.out::println);
    }

    @Test
    public void findByIssueID() {
        Bug bug= dao.findByIssueid(issueID);
        System.out.println(bug);
    }
    @Test
    public void deleteByIssueID() {
        dao.deleteById(issueID);
    }
    @Test
    public void insertByIssueID() {
        bug.setIssueid(UUID.randomUUID().toString());
        bug.setTitle("ui interface");
        bug.setDescription("responsive does not working in mobile devices");
        bug.setPriority("Minor");
        bug.setStatus("New");
        bug=dao.save(bug);
        System.out.println("bug is added: " +bug);
    }
    @Test
    public void updateByIssueID() {
        bug=dao.findByIssueid(issueID);
        System.out.println("bug: " +bug);
        bug.setTitle("h2 db doesn't persist data");
        bug.setDescription("we have data lost when service restart");
        bug.setPriority("Major");
        bug.setStatus("Verified");
        dao.save(bug);
        System.out.println("bug is updated: " +bug);
    }

    @Test
    public void assignBug2Dev() {
        bug=dao.findByIssueid(issueID);
        System.out.println("developer for this bug is: " +bug.getAssignedev());
        bug.setAssignedev(4);
        dao.save(bug);
        System.out.println("developer for this bug is: " +bug.getAssignedev()+". "+bug.getDeveloperByAssignedev().getDevname());
    }
}