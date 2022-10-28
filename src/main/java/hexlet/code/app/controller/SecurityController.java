package hexlet.code.app.controller;

import hexlet.code.app.service.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api")
public class SecurityController {
    @Autowired
    private SecurityServiceImpl securityService;
    @PostMapping("/login")
    public String getJWT(@RequestBody(required = false) Map<String, String> map) {
        return securityService.getJWT(map);
    }
}