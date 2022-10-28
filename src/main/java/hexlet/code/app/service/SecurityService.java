package hexlet.code.app.service;

import java.util.Map;

public interface SecurityService {
    String getJWT(Map<String, String> user);
}
