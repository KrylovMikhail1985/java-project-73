package hexlet.code.app.service;

import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.Label;
import hexlet.code.app.model.Task;
import hexlet.code.app.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Autowired
    private LabelRepository labelRepository;
    @Override
    public Label createNewLabel(Label label) {
        return labelRepository.save(label);
    }

    @Override
    public Label findLabel(long id) {
        return labelRepository.findById(id).orElseThrow();
    }

    @Override
    public Label updateLabel(long id, @Valid Label label) {
        Label updatedLabel = labelRepository.findById(id).orElseThrow();
        updatedLabel.setName(label.getName());
        return updatedLabel;
    }
    @Override
    public void deleteLabel(long id) throws NotValidDataException {
        Label label = labelRepository.findById(id).orElseThrow();
        List<Task> listOfTasks = label.getListOfTasks();

        if (!listOfTasks.isEmpty()) {
            throw new NotValidDataException("There is Task that connected to this Label");
        } else {
            labelRepository.deleteById(id);
        }
    }

    @Override
    public List<Label> findAllLabels() {
        return labelRepository.findAll();
    }
}
