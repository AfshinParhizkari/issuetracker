package ag.pinguin.issuetracker.controller;
/**
 * @Project issuetracker
 * @Author Afshin Parhizkari
 * @Date 2022 - 01 - 12
 * @Time 2:02 AM
 * Created by   IntelliJ IDEA
 * Email:       Afshin.Parhizkari@gmail.com
 * Description:
 */
import ag.pinguin.issuetracker.entity.Developer;
import ag.pinguin.issuetracker.repository.DeveloperDao;
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
import java.util.List;

@RestController
@RequestMapping("/rst/developer")
@Tag(name = "Developer",description = "Developer info can show and change via this rest service")
public class DeveloperRst {
    @Autowired private DeveloperDao dao;

    @Operation(summary = "Get a developer by developer ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for get Developer(s)",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "All Developers",
                                    value = "{\"devid\": null}",
                                    summary = "show all Developers"),
                            @ExampleObject(
                                    name = "Just developer name andre",
                                    value = "{\"devid\":1}",
                                    summary = "show a developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the developer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Developer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Developer name", content = @Content),
            @ApiResponse(responseCode = "404", description = "Developer not found", content = @Content) })
    @PostMapping(value = "/find" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String findDeveloper(@RequestBody Developer developer) throws Exception {
        List<Developer> returnData;
        if(developer.getDevid()==null) {
            returnData = (dao.findAll());
        }else
            returnData = (dao.findByDevid(developer.getDevid()));
        return (new ObjectMapper()).writeValueAsString(returnData);
    }

    @Operation(summary = "Delete a developer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for delete Developer",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                             @ExampleObject(
                                    name = "delete developer hossein",
                                    value = "{\"devid\":3}",
                                    summary = "delete developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer is deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid developer name", content = @Content),
            @ApiResponse(responseCode = "404", description = "developer name not found", content = @Content) })
    @DeleteMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteDeveloper(@RequestBody String receivedData) throws Exception {
        JSONObject json = new JSONObject(receivedData);
        Integer developerID=json.optInt("devid",0);
        dao.deleteById(developerID);
    }

    @Operation(summary = "create or update a developer by developer ID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for create new Developer",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "create new developer mahdieh",
                                    value = "{\"devid\":\"5\"," +
                                              "\"devname\":\"mahdieh\"" +
                                            "}",
                                    summary = "create developer"),
                            @ExampleObject(
                                    name = "update developer afshin",
                                    value = "{\"devid\":\"4\"," +
                                            "\"devname\":\"afshin-update\"" +
                                            "}",
                                    summary = "update developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is created"),
            @ApiResponse(responseCode = "400", description = "Invalid developer name", content = @Content) })
    @PutMapping(value = "/save" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveDeveloper(@Valid @RequestBody Developer developer) throws Exception {
        String message="Error";
        List<Developer> developers= dao.findByDevid(developer.getDevid());
        if(developers.size()==0) {
            developer=dao.save(developer);
            if(developer==null)
                message= "{\"message\":\"developer is Not added. some problem is occurred\"}";
            else
                message= "{\"message\":\"new developer is added\"}";
        }else {
            developers.get(0).setDevname(developer.getDevname());
            developer=dao.save(developers.get(0));
            message= "{\"message\":\"developer is updated\"}";
        }
        return message;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<Object> generalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ExceptionUtils.getRootCause(ex).getMessage());
    }
}