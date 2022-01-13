package ag.pinguin.issuetracker.service;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 13
 * @Time 7:32 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.entity.IssueDTO;
import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.entity.StoryStatus;
import ag.pinguin.issuetracker.repository.DeveloperDao;
import ag.pinguin.issuetracker.repository.StoryDao;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PlanStory {
    @Autowired private StoryDao dao;
    @Autowired private DeveloperDao devDao;
    final static int capacity=10;

    public List<Story> planStory() throws Exception{
        //how many task we can do for this time box(max<=capacity*developerCount) and select the tasks
        int developerCount=(int)devDao.count();
        Pageable currentWeekTasks =  PageRequest.of(0, capacity*developerCount, Sort.by("creationdate").descending());
        List<Story> stories =dao.findByStatusNotContains(currentWeekTasks,StoryStatus.Completed).getContent();
        //what is the developer load? example dev1 has 3 tasks
        List<IssueDTO> devCapacities=PlanStory.convertArray2IssueDTO(dao.getCountOfDeveloperTasks());
        IssueDTO frestDev =  new IssueDTO();
        //Assign the tasks for current week
        for(int i=0;i< stories.size();i++){
            Story story =stories.get(i);
            if(story.getAssignedev()==null){
                frestDev = Collections.min(devCapacities, Comparator.comparing(s -> s.getCount()));
                if(frestDev.getAssignedev()<capacity) {
                    stories.get(i).setAssignedev(frestDev.getAssignedev());
                    devCapacities.get(devCapacities.indexOf(frestDev)).setCount(frestDev.getCount()+1);
                }
            }
        }
        dao.saveAll(stories);
        return stories;
    }

    public static List<IssueDTO> convertArray2IssueDTO(ArrayList<Object[]> source){
        try{
            List<IssueDTO> destination = new ArrayList<>();
            for(int i=0;i<source.size();i++)
                destination.add(new IssueDTO((Integer)source.get(i)[0], ((BigInteger)source.get(i)[1]).intValue()));
            return destination;
        }catch (Exception e) {
            return null;
        }
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> generalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getRootCause(ex).getMessage());
    }

}
