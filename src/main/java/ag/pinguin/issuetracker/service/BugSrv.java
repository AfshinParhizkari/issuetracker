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
import ag.pinguin.issuetracker.entity.Bug;
import ag.pinguin.issuetracker.entity.BugStatus;
import ag.pinguin.issuetracker.entity.Developer;
import ag.pinguin.issuetracker.repository.BugDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BugSrv {
    @Autowired private BugDao dao;

    public List<Bug> findBugs(String issueID) throws Exception {
        List<Bug> bugs=new ArrayList<>();
        if(issueID.isEmpty()) bugs = (dao.findAll());
        else   bugs.add(dao.findByIssueid(issueID));;
        return bugs;
    }

    public Bug findBug(String issueID) throws Exception {
        if(issueID==null) issueID="";
        return dao.findById(issueID).orElse(new Bug());
    }

    public void deleteBug(String issueID) throws Exception {
        dao.deleteById(issueID);
    }

    public Bug upsertBug(Bug bug) throws Exception {
        return dao.save(bug);
    }

    public Bug assignBug(Bug bug, Developer developer) throws Exception {
        bug.setAssignedev(developer.getDevid());
        bug=dao.save(bug);
        return bug;
    }

    public Bug changeStatus(Bug bug, BugStatus status) throws Exception {
        bug.setStatus(status.toString());
        bug=dao.save(bug);
        return bug;
    }
}