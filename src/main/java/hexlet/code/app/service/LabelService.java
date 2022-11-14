package hexlet.code.app.service;

import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.Label;

import java.util.List;

public interface LabelService {
    Label createNewLabel(Label label);
    Label findLabel(long id);
    Label updateLabel(long id, Label label);
    void deleteLabel(long id) throws NotValidDataException;
    List<Label> findAllLabels();
}
