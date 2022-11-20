package hexlet.code.service;

import hexlet.code.exception.NotValidDataException;
import hexlet.code.model.Label;

import java.util.List;

public interface LabelService {
    Label createNewLabel(Label label);
    Label findLabel(long id);
    Label updateLabel(long id, Label label);
    void deleteLabel(long id) throws NotValidDataException;
    List<Label> findAllLabels();
}
