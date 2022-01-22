package ag.pinguin.issuetracker.controller;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 13
 * @Time 3:57 PM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.config.BasicData;
import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.entity.StoryStatus;
import ag.pinguin.issuetracker.service.PlanningSrv;
import ag.pinguin.issuetracker.service.StorySrv;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/rst/stories")
@Tag(name = "story",description = "This service can show story information and change it")
public class StoryRst {
    @Autowired private StorySrv srv;
    @Autowired private PlanningSrv planningSrv;

    @Operation(summary = "Set the issueID field to Get a story or don't set to show all stories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the story(s)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Story.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @Parameter(name = "issueID",description = "A string identifier", example = "727e2463-f682-4389-97d2-f7e852feafce")
    @GetMapping(value = "/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String findStories(@RequestParam(required = false,defaultValue = "") String issueID) throws Exception {
        return (new ObjectMapper()).writeValueAsString(srv.findStories(issueID));
    }

    @Operation(summary = "Set the issueID field to Delete a story")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "story is deleted"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @Parameter(name = "issueID",description = "A string identifier", example = "396eaf1b-1f11-45bd-9a0f-2107edd3112b")
    @DeleteMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteStory(@RequestParam String issueID) throws Exception {
        srv.deleteStory(issueID);
        return String.format("{\"message\":\"issueID %s is deleted\"}",issueID);
    }

    @Operation(summary = "Change status to New, Verified or Resolved via issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for commit a change status",
            required = true,
            content = @Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "Correct. status:{New,Estimated,Completed}",
                                    value = "{\"issueid\":\"aba0dba2-5b69-4f22-bbe9-d715402f0974\"," +
                                            "\"status\":\"Completed\"" +
                                            "}",
                                    summary ="Change status to New"),
                            @ExampleObject(
                                    name = "Incorrect. status:{New,Estimated,Completed}",
                                    value = "{\"issueid\":\"727e2463-f682-4389-97d2-f7e852feafce\"," +
                                            "\"status\":\"New\"" +
                                            "}",
                                    summary = "Can not change Resolved status") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PutMapping(value = "/status" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeStatus(@RequestBody String receivedData) throws Exception {
        String message="";
        JSONObject json = new JSONObject(receivedData);
        String storyID=json.optString("issueid","");
        StoryStatus status=StoryStatus.valueOf(json.optString("status",""));
        Story story = srv.findStory(storyID);
        if(story==null || status==null || story.getIssueid()==null || story.getStatus().equals(StoryStatus.Completed.toString()))
            message= "{\"message\":\"story or status is not found or status was Resolved\"}";
        else
            message = (new ObjectMapper()).writeValueAsString(srv.changeStatus(story,status));
        return message;
    }

    @Operation(summary = "create a new story")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for create new story",
            required = true,
            content = @Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "status:{New,Estimated,Completed}",
                                    value = "{\"title\":\"add security\"," +
                                            "\"description\":\"transfer data from h2 to mysql for EDW\"," +
                                            "\"estimatedpoint\":2" +
                                            "}",
                                    summary = "Create a new story") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is created"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PostMapping(value = "/" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveStory(@Valid @RequestBody Story story) throws Exception {
        String message;
        Story dbstory= srv.findStory(story.getIssueid());
        if(dbstory==null || dbstory.getIssueid()==null) {
            story.setIssueid(UUID.randomUUID().toString());
            story.setStatus(StoryStatus.New.toString());
            story=srv.upsertStory(story);
            if(story==null) message= "{\"message\":\"story is not added. some problem is occurred.\"}";
            else message= (new ObjectMapper()).writeValueAsString(story);
        }else message= String.format("{\"message\":\"issueID %s is already exist.\"}",story.getIssueid());
        return message;
    }

    @Operation(summary = "Update a story via issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for update a story",
            required = true,
            content = @Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "status:{New,Estimated,Completed}",
                                    value = "{\"issueid\":\"396eaf1b-1f11-45bd-9a0f-2107edd3112b\"," +
                                            "\"title\":\"use SSIS for ETL\"," +
                                            "\"description\":\"we have data lost when service restart\"," +
                                            "\"estimatedpoint\":3" +
                                            "}",
                                    summary = "update a story via issueID") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is updated"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PutMapping(value = "/" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateStory(@Valid @RequestBody Story story) throws Exception {
        String message;
        Story dbstory= srv.findStory(story.getIssueid());
        if(dbstory==null || dbstory.getIssueid()==null || dbstory.getStatus().equals(StoryStatus.Completed.toString()))
            message=String.format("{\"message\":\"issueID %s is not found to update Or Status was completed.\"}",story.getIssueid());
        else {
            dbstory.setTitle(story.getTitle());
            dbstory.setDescription(story.getDescription());
            dbstory.setEstimatedpoint(story.getEstimatedpoint());
            story=srv.upsertStory(dbstory);
            message= (new ObjectMapper()).writeValueAsString(story);
        }
        return message;
    }

    @Operation(summary = "Set Capacity for developers default is 10 story points per each developer ")
    @Parameter(name = "capacity",description = "capacity of developer", example = "10")
    @GetMapping(value = "/capacity")
    public Integer setCapacity(@RequestParam(required = false,defaultValue = "10") Integer capacity) throws Exception {
        BasicData.capacity=capacity;
        return BasicData.capacity;
    }

    @Operation(summary = "Plan all unassigned stories(max:10* dev-Count) for current week to developers")
    @GetMapping(value = "/plan/old" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String planOldWay() throws Exception {
        return (new ObjectMapper()).writeValueAsString(planningSrv.planOldWay());
    }

    @Operation(summary = "Plan all unassigned stories(max:10* dev-Count) for current week to developers")
    @GetMapping(value = "/plan/sprint" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String planCurrentSprint() throws Exception {
        return (new ObjectMapper()).writeValueAsString(planningSrv.planCurrentSprint());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String generalException(Exception ex) {
        return "{\"message\":\""+ExceptionUtils.getRootCause(ex).getMessage().replace("\"","").replace("\n", "")+"\"}";
    }
}