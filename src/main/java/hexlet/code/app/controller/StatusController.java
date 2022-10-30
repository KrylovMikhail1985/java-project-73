package hexlet.code.app.controller;

import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.Status;
import hexlet.code.app.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.util.List;

@RestController
public class StatusController {
    @Autowired
    private StatusService statusService;
    @PostMapping("/api/statuses")
    public Status createNewStatus(@Valid @RequestBody Status status, BindingResult bindingResult)
            throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("Status is not valid");
        }
        return statusService.createNewStatus(status);
    }
    @GetMapping("/api/statuses/{id}")
    public Status findStatusById(@PathVariable(name = "id") long id) {
        return statusService.findStatusById(id);
    }
    @GetMapping("/api/statuses")
    public List<Status> findAllStatuses() {
        return statusService.findAllStatuses();
    }
    @PutMapping("/api/statuses/{id}")
    public Status updateStatus(@PathVariable(name = "id") long id,
                                @Valid @RequestBody Status status, BindingResult bindingResult)
            throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("Status is not valid");
        }
        return statusService.updateStatus(id, status);
    }
    @DeleteMapping("/api/statuses/{id}")
    public void deleteStatusById(@PathVariable(name = "id") long id) {
        statusService.deleteStatus(id);
    }
}
