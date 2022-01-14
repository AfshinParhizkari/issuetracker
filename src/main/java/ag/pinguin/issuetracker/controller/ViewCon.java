package ag.pinguin.issuetracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @RequestMapping("/stories")
    public String redirectToStoriesPage() {
        return "stories";
    }
}
