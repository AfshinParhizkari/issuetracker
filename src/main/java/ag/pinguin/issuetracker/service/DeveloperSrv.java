package ag.pinguin.issuetracker.service;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 21
 * @Time 6:54 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.entity.Developer;
import ag.pinguin.issuetracker.repository.DeveloperDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DeveloperSrv {
    @Autowired private DeveloperDao dao;

    public List<Developer> findDevelopers(Integer developerID) throws Exception {
        List<Developer> returnData;
        if(developerID==null) returnData = (dao.findAll());
        else   returnData = (dao.findByDevid(developerID));
        return returnData;
    }

    public Developer findDeveloper(Integer developerID) throws Exception {
        return dao.findById(developerID).orElse(new Developer());
    }

    public void deleteDeveloper(Integer developerID) throws Exception {
            dao.deleteById(developerID);
    }

    public Developer upsertDeveloper(Developer developer) throws Exception {
        return dao.save(developer);
    }
}