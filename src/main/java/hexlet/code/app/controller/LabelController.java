package hexlet.code.app.controller;

import hexlet.code.app.model.Label;
import hexlet.code.app.service.LabelServiceImpl;
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
    @GetMapping("/labels/{id}")
    public Label findLabel(@PathVariable(name = "id") long id) {
        return labelService.findLabel(id);
    }
    @GetMapping("/labels")
    public List<Label> findAllLabels() {
        return labelService.findAllLabels();
    }
    @PostMapping("/labels")
    public Label createNewLabel(@Valid @RequestBody Label label) {
        return labelService.createNewLabel(label);
    }
    @PutMapping("/labels/{id}")
    public Label updateLabel(@PathVariable(name = "id") long id,
                             @Valid @RequestBody Label label) {
        return labelService.updateLabel(id, label);
    }
    @DeleteMapping("/labels/{id}")
    public void deleteLabel(@PathVariable(name = "id") long id) throws Exception {
        labelService.deleteLabel(id);
    }
}
