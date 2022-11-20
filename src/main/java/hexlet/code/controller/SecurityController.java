package hexlet.code.controller;

import hexlet.code.exception.NotAuthorizedException;
import hexlet.code.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RestController
@RequestMapping("api")
public class SecurityController {
    @Autowired
    private SecurityServiceImpl securityService;
    @PostMapping("/login")
    public String getJWT(@RequestBody(required = false) Map<String, String> map) throws NotAuthorizedException {
        return securityService.getJWT(map);
    }
}
