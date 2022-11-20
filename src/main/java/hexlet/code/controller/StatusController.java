package hexlet.code.controller;

import hexlet.code.exception.NotValidDataException;
import hexlet.code.model.Status;
import hexlet.code.service.StatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;
    @Operation(summary = "Get all Statuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "All Statuses were received"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @PostMapping("/api/statuses")
    @ResponseStatus(CREATED)
    public Status createNewStatus(@Parameter(description = "Status's ID") @Valid @RequestBody Status status,
                                  BindingResult bindingResult) throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("Status is not valid");
        }
        return statusService.createNewStatus(status);
    }
    @Operation(summary = "Get Status by it Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status was found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("/api/statuses/{id}")
    public Status findStatusById(@Parameter(description = "Status's ID") @PathVariable(name = "id") long id) {
        return statusService.findStatusById(id);
    }
    @Operation(summary = "Get all Statuses")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statuses were successfully received"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("/api/statuses")
    public List<Status> findAllStatuses() {
        return statusService.findAllStatuses();
    }
    @Operation(summary = "Update current Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status was successfully updated"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @PutMapping("/api/statuses/{id}")
    public Status updateStatus(@Parameter(description = "Status's ID") @PathVariable(name = "id") long id,
                               @Parameter(description = "Status's body") @Valid @RequestBody Status status,
                               BindingResult bindingResult) throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("Status is not valid");
        }
        return statusService.updateStatus(id, status);
    }
    @Operation(summary = "Delete current Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status was successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @DeleteMapping("/api/statuses/{id}")
    public void deleteStatusById(@Parameter(description = "Status's ID") @PathVariable(name = "id") long id) {
        statusService.deleteStatus(id);
    }
}
