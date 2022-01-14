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
import ag.pinguin.issuetracker.repository.BugDao;
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
@RequestMapping("/rst/bug")
@Tag(name = "Bug",description = "Bug info can show and change via this rest service")
public class BugRst {
    @Autowired private BugDao dao;

    @Operation(summary = "Get a Bug by issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for get Bug(s)",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "All Bugs",
                                    value = "{\"issueid\": \"\"}",
                                    summary = "show all Bugs"),
                            @ExampleObject(
                                    name = "One Bug",
                                    value = "{\"issueid\":\"6b24ba48-52cd-4e1f-a2d7-beba1d7f456f\"}",
                                    summary = "show a Bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the Bug", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Bug.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Bug", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bug not found", content = @Content) })
    @PostMapping(value = "/find" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String findBug(@RequestBody Bug bug) throws Exception {
        List<Bug> returnData=new ArrayList<>();
        if(bug.getIssueid().isEmpty()) {
            returnData = (dao.findAll());
        }else
            returnData.add(dao.findByIssueid(bug.getIssueid()));
        return (new ObjectMapper()).writeValueAsString(returnData);
    }

    @Operation(summary = "Assign a Bug to developer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for Assign a Bug",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "Assign Bug",
                                    value = "{\"issueid\":\"d534ff04-43c2-429e-b91f-deecf6210c32\","+
                                            "\"assignedev\":1" +
                                            "}",
                                    summary = "Assign Bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bug is assigned"),
            @ApiResponse(responseCode = "400", description = "Invalid Bug", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bug not found", content = @Content) })
    @PutMapping(value = "/assign",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> assignBug(@RequestBody String receivedData) throws Exception {
        JSONObject json = new JSONObject(receivedData);
        String bugID=json.optString("issueid","");
        Integer developerID=json.optInt("assignedev",1);
        Bug bug =dao.findByIssueid(bugID);
        bug.setAssignedev(developerID);
        bug=dao.save(bug);
        String response=(new ObjectMapper()).writeValueAsString(bug);
        return new ResponseEntity<String>(response,HttpStatus.OK);
    }

    @Operation(summary = "Delete a Bug")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for delete a Bug",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "delete Bug",
                                    value = "{\"issueid\":\"06f007fb-cc1d-43a5-843f-8484660f71ff\"}",
                                    summary = "delete Bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bug is deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Bug", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bug not found", content = @Content) })
    @DeleteMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteBug(@RequestBody String receivedData) throws Exception {
        JSONObject json = new JSONObject(receivedData);
        String bugID=json.optString("issueid","");
        dao.deleteById(bugID);
    }

    @Operation(summary = "create or update a Bug by issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for create new Bug",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "create new Bug",
                                    value = "{\"title\":\"ui interface\"," +
                                            "\"description\":\"responsive does not working in mobile devices\"," +
                                            "\"priority\":\"Minor\"," +
                                            "\"status\":\"New\"" +
                                            "}",
                                    summary = "create Bug"),
                            @ExampleObject(
                                    name = "update Bug",
                                    value = "{\"issueid\":\"6b24ba48-52cd-4e1f-a2d7-beba1d7f456f\"," +
                                            "\"title\":\"h2 db doesn't persist data\"," +
                                            "\"description\":\"we have data lost when service restart\"," +
                                            "\"priority\":\"Major\"," +
                                            "\"status\":\"Verified\"" +
                                            "}",
                                    summary = "update Bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is created"),
            @ApiResponse(responseCode = "400", description = "Invalid Bug name", content = @Content) })
    @PutMapping(value = "/save" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String upsertBug(@Valid @RequestBody Bug bug) throws Exception {
        String message="Error";
        Bug dbBug= dao.findByIssueid(bug.getIssueid());
        if(dbBug==null) {
            bug.setIssueid(UUID.randomUUID().toString());
            bug=dao.save(bug);
            if(bug==null)
                message= "{\"message\":\"Bug is Not added. some problem is occurred\"}";
            else
                message= "{\"message\":\"new Bug is added\"}";
        }else {
            dbBug.setTitle(bug.getTitle());
            dbBug.setDescription(bug.getDescription());
            dbBug.setPriority(bug.getPriority());
            dbBug.setStatus(bug.getStatus());
            bug=dao.save(dbBug);
            message= "{\"message\":\"Bug is updated\"}";
        }
        return message;
    }

    @Operation(summary = "Change status to New, Verified or Resolved by issueID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for commit a change status",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "change status New",
                                    value = "{\"issueid\":\"d534ff04-43c2-429e-b91f-deecf6210c32\"," +
                                            "\"status\":\"Verified\"" +
                                            "}",
                                    summary = "change status New"),
                            @ExampleObject(
                                    name = "change status Resolved",
                                    value = "{\"issueid\":\"6b24ba48-52cd-4e1f-a2d7-beba1d7f456f\"," +
                                            "\"status\":\"New\"" +
                                            "}",
                                    summary = "Can not change Resolved status") }))
    @PostMapping(value = "/changestatus" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String changeStatus(@RequestBody String receivedData) throws Exception {
        String returnData="";
        JSONObject json = new JSONObject(receivedData);
        String bugID=json.optString("issueid","");
        String status=json.optString("status","");
        Bug bug = dao.findByIssueid(bugID);
        if(status.isEmpty() || bug.getStatus().equals("Resolved"))
            returnData="{\"message\":\""+ BugStatus.Verified+" status can't change. the task is closed\"}";
        else {
            bug.setStatus(status);
            bug=dao.save(bug);
            returnData=(new ObjectMapper()).writeValueAsString(bug);
        }
        return returnData;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> generalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getRootCause(ex).getMessage());
    }
}