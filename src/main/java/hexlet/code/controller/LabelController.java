package hexlet.code.controller;

import hexlet.code.model.Label;
import hexlet.code.service.LabelServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LabelController {
    @Autowired
    private LabelServiceImpl labelService;
    @Operation(summary = "Get Label by it id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label was found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("/labels/{id}")
    public Label findLabel(@Parameter(description = "Label's ID") @PathVariable(name = "id") long id) {
        return labelService.findLabel(id);
    }
    @Operation(summary = "Find all Labels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Labels were found"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("/labels")
    public List<Label> findAllLabels() {
        return labelService.findAllLabels();
    }
    @Operation(summary = "Create new Label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Labels were created"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @PostMapping("/labels")
    public Label createNewLabel(@Parameter(description = "Label's body") @Valid @RequestBody Label label) {
        return labelService.createNewLabel(label);
    }
    @Operation(summary = "Update current Label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label was updated"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @PutMapping("/labels/{id}")
    public Label updateLabel(@Parameter(description = "Label's ID") @PathVariable(name = "id") long id,
                             @Parameter(description = "Label's body") @Valid @RequestBody Label label) {
        return labelService.updateLabel(id, label);
    }
    @Operation(summary = "Delete current Label")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Label was successfully deleted"),
            @ApiResponse(responseCode = "422", description = "Label cannot be deleted while it using"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @DeleteMapping("/labels/{id}")
    public void deleteLabel(@Parameter(description = "Label's ID") @PathVariable(name = "id") long id)
            throws Exception {
        labelService.deleteLabel(id);
    }
}
