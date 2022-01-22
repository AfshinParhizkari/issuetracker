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

    public List<Story> planAllSprints() throws Exception{
        Integer sprintNum=dao.getMaxSprint();
        //OrderByEstimatedpoint: priority based on small EPV stories
        List<Story> stories =dao.findByStatusNotContainsOrderByEstimatedpoint(StoryStatus.Completed);
        //how much is developer's load(EPV) for this sprint? example Andre=5 ehsan=4 hossein=0 afshin=7 , ...
        List<IssueDTO> devCapacities= PlanningSrv.convertArray2IssueDTO(dao.getSumOfDeveloperEPV(sprintNum));
        IssueDTO freestDev;
        //Assign the stories to current sprint to developer with same load. condition: sum(EPV) for each dev<=10
        for(int i=0;i< stories.size();i++){ //All but not completed tasks
            Story story =stories.get(i);//select smallest EPV story
            if(story.getAssignedev()==null){ //have assigned it before?
                freestDev = Collections.min(devCapacities, Comparator.comparing(s -> s.getCapacity()));//chose developer with minimum capacity(EPV)
                if(freestDev.getCapacity()+stories.get(i).getEstimatedpoint()<=BasicData.capacity) {//what about the capacity of freest developer(EPV)? less than 10?
                    stories.get(i).setAssignedev(freestDev.getAssignedev());//Assigned Developer
                    stories.get(i).setSprint(sprintNum);//Assigned sprint
                    devCapacities.get(devCapacities.indexOf(freestDev)).setCapacity(freestDev.getCapacity()+stories.get(i).getEstimatedpoint());//decrease dev capacity(EPV)
                }
                else{//Open new sprint and reset capacity
                    sprintNum++;
                    devCapacities= PlanningSrv.convertArray2IssueDTO(dao.getSumOfDeveloperEPV(sprintNum));//all capacities reset to zero
                    freestDev = Collections.min(devCapacities, Comparator.comparing(s -> s.getCapacity()));
                    stories.get(i).setAssignedev(freestDev.getAssignedev());//Assigned Developer
                    stories.get(i).setSprint(sprintNum);//Assigned sprint
                    devCapacities.get(devCapacities.indexOf(freestDev)).setCapacity(freestDev.getCapacity()+stories.get(i).getEstimatedpoint());//decrease dev capacity(EPV)
                }
            }
        }
        stories= dao.saveAll(stories);
        return stories;
    }
    //Just Current week based on capacity. other stories are EPIC
    public List<Story> planCurrentSprint() throws Exception{
        //OrderByEstimatedpoint: priority based on small EPV stories
        List<Story> stories =dao.findByStatusNotContainsOrderByEstimatedpoint(StoryStatus.Completed);
        //how much is developer's load(EPV) for this sprint? example Andre=5 ehsan=4 hossein=0 afshin=7 , ...
        List<IssueDTO> devCapacities= PlanningSrv.convertArray2IssueDTO(dao.getSumOfDeveloperEPV());
        IssueDTO freestDev;
        //Assign the stories to current sprint to developer with same load. condition: sum(EPV) for each dev<=10
        for(int i=0;i< stories.size();i++){ //All but not completed tasks
            Story story =stories.get(i);//select smallest EPV story
            if(story.getAssignedev()==null){ //have assigned it before?
                freestDev = Collections.min(devCapacities, Comparator.comparing(s -> s.getCapacity()));//chose developer with minimum capacity(EPV)
                if(freestDev.getCapacity()+stories.get(i).getEstimatedpoint()<=BasicData.capacity) {//what about the capacity of developer(EPV)? less than 10?
                    stories.get(i).setAssignedev(freestDev.getAssignedev());//Assigned
                    devCapacities.get(devCapacities.indexOf(freestDev)).setCapacity(freestDev.getCapacity()+stories.get(i).getEstimatedpoint());//decrease dev capacity(EPV)
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
        IssueDTO freestDev =  new IssueDTO();
        //Assign the tasks for current week to developer with same load. condition: count of task for each dev<=capacity
        for(int i=0;i< stories.size();i++){//All not complete tasks: max<=capacity*developerCount
            Story story =stories.get(i);//select one
            if(story.getAssignedev()==null){//have assigned it before?
                freestDev = Collections.min(devCapacities, Comparator.comparing(s -> s.getCapacity()));//choose freest developer
                if(freestDev.getCapacity()<BasicData.capacity) {//what about the capacity of developer? less than 10?
                    stories.get(i).setAssignedev(freestDev.getAssignedev());//Assigned
                    devCapacities.get(devCapacities.indexOf(freestDev)).setCapacity(freestDev.getCapacity()+1);//decrease dev capacity
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