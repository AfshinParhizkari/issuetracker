package ag.pinguin.issuetracker.repository;

import ag.pinguin.issuetracker.IssuetrackerApplication;
import ag.pinguin.issuetracker.entity.IssueDTO;
import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.entity.StoryStatus;
import ag.pinguin.issuetracker.service.PlanningSrv;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

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
    @Autowired private Story story;
    @Autowired private StoryDao dao;
    @Autowired private DeveloperDao devDao;
    final static int capacity=10;
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

    @Test
    public void findFreestDeveloper() {
        List<IssueDTO> devCapacities= PlanningSrv.convertArray2IssueDTO(dao.getCountOfDeveloperTasks());
        devCapacities.forEach(System.out::println);

        IssueDTO issueDTO =  Collections.min(devCapacities, Comparator.comparing(s -> s.getCapacity()));
        System.out.println("The freest developer is : " + issueDTO);
    }

    @Test
    public void printNotAssignedStoryForThisWeek() {
        int developerCount=(int)devDao.count();
        Pageable currentWeekTasks =  PageRequest.of(0, capacity*developerCount, Sort.by("creationdate").descending());
        Page<Story> stories =dao.findByStatusNotContains(currentWeekTasks,StoryStatus.Completed);
        System.out.println("count of unassigned tasks: "+ dao.countByStatusNotContains(StoryStatus.Completed));
        System.out.println("count of developers: "+ capacity*developerCount);
        stories.forEach(System.out::println);
    }

}