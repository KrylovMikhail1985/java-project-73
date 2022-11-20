package hexlet.code.service;

import hexlet.code.exception.NotAuthorizedException;

import java.util.Map;

public interface SecurityService {
    String getJWT(Map<String, String> user) throws NotAuthorizedException;
}
