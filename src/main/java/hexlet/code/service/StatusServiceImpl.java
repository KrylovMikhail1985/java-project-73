package hexlet.code.service;

import hexlet.code.exception.NotValidDataException;
import hexlet.code.model.Status;
import hexlet.code.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {
    @Autowired
    private StatusRepository statusRepository;
    private final List<String> listOfStatuses = List.of("Новый", "В работе", "На тестировании", "Завершен");
    @Override
    public Status createNewStatus(Status status) throws NotValidDataException {
        checkStatusName(status.getName());
        return statusRepository.save(status);
    }

    @Override
    public Status findStatusById(long id) {
        return statusRepository.findById(id).orElseThrow();
    }

    @Override
    public Status updateStatus(long id, Status status) throws NotValidDataException {
        checkStatusName(status.getName());
        Status resultStatus = statusRepository.findById(id).orElseThrow();
        resultStatus.setName(status.getName());
        resultStatus.setCreatedAt(new Date());
        return resultStatus;
    }

    @Override
    public void deleteStatus(long id) {
        statusRepository.deleteById(id);
    }
    private void checkStatusName(String name) throws NotValidDataException {
        if (!listOfStatuses.contains(name)) {
            System.out.println("Status is not valid. Status must be one of: " +
                    "\"Новый\", \"В работе\", \"На тестировании\", \"Завершен\"");
            throw new NotValidDataException("myException");
        }
    }

    @Override
    public List<Status> findAllStatuses() {
        return statusRepository.findAll();
    }
}
