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
import ag.pinguin.issuetracker.config.BasicData;
import ag.pinguin.issuetracker.entity.IssueDTO;
import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.entity.StoryStatus;
import ag.pinguin.issuetracker.repository.DeveloperDao;
import ag.pinguin.issuetracker.repository.StoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class PlanningSrv {
    @Autowired private StoryDao dao;
    @Autowired private DeveloperDao devDao;

    public List<Story> planCurrentSprint() throws Exception{
        List<Story> stories =dao.findByStatusNotContains(StoryStatus.Completed);
        //how much is the developer load(EPV) for this week? example Andre=5 ehsan=4 hossein=0 afshin=6 , ...
        List<IssueDTO> devCapacities= PlanningSrv.convertArray2IssueDTO(dao.getSumOfDeveloperEPV());
        IssueDTO firstDev;
        //Assign the tasks for current sprint to developer with same load. condition: count of task for each dev<=capacity(epv sum)
        for(int i=0;i< stories.size();i++){ //All not complete tasks
            Story story =stories.get(i);//select one
            if(story.getAssignedev()==null){ //have assigned it before?
                firstDev = Collections.min(devCapacities, Comparator.comparing(s -> s.getCapacity()));//chose developer with minimum capacity(EPV)
                if(firstDev.getCapacity()+stories.get(i).getEstimatedpoint()<=BasicData.capacity) {//what about the capacity of developer(EPV)? less than 10?
                    stories.get(i).setAssignedev(firstDev.getAssignedev());//Assigned
                    devCapacities.get(devCapacities.indexOf(firstDev)).setCapacity(firstDev.getCapacity()+stories.get(i).getEstimatedpoint());//decrease dev capacity(EPV)
                }
            }
        }
        dao.saveAll(stories);
        return stories;
    }

    public List<Story> planOldWay() throws Exception{
        //how many task we can do for this time box(max<=capacity*developerCount) and select the tasks
        int developerCount=(int)devDao.count();
        Pageable currentWeekTasks =  PageRequest.of(0, BasicData.capacity*developerCount, Sort.by("creationdate").ascending());
        List<Story> stories =dao.findByStatusNotContains(currentWeekTasks,StoryStatus.Completed).getContent();//stories.size()<=capacity*developerCount
        //how much is the developer load for this week? example Andre has 3 tasks, Afshin has 0, ...
        List<IssueDTO> devCapacities= PlanningSrv.convertArray2IssueDTO(dao.getCountOfDeveloperTasks());
        IssueDTO firstDev =  new IssueDTO();
        //Assign the tasks for current week to developer with same load. condition: count of task for each dev<=capacity
        for(int i=0;i< stories.size();i++){//All not complete tasks: max<=capacity*developerCount
            Story story =stories.get(i);//select one
            if(story.getAssignedev()==null){//have assigned it before?
                firstDev = Collections.min(devCapacities, Comparator.comparing(s -> s.getCapacity()));//choose freest developer
                if(firstDev.getCapacity()<BasicData.capacity) {//what about the capacity of developer? less than 10?
                    stories.get(i).setAssignedev(firstDev.getAssignedev());//Assigned
                    devCapacities.get(devCapacities.indexOf(firstDev)).setCapacity(firstDev.getCapacity()+1);//decrease dev capacity
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
}