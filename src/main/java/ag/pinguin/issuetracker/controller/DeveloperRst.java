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
import ag.pinguin.issuetracker.entity.IssueDTO;
import ag.pinguin.issuetracker.service.DeveloperSrv;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/rst/developers")
@Tag(name = "Developer",description = "Developer info can show and change via this rest service")
public class DeveloperRst {
    @Autowired private DeveloperSrv srv;
    @Autowired private StorySrv storySrv;

    @Operation(summary = "Set the developerID field to Get a developer or don't set developerID field to show all developers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the developer(s)", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Developer.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @GetMapping(value = "/" , produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameter(name = "developerID",description = "Integer identifier", example = "1")
    public String findDevelopers(@RequestParam(required = false) Integer developerID) throws Exception {
        return (new ObjectMapper()).writeValueAsString(srv.findDevelopers(developerID));
    }

    @Operation(summary = "How much is the developer's load?")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = IssueDTO.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @Parameter(name = "sprintNum", required = true, description = "Integer identifier", example = "2")
    @GetMapping(value = "/load" , produces = MediaType.APPLICATION_JSON_VALUE)
    public String calculateDeveloperLoad(@RequestParam Integer sprintNum) throws Exception {
        return (new ObjectMapper()).writeValueAsString(storySrv.calculateDeveloperLoad(sprintNum));
    }

    @Operation(summary = "Set the developerID field to Delete a developer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Developer is deleted"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @Parameter(name = "developerID",description = "Integer identifier", example = "3")
    @DeleteMapping(value = "/",produces = MediaType.APPLICATION_JSON_VALUE)
    public String deleteDeveloper(@RequestParam Integer developerID) throws Exception {
            srv.deleteDeveloper(developerID);
            return String.format("{\"message\":\"developerID %s is deleted\"}",developerID);
    }

    @Operation(summary = "Create a new developer")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for create new Developer",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "create a new developer, mahdieh",
                                    value = "{\"devid\":\"5\"," +
                                              "\"devname\":\"mahdieh\"" +
                                            "}",
                                    summary = "create developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is created"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PostMapping(value = "/" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String saveDeveloper(@Valid @RequestBody Developer developer) throws Exception {
        String message;
        Developer dbDeveloper= srv.findDeveloper(developer.getDevid());
        if(dbDeveloper==null || dbDeveloper.getDevid()==null) {
           developer=srv.upsertDeveloper(developer);
                if(developer==null) message= "{\"message\":\"developer is not added. some problem is occurred.\"}";
                else message= (new ObjectMapper()).writeValueAsString(developer);
            }else message= String.format("{\"message\":\"developerID %s is already exist.\"}",developer.getDevid());
            return message;
    }

    @Operation(summary = "Update a developer via developerID")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for update a Developer",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "update developer afshin",
                                    value = "{\"devid\":\"4\"," +
                                            "\"devname\":\"afshin-update\"" +
                                            "}",
                                    summary = "update developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is updated"),
            @ApiResponse(responseCode = "500", description = "Internal error", content = @Content) })
    @PutMapping(value = "/" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateDeveloper(@Valid @RequestBody Developer developer) throws Exception {
        String message;
        Developer dbDeveloper= srv.findDeveloper(developer.getDevid());
        if(dbDeveloper==null || dbDeveloper.getDevid()==null) message=String.format("{\"message\":\"developerID %s is not found to update.\"}",developer.getDevid());
        else {
            dbDeveloper.setDevname(developer.getDevname());
            developer=srv.upsertDeveloper(dbDeveloper);
            message= (new ObjectMapper()).writeValueAsString(developer);
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