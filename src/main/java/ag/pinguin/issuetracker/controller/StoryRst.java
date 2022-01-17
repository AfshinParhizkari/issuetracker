package ag.pinguin.issuetracker.controller;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 13
 * @Time 4:33 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.config.BasicData;
import ag.pinguin.issuetracker.entity.BugStatus;
import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.entity.StoryStatus;
import ag.pinguin.issuetracker.repository.StoryDao;
import ag.pinguin.issuetracker.service.PlanStory;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rst/story")
@Tag(name = "story",description = "story info can show and change via this rest service")
public class StoryRst {
    @Autowired private StoryDao dao;
    @Autowired private PlanStory srv;

    @Operation(summary = "Get a story by issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for get story(s)",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "All storys",
                                    value = "{\"issueid\": \"\"}",
                                    summary = "show all storys"),
                            @ExampleObject(
                                    name = "One story",
                                    value = "{\"issueid\":\"727e2463-f682-4389-97d2-f7e852feafce\"}",
                                    summary = "show a story") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the story", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Story.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid story", content = @Content),
            @ApiResponse(responseCode = "404", description = "story not found", content = @Content) })
    @PostMapping(value = "/find" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String findStory(@RequestBody Story story) throws Exception {
        List<Story> returnData=new ArrayList<>();
        if(story.getIssueid().isEmpty()) {
            returnData = (dao.findAll());
        }else
            returnData.add(dao.findByIssueid(story.getIssueid()));
        return (new ObjectMapper()).writeValueAsString(returnData);
    }

    @Operation(summary = "Delete a story")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for delete a story",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "delete story",
                                    value = "{\"issueid\":\"396eaf1b-1f11-45bd-9a0f-2107edd3112b\"}",
                                    summary = "delete story") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "story is deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid story", content = @Content),
            @ApiResponse(responseCode = "404", description = "story not found", content = @Content) })
    @DeleteMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteStory(@RequestBody String receivedData) throws Exception {
        JSONObject json = new JSONObject(receivedData);
        String storyID=json.optString("issueid","");
        dao.deleteById(storyID);
    }

    @Operation(summary = "create or update a story by issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for create new story",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "create new story",
                                    value = "{\"title\":\"add security\"," +
                                            "\"description\":\"transfer data from h2 to mysql for EDW\"," +
                                            "\"estimatedpoint\":0," +
                                            "\"status\":\"New\"" +
                                            "}",
                                    summary = "create story"),
                            @ExampleObject(
                                    name = "update story",
                                    value = "{\"issueid\":\"727e2463-f682-4389-97d2-f7e852feafce\"," +
                                            "\"title\":\"use SSIS for ETL\"," +
                                            "\"description\":\"we have data lost when service restart\"," +
                                            "\"estimatedpoint\":3," +
                                            "\"status\":\"Verified\"" +
                                            "}",
                                    summary = "update story") }))
            @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is created"),
            @ApiResponse(responseCode = "400", description = "Invalid story name", content = @Content) })
    @PutMapping(value = "/save" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String upsertStory(@Valid @RequestBody Story story) throws Exception {
        String message="Error";
        Story dbstory= dao.findByIssueid(story.getIssueid());
        if(dbstory==null) {
            story.setIssueid(UUID.randomUUID().toString());
            story.setStatus(StoryStatus.New.toString());
            story=dao.save(story);
            if(story==null)
                message= "{\"message\":\"story is Not added. some problem is occurred\"}";
            else
                message= "{\"message\":\"new story is added\"}";
        }else {
            if(dbstory.getStatus().equals(StoryStatus.Completed)) message=  "{\"message\":\"Story is Completed and closed\"}";
            else{
                dbstory.setTitle(story.getTitle());
                dbstory.setDescription(story.getDescription());
                dbstory.setEstimatedpoint(story.getEstimatedpoint());
                story=dao.save(dbstory);
                message= "{\"message\":\"story is updated\"}";
            }
        }
        return message;
    }

    @Operation(summary = "Plan all unassigned stories(max:10*Count(dev) for a week) to developers")
    @GetMapping(value = "/plan" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String planStory() throws Exception {
        return (new ObjectMapper()).writeValueAsString(srv.planStory());
    }

    @Operation(summary = "Change status to New, Estimated or Completed by issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for commit a change status",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "change status New",
                                    value = "{\"issueid\":\"aba0dba2-5b69-4f22-bbe9-d715402f0974\"," +
                                            "\"status\":\"Completed\"" +
                                            "}",
                                    summary = "change status New"),
                            @ExampleObject(
                                    name = "change status Resolved",
                                    value = "{\"issueid\":\"727e2463-f682-4389-97d2-f7e852feafce\"," +
                                            "\"status\":\"New\"" +
                                            "}",
                                    summary = "Can not change Completed status") }))
    @PostMapping(value = "/changestatus" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeStatus(@RequestBody String receivedData) throws Exception {
        String returnData="";
        JSONObject json = new JSONObject(receivedData);
        String bugID=json.optString("issueid","");
        String status=json.optString("status","");
        Story story = dao.findByIssueid(bugID);
        if(status.isEmpty() || story.getStatus().equals("Completed"))
            returnData="{\"message\":\""+ BugStatus.Verified+" status can't change. the task is closed\"}";
        else {
            story.setStatus(status);
            story=dao.save(story);
            returnData=(new ObjectMapper()).writeValueAsString(story);
        }
        return returnData;
    }

    @Operation(summary = "Set Capacity for developers default is 10 stories per each developer ")
    @GetMapping(value = "/setcapacity")
    public Integer setCapacity(@RequestParam(required = false) Integer capacity) throws Exception {
        if(capacity==null) capacity=10;
        BasicData.capacity=capacity;
        return BasicData.capacity;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> generalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getRootCause(ex).getMessage());
    }
}
