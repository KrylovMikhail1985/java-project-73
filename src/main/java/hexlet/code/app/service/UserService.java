package hexlet.code.app.service;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.User;

import java.util.List;

public interface UserService {
    User createNewUser(User user);
    User findUserById(long id);
    List<User> findAllUsers();
    User updateUser(long id, User user) throws NotValidDataException;
    void deleteUser(long id);
    User findByEmail(String firstName);
    UserDto convertUserToUserDto(User user);
    List<UserDto> convertListOfUsersToListOfUsersDto(List<User> listOfUsers);
    User getCurrentUser();
    User findUserByUserId(long id);
}
