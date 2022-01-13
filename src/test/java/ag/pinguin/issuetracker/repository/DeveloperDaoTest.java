package ag.pinguin.issuetracker.repository;

import ag.pinguin.issuetracker.IssuetrackerApplication;
import ag.pinguin.issuetracker.entity.Developer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static org.junit.Assert.*;

/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 10:53 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={IssuetrackerApplication.class})
@ContextConfiguration(classes={Developer.class, DeveloperDao.class})
@ActiveProfiles("test")
public class DeveloperDaoTest {
    @Autowired Developer developer;
    @Autowired DeveloperDao dao;

    @Test
    public void findAll() {
        List<Developer> developers= dao.findAll();
        developers.forEach(System.out::println);
    }

    @Test
    public void countDevelopers() {
        System.out.println(dao.count());
    }
}