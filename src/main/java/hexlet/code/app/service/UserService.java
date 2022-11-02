package hexlet.code.app.service;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.User;

import java.util.List;

public interface UserService {
    User createNewUser(UserDto userDto);
    User findUserById(long id);
    List<User> findAllUsers();
    User updateUser(long id, UserDto userDto) throws NotValidDataException;
    void deleteUser(long id);
    User findByEmail(String firstName);
    User getCurrentUser();
    User findUserByUserId(long id);
}
