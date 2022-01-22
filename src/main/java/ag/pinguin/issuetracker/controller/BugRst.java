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
import ag.pinguin.issuetracker.entity.Bug;
import ag.pinguin.issuetracker.entity.BugStatus;
import ag.pinguin.issuetracker.entity.Developer;
import ag.pinguin.issuetracker.entity.StoryStatus;
import ag.pinguin.issuetracker.service.BugSrv;
import ag.pinguin.issuetracker.service.DeveloperSrv;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/rst/bugs")
@Tag(name = "Bug",description = "This service can show bug information and change it")
public class BugRst {
    @Autowired private BugSrv srv;
    @Autowired private DeveloperSrv devSrv;

    @Operation(summary = "Set the issueID field to Get a bug or don't set to show all bugs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the bug(s)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Bug.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @Parameter(name = "issueID",description = "A string identifier", example = "6b24ba48-52cd-4e1f-a2d7-beba1d7f456f")
    @GetMapping(value = "/" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public String findBugs(@RequestParam(required = false,defaultValue = "") String issueID) throws Exception {
        return (new ObjectMapper()).writeValueAsString(srv.findBugs(issueID));
    }

    @Operation(summary = "Set the issueID field to Delete a bug")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bug is deleted"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @Parameter(name = "issueID",description = "A string identifier", example = "06f007fb-cc1d-43a5-843f-8484660f71ff")
    @DeleteMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteBug(@RequestParam String issueID) throws Exception {
        srv.deleteBug(issueID);
        return String.format("{\"message\":\"issueID %s is deleted\"}",issueID);
    }

    @Operation(summary = "Assign a Bug to developer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for Assign a Bug",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "Assign developer to bug",
                                    value = "{\"issueid\":\"d534ff04-43c2-429e-b91f-deecf6210c32\","+
                                            "\"assignedev\":1" +
                                            "}",
                                    summary = "Assign developer to bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bug is assigned"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PutMapping(value = "/assignment",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> assignBug(@RequestBody String receivedData) throws Exception {//Also you can use DTO obj
        JSONObject json = new JSONObject(receivedData);
        String bugID=json.optString("issueid","");
        Integer developerID=json.optInt("assignedev",1);
        Bug bug =srv.findBug(bugID);
        Developer developer=devSrv.findDeveloper(developerID);
        String message;
        if(bug==null || developer==null || bug.getIssueid()==null || developer.getDevid()==null || bug.getStatus().equals(BugStatus.Resolved.toString()))
            message= "{\"message\":\"Bug or Developer is not found Or status was Resolved\"}";
        else
            message = (new ObjectMapper()).writeValueAsString(srv.assignBug(bug,developer));
        return new ResponseEntity<String>(message,HttpStatus.OK);
    }

    @Operation(summary = "Change status to New, Verified or Resolved via issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for commit a change status",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "Correct. status:{New,Verified,Resolved}",
                                    value = "{\"issueid\":\"d534ff04-43c2-429e-b91f-deecf6210c32\"," +
                                            "\"status\":\"Verified\"" +
                                            "}",
                                    summary ="Change status to New"),
                            @ExampleObject(
                                    name = "Incorrect. status:{New,Verified,Resolved}",
                                    value = "{\"issueid\":\"6b24ba48-52cd-4e1f-a2d7-beba1d7f456f\"," +
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
        String bugID=json.optString("issueid","");
        BugStatus status=BugStatus.valueOf(json.optString("status",""));
        Bug bug = srv.findBug(bugID);
        if(bug==null || status==null || bug.getIssueid()==null || bug.getStatus().equals(BugStatus.Resolved.toString()))
            message= "{\"message\":\"bug or status is not found or status was Resolved\"}";
        else
            message = (new ObjectMapper()).writeValueAsString(srv.changeStatus(bug,status));
        return message;
    }

    @Operation(summary = "create a new Bug")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for create new bug",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "priority:{Critical,Major,Minor}",
                                    value = "{\"title\":\"ui interface\"," +
                                            "\"description\":\"responsive does not working in mobile devices\"," +
                                            "\"priority\":\"Minor\"" +
                                            "}",
                                    summary = "Create a new bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is created"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PostMapping(value = "/" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveBug(@Valid @RequestBody Bug bug) throws Exception {
        String message;
        Bug dbbug= srv.findBug(bug.getIssueid());
        if(dbbug==null || dbbug.getIssueid()==null) {
            bug.setIssueid(UUID.randomUUID().toString());
            bug.setStatus(BugStatus.New.toString());
            bug=srv.upsertBug(bug);
            if(bug==null) message= "{\"message\":\"bug is not added. some problem is occurred.\"}";
            else message= (new ObjectMapper()).writeValueAsString(bug);
        }else message= String.format("{\"message\":\"issueID %s is already exist.\"}",bug.getIssueid());
        return message;
    }

    @Operation(summary = "Update a bug via issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for update a bug",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "priority:{Critical,Major,Minor}",
                                    value = "{\"issueid\":\"06f007fb-cc1d-43a5-843f-8484660f71ff\"," +
                                            "\"title\":\"h2 db doesn't persist data\"," +
                                            "\"description\":\"we have data lost when service restart\"," +
                                            "\"priority\":\"Major\"" +
                                            "}",
                                    summary = "update a bug via issueID") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is updated"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PutMapping(value = "/" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateBug(@Valid @RequestBody Bug bug) throws Exception {
        String message;
        Bug dbBug= srv.findBug(bug.getIssueid());
        if(dbBug==null || dbBug.getIssueid()==null || dbBug.getStatus().equals(BugStatus.Verified.toString()))
            message=String.format("{\"message\":\"issueID %s is not found to update Or Status was completed.\"}",bug.getIssueid());
        else {
            dbBug.setTitle(bug.getTitle());
            dbBug.setDescription(bug.getDescription());
            dbBug.setPriority(bug.getPriority());
            bug=srv.upsertBug(dbBug);
            message= (new ObjectMapper()).writeValueAsString(bug);
        }
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String generalException(Exception ex) {
        return "{\"message\":\""+ExceptionUtils.getRootCause(ex).getMessage().replace("\"","").replace("\n", "")+"\"}";
    }
}