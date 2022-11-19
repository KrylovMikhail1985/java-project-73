package hexlet.code.app.controller;

import hexlet.code.app.dto.UserDto;
import hexlet.code.app.exception.NotValidDataException;
import hexlet.code.app.model.User;
import hexlet.code.app.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Get all Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users were successfully received"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }
    @Operation(summary = "Get current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully received"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @GetMapping("/{id}")
    public User getUserById(@Parameter(description = "User's ID") @PathVariable(name = "id") long id) {
        return userService.findUserById(id);
    }

    @Operation(summary = "Create new User")
    @ApiResponse(responseCode = "200", description = "User was successfully created")
    @PostMapping("")
    public User createUser(@Parameter(description = "User's body") @Valid @RequestBody UserDto userDto,
                           BindingResult bindingResult) throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("User is not valid");
        }
        return userService.createNewUser(userDto);
    }
    @Operation(summary = "Update current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully updated"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @PutMapping("/{id}")
    public User updateUser(@Parameter(description = "User's body") @Valid @RequestBody UserDto user,
                           BindingResult bindingResult,
                           @Parameter(description = "User's ID") @PathVariable(name = "id") long id)
            throws NotValidDataException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            throw new NotValidDataException("User is not valid");
        }
        return userService.updateUser(id, user);
    }
    @Operation(summary = "Delete current User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User was successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Not authenticated request")
    })
    @DeleteMapping("/{id}")
    public void deleteUser(@Parameter(description = "User's ID") @PathVariable(name = "id") long id) {
        userService.deleteUser(id);
    }
}
