package hexlet.code.app.controller;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.User;
import hexlet.code.app.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api/users")
public class UsersController {
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(name = "id") long id) {
        return userService.findUserById(id);
    }

    @PostMapping("")
    public User createUser(@Valid @RequestBody UserDto userDto, BindingResult bindingResult)
            throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("User is not valid");
        }
        return userService.createNewUser(userDto);
    }
    @PutMapping("/{id}")
    public User updateUser(@Valid @RequestBody UserDto user, BindingResult bindingResult,
                              @PathVariable(name = "id") long id) throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("User is not valid");
        }
        return userService.updateUser(id, user);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable(name = "id") long id) {
        userService.deleteUser(id);
    }
}
