package hexlet.code.app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    void contextLoads() {
    }
    @BeforeAll
    public void createUser() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"firstName\": \"Shura\"," +
                        "\"lastName\": \"Sidorov\"," +
                        "\"password\": \"123321\"," +
                        "\"email\": \"frolov@fff.com\"" +
                        "}")
        ).andReturn();
    }
    @Test
    public void findAllUsers() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/users"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains("firstName")
                .contains("Shura")
                .doesNotContain("password");
    }
    @Test
    public void createUserTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"Ivan\"," +
                                "\"lastName\": \"Petrov\"," +
                                "\"password\": \"12345\"," +
                                "\"email\": \"aaaa@jkl.ru\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Petrov");
        assertThat(response.getContentAsString()).doesNotContain("password");
    }
    @Test
    public void getUserById() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/users/1")
                                .header("Authorization", "Basic ZnJvbG92QGZmZi5jb206MTIzMzIx"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Shura");
        assertThat(response.getContentAsString()).doesNotContain("password");
    }
    @Test
    public void updateUserTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/users/1")
                                .header("Authorization", "Basic ZnJvbG92QGZmZi5jb206MTIzMzIx")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"firstName\": \"ShuraUpdate\"," +
                                        "\"lastName\": \"SidorovUpdate\"," +
                                        "\"email\": \"lll@jkl.ru\"" +
                                        "}")
                        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains("ShuraUpdate")
                .contains("SidorovUpdate")
                .contains("lll@jkl.ru");
        assertThat(response.getContentAsString()).doesNotContain("password");
    }
    @Test
    public void doNotValidNameForCreatingUser() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"\"," +
                                "\"lastName\": \"Petrov\"," +
                                "\"password\": \"12345\"," +
                                "\"email\": \"aaaa@jkl.ru\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void doNotValidLastNameForCreatingUser() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"Ivan\"," +
                                "\"lastName\": \"\"," +
                                "\"password\": \"12345\"," +
                                "\"email\": \"aaaa@jkl.ru\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void doNotValidEmailForCreatingUser() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"Ivan\"," +
                                "\"lastName\": \"Petrov\"," +
                                "\"password\": \"12345\"," +
                                "\"email\": \"aaaaru\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void doNotValidPasswordForCreatingUser() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"Ivan\"," +
                                "\"lastName\": \"Petrov\"," +
                                "\"password\": \"h\"," +
                                "\"email\": \"aaaa@jkl.ru\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void doNotValidNameForUpdateUserTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/users/1")
                        .header("Authorization", "Basic ZnJvbG92QGZmZi5jb206MTIzMzIx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"\"," +
                                "\"lastName\": \"SidorovUpdate\"," +
                                "\"email\": \"lll@jkl.ru\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void doNotValidLastNameForUpdateUserTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/users/1")
                        .header("Authorization", "Basic ZnJvbG92QGZmZi5jb206MTIzMzIx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"ShuraUpdate\"," +
                                "\"lastName\": \"\"," +
                                "\"email\": \"lll@jkl.ru\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void doNotValidEmailForUpdateUserTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/users/1")
                        .header("Authorization", "Basic ZnJvbG92QGZmZi5jb206MTIzMzIx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"firstName\": \"ShuraUpdate\"," +
                                "\"lastName\": \"SidorovUpdate\"," +
                                "\"email\": \"llu\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void getUserByIdNotAuthorized() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/users/1"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
}
