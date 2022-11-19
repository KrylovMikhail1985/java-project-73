package hexlet.code.app;

import hexlet.code.app.service.LabelServiceImpl;
import hexlet.code.app.service.TaskServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@DBRider
//@DBUnit(schema = "test")
//@DataSet(value = "fillingDB.yml")
class AppApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LabelServiceImpl labelService;
    @Autowired
    private TaskServiceImpl taskService;
    @Test
    void contextLoads() {
    }
    private String headerBearer;
    @BeforeAll
    public void fillingDB() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"firstName\": \"mmm\"," +
                        "\"lastName\": \"mmm\"," +
                        "\"password\": \"mmm123\"," +
                        "\"email\": \"mmm@jjj.ru\"" +
                        "}")
        );
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"email\": \"mmm@jjj.ru\"," +
                                "\"password\": \"mmm123\"" +
                                "}")
                ).andReturn().getResponse();
        this.headerBearer = "Bearer " + response.getContentAsString();

        fillingOtherTable();
    }
    @Test
    public void findAllUsers() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/users"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains("firstName")
                .contains("mmm")
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
                                .header("Authorization", headerBearer))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("mmm");
        assertThat(response.getContentAsString()).doesNotContain("password");
    }
    @Test
    public void updateUserTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/users/1")
                                .header("Authorization", headerBearer)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{" +
                                        "\"firstName\": \"ShuraUpdate\"," +
                                        "\"lastName\": \"SidorovUpdate\"," +
                                        "\"password\": \"somePassword\"," +
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
                        .header("Authorization", headerBearer)
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
                        .header("Authorization", headerBearer)
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
                        .header("Authorization", headerBearer)
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
    public void getUserByIdNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/users/1"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void deleteUserTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/users/1")
                                .header("Authorization", headerBearer)
                        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }
    @Test
    public void createNewStatusTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/statuses")
                        .header("Authorization", headerBearer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"В работе\"" +
                                "}")
                ).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("В работе");
    }
    @Test
    public void createNewStatusNotValidTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/statuses")
                        .header("Authorization", headerBearer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"не тот статус\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    @Test
    public void createNewStatusNotAuthenticatedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/statuses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"Name\": \"В работе\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void readStatusTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/statuses/1"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("id");
    }
    @Test
    public void readAllStatusesTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/statuses"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("id");
    }
    @Test
    public void updateStatusTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/statuses/1")
                        .header("Authorization", headerBearer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"В работе\"" +
                                "}")
                ).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("В работе");
    }
    @Test
    public void updateStatusNotAuthenticatedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/statuses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"В работе\"" +
                                "}")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void deleteStatusTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/statuses/1")
                        .header("Authorization", headerBearer)
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }
    @Test
    public void deleteStatusNotAuthenticatedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/statuses/1")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void getAllTasksTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/tasks")
                                .header("Authorization", headerBearer)
                        ).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Новая задача", "Новый", "mmm");
    }
    @Test
    public void getAllTasksWithParamsTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/tasks?taskStatus=1&executorId=2&labels=2&authorId=1")
                        .header("Authorization", headerBearer)
                ).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString())
                .contains("Новая задача 2", "Новый", "mmm")
                .doesNotContain("www");
    }
    @Test
    public void getTaskById() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/tasks/1")
                                .header("Authorization", headerBearer)
                        ).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Новая задача", "Новый", "mmm");
    }
    @Test
    public void createTaskTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/tasks")
                        .header("Authorization", headerBearer)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{" +
                            "\"name\": \"TestTask\"," +
                            "\"description\": \"Какое-то описание\"," +
                            "\"executorId\": 1," +
                            "\"taskStatusId\": 1" +
                            "}")).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("TestTask", "Новый", "mmm");
    }
    @Test
    public void updateTaskTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/tasks/1")
                        .header("Authorization", headerBearer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"Новая задача updated\"," +
                                "\"description\": \"Какое-то описание\"," +
                                "\"executorId\": 1," +
                                "\"taskStatusId\": 1" +
                                "}")).andReturn().getResponse();
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("Новая задача updated");
    }
    @Test
    public void deleteTaskTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/tasks/1")
                        .header("Authorization", headerBearer)
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }
    @Test
    public void getAllTasksNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/tasks"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void getTasksByIdNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/tasks/1"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void createTaskAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"TestTask\"," +
                                "\"description\": \"Какое-то описание\"," +
                                "\"executorId\": 1," +
                                "\"taskStatusId\": 1" +
                                "}")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void updateTaskNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"Новая задача updated\"," +
                                "\"description\": \"Какое-то описание\"," +
                                "\"executorId\": 1," +
                                "\"taskStatusId\": 1" +
                                "}")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void deleteTaskNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/tasks/1")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void getAllLabelsTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/labels")
                        .header("Authorization", headerBearer)
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getContentAsString()).contains("Новая метка");
    }
    @Test
    public void getLabelsByIdTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/labels/1")
                        .header("Authorization", headerBearer)
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getContentAsString()).contains("Новая метка");
    }
    @Test
    public void createNewLabelTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/labels")
                        .header("Authorization", headerBearer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"Еще одна метка\"" +
                                "}")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        response.setCharacterEncoding("UTF-8");
        assertThat(response.getContentAsString()).contains("Еще одна метка");
    }
    @Test
    public void updateLabelTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/labels/1")
                        .header("Authorization", headerBearer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"updated Name\"" +
                                "}")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).contains("updated Name");
    }
    @Test
    public void deleteLabelTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/labels/1")
                                .header("Authorization", headerBearer)
                        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }
    @Test
    public void getAllLabelsNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/labels")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }
    @Test
    public void getLabelsByIdNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(get("/api/labels/1")
                ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(200);
    }
    @Test
    public void createNewLabelNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(post("/api/labels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"Еще одна метка\"" +
                                "}")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void updateLabelNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(put("/api/labels/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{" +
                                "\"name\": \"updated Name\"" +
                                "}")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void deleteLabelNotAuthorizedTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/labels/1"))
                        .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(401);
    }
    @Test
    public void deleteLabelWithTaskTest() throws Exception {
        MockHttpServletResponse response =
                mockMvc.perform(delete("/api/labels/2")
                                .header("Authorization", headerBearer)
                        ).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(422);
    }
    public void fillingOtherTable() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"firstName\": \"aaa\"," +
                        "\"lastName\": \"aaa\"," +
                        "\"password\": \"aaa123\"," +
                        "\"email\": \"aaa@jjj.ru\"" +
                        "}")
        );
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"firstName\": \"www\"," +
                        "\"lastName\": \"www\"," +
                        "\"password\": \"www123\"," +
                        "\"email\": \"www@jjj.ru\"" +
                        "}")
        );

        mockMvc.perform(post("/api/statuses")
                .header("Authorization", headerBearer)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"Новый\"" +
                        "}")
        );

        mockMvc.perform(post("/api/labels")
                .header("Authorization", headerBearer)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"Новая метка\"" +
                        "}")
        );
        mockMvc.perform(post("/api/labels")
                .header("Authorization", headerBearer)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"Новая метка 2\"" +
                        "}")
        );

        mockMvc.perform(post("/api/tasks")
                .header("Authorization", headerBearer)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"Новая задача\"," +
                        "\"description\": \"Какое-то описание\"," +
                        "\"executorId\": 1," +
                        "\"taskStatusId\": 1" +
                        "}")
        );
        mockMvc.perform(post("/api/tasks")
                .header("Authorization", headerBearer)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"name\": \"Новая задача 2\"," +
                        "\"description\": \"Какое-то описание\"," +
                        "\"executorId\": 2," +
                        "\"taskStatusId\": 1," +
                        "\"labelIds\": [2]" +
                        "}")
        );
    }
}
