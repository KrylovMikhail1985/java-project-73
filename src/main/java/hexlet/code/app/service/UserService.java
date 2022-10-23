package hexlet.code.app.service;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.model.User;

import java.util.List;

public interface UserService {
    User createNewUser(UserDto userdto);
    User findUserById(long id);
    List<User> findAllUsers();
    User updateUser(long id, UserDto userdto);
    void deleteUser(long id);
}
