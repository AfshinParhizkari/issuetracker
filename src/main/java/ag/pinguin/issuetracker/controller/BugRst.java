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
                                    value = "{\"issueid\": null}",
                                    summary = "show all Bugs"),
                            @ExampleObject(
                                    name = "One Bug",
                                    value = "{\"issueid\":1}",
                                    summary = "show a Bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the Bug", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Bug.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Bug", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bug not found", content = @Content) })
    @PostMapping(value = "/find" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String find(@RequestBody Bug bug) throws Exception {
        List<Bug> returnData=new ArrayList<>();
        if(bug.getIssueid()==null) {
            returnData = (dao.findAll());
        }else
            returnData.add(dao.findByIssueid(bug.getIssueid()));
        return (new ObjectMapper()).writeValueAsString(returnData);
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
                                    value = "{\"issueid\":2}",
                                    summary = "delete Bug") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bug is deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Bug", content = @Content),
            @ApiResponse(responseCode = "404", description = "Bug not found", content = @Content) })
    @DeleteMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody String receivedData) throws Exception {
        JSONObject json = new JSONObject(receivedData);
        Integer bugID=json.optInt("issueid",0);
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
                                    value = "{\"issueid\":\"1\"," +
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
    public String save(@Valid @RequestBody Bug bug) throws Exception {
        String message="Error";
        Bug dbBug= dao.findByIssueid(bug.getIssueid());
        if(dbBug==null) {
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

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> generalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getRootCause(ex).getMessage());
    }
}