package hexlet.code.service;

import hexlet.code.exception.NotValidDataException;
import hexlet.code.model.Status;

import java.util.List;

public interface StatusService {
    Status createNewStatus(Status status) throws NotValidDataException;
    Status findStatusById(long id);
    Status updateStatus(long id, Status status) throws NotValidDataException;
    void deleteStatus(long id);
    List<Status> findAllStatuses();
}
