package hexlet.code.service;

import hexlet.code.exception.NotValidDataException;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.repository.LabelRepository;
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
