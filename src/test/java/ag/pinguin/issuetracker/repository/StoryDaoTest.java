package ag.pinguin.issuetracker.repository;

import ag.pinguin.issuetracker.IssuetrackerApplication;
import ag.pinguin.issuetracker.entity.Story;
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

/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 13
 * @Time 3:41 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={IssuetrackerApplication.class})
@ContextConfiguration(classes={Story.class, StoryDao.class})
@ActiveProfiles("test")
public class StoryDaoTest {
    @Autowired Story story;
    @Autowired StoryDao dao;
    String issueID="727e2463-f682-4389-97d2-f7e852feafce";

    @Test
    public void findAll() {
        List<Story> storyList= dao.findAll();
        storyList.forEach(System.out::println);
    }

    @Test
    public void findByIssueID() {
        Story story= dao.findByIssueid(issueID);
        System.out.println(story);
    }
    @Test
    public void deleteByIssueID() {
        dao.deleteById(issueID);
    }
    @Test
    public void insertByIssueID() {
        story.setIssueid(UUID.randomUUID().toString());
        story.setTitle("add security");
        story.setDescription("use jjwt for Authentication and spring.sec for Authorization");
        story.setEstimatedpoint(5);
        story.setStatus("New");
        story=dao.save(story);
        System.out.println("Story is added: " +story);
    }
    @Test
    public void updateByIssueID() {
        story=dao.findByIssueid(issueID);
        System.out.println("Story: " +story);
        story.setTitle("use SSIS for ETL");
        story.setDescription("transfer data from h2 to mysql for EDW");
        story.setEstimatedpoint(2);
        story.setStatus("Estimated");
        dao.save(story);
        System.out.println("Story is updated: " +story);
    }
}