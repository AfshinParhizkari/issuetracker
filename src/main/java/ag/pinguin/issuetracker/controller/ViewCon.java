package ag.pinguin.issuetracker.controller;

import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.repository.StoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 14
 * @Time 11:24 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
@Controller
@RequestMapping("/api")
public class ViewCon {
    @Autowired private StoryDao dao;

    @RequestMapping("/stories")
    public String redirectToStoriesPage() {
        return "stories";
    }
    @RequestMapping("/story")
    public String redirectToStoryPage(@RequestParam String issueid, ModelMap modelMap) {
        Optional<Story> opt= dao.findById(issueid);
        Story story=opt.orElse(new Story());
        modelMap.addAttribute("story",story);
        return "story";
    }

}
