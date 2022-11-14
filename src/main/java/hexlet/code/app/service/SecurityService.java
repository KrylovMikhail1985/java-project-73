package hexlet.code.app.service;

import hexlet.code.app.exception.NotAuthorizedException;

import java.util.Map;

public interface SecurityService {
    String getJWT(Map<String, String> user) throws NotAuthorizedException;
}
