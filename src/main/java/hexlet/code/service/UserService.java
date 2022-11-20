package hexlet.code.service;

import hexlet.code.dto.UserDto;
import hexlet.code.exception.NotValidDataException;
import hexlet.code.model.User;

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
