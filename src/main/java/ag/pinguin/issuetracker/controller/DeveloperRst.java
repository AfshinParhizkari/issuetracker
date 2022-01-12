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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rst/developer")
@Tag(name = "Developer",description = "Developer info can show via this rest service")
public class DeveloperRst {
    @Autowired private DeveloperDao dao;

    @Operation(summary = "Get a developer by developer name")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for get Developer(s)",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "All Developers",
                                    value = "{\"devname\": \"\"}",
                                    summary = "All Developers"),
                            @ExampleObject(
                                    name = "Just developer name andre",
                                    value = "{\"devname\":\"andre\"}",
                                    summary = "One developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "found the developer", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Developer.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Developer name", content = @Content),
            @ApiResponse(responseCode = "404", description = "Developer not found", content = @Content) })
    @PostMapping(value = "/find" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String find(@RequestBody Developer developer) throws Exception {
        List<Developer> returnData;
        if(developer.getDevname()==null || developer.getDevname().isEmpty()) {
            returnData = (dao.findAll());
        }else
            returnData = (dao.findByDevname(developer.getDevname()));
        return  (new ObjectMapper()).writeValueAsString(returnData);
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
                                    value = "{\"devname\":\"hossein\"}",
                                    summary = "delete developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid developer name", content = @Content),
            @ApiResponse(responseCode = "404", description = "developer name not found", content = @Content) })
    @DeleteMapping(value = "/delete",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@RequestBody Developer developer) throws Exception {
        dao.delete(developer);
    }

    @Operation(summary = "create a developer by developer name")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Examples for create new Developer",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content (
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {
                            @ExampleObject(
                                    name = "create developer mahdieh",
                                    value = "{\"devname\":\"mahdieh\"}",
                                    summary = "create developer") }))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "record is created"),
            @ApiResponse(responseCode = "400", description = "Invalid developer name", content = @Content) })
    @PutMapping(value = "/save" ,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public String save(@Valid @RequestBody Developer developer) throws Exception {
        if(dao.findByDevname(developer.getDevname()).size()==0) {
            dao.save(developer);
           return "{\"message\":\"new developer is added\"}";
       }else
            return "{\"message\":\"developer is already saved\"}";
    }
}