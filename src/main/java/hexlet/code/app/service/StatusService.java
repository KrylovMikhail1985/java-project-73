package hexlet.code.app.service;

import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.Status;

import java.util.List;

public interface StatusService {
    Status createNewStatus(Status status) throws NotValidDataException;
    Status findStatusById(long id);
    Status updateStatus(long id, Status status) throws NotValidDataException;
    void deleteStatus(long id);
    List<Status> findAllStatuses();
}
