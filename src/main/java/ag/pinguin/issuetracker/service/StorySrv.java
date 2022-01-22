package ag.pinguin.issuetracker.service;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 21
 * @Time 11:17 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */

import ag.pinguin.issuetracker.entity.IssueDTO;
import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.entity.StoryStatus;
import ag.pinguin.issuetracker.repository.StoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StorySrv {
    @Autowired private StoryDao dao;

    public List<Story> findStories(String issueID) throws Exception {
        List<Story> stories=new ArrayList<>();
        if(issueID.isEmpty()) stories = (dao.findAll());
        else   stories.add(dao.findByIssueid(issueID));;
        return stories;
    }

    public Story findStory(String issueID) throws Exception {
        if(issueID==null) issueID="";
        return dao.findById(issueID).orElse(new Story());
    }

    public void deleteStory(String issueID) throws Exception {
        dao.deleteById(issueID);
    }

    public Story upsertStory(Story story) throws Exception {
        return dao.save(story);
    }

    public Story changeStatus(Story story, StoryStatus status) throws Exception {
        story.setStatus(status.toString());
        story=dao.save(story);
        return story;
    }

    public List<IssueDTO> calculateDeveloperLoad(Integer sprintNum) throws Exception {
        return PlanningSrv.convertArray2IssueDTO(dao.getSumOfDeveloperEPV(sprintNum));
    }

}