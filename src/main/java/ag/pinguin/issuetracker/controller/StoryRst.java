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
import ag.pinguin.issuetracker.entity.Story;
import ag.pinguin.issuetracker.repository.StoryDao;
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

@RestController
@RequestMapping("/rst/story")
@Tag(name = "story",description = "story info can show and change via this rest service")
public class StoryRst {
    @Autowired private StoryDao dao;

    @Operation(summary = "Get a story by issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for get story(s)",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "All storys",
                                    value = "{\"issueid\": null}",
                                    summary = "show all storys"),
                            @ExampleObject(
                                    name = "One story",
                                    value = "{\"issueid\":1}",
                                    summary = "show a story") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the story", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Story.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid story", content = @Content),
            @ApiResponse(responseCode = "404", description = "story not found", content = @Content) })
    @PostMapping(value = "/find" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String find(@RequestBody Story story) throws Exception {
        List<Story> returnData=new ArrayList<>();
        if(story.getIssueid()==null) {
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
                                    value = "{\"issueid\":2}",
                                    summary = "delete story") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "story is deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid story", content = @Content),
            @ApiResponse(responseCode = "404", description = "story not found", content = @Content) })
    @DeleteMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody String receivedData) throws Exception {
        JSONObject json = new JSONObject(receivedData);
        Integer storyID=json.optInt("issueid",0);
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
                                            "\"estimatedpoint\":3," +
                                            "\"status\":\"Estimated\"" +
                                            "}",
                                    summary = "create story"),
                            @ExampleObject(
                                    name = "update story",
                                    value = "{\"issueid\":\"1\"," +
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
    public String save(@Valid @RequestBody Story story) throws Exception {
        String message="Error";
        Story dbstory= dao.findByIssueid(story.getIssueid());
        if(dbstory==null) {
            story=dao.save(story);
            if(story==null)
                message= "{\"message\":\"story is Not added. some problem is occurred\"}";
            else
                message= "{\"message\":\"new story is added\"}";
        }else {
            dbstory.setTitle(story.getTitle());
            dbstory.setDescription(story.getDescription());
            dbstory.setEstimatedpoint(story.getEstimatedpoint());
            dbstory.setStatus(story.getStatus());
            story=dao.save(dbstory);
            message= "{\"message\":\"story is updated\"}";
        }
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> generalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getRootCause(ex).getMessage());
    }
}
