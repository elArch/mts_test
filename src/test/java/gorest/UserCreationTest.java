package gorest;

import gorest.api.client.ApiClient;
import gorest.api.users.UsersApi;
import gorest.api.users.model.CreatableUser;
import gorest.api.users.model.UserResponse;
import io.qameta.allure.AllureId;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.mts.test.example.gorest.helpers.UserHelper;
import ru.mts.test.example.gorest.utils.AllureLogger;
import ru.mts.test.example.gorest.utils.TestConfiguration;
import ru.mts.test.example.gorest.utils.UserUtils;

import static io.qameta.allure.Allure.step;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;


public class UserCreationTest {

    UsersApi usersApi;
    UserUtils userUtils;
    UserHelper userHelper;
    AllureLogger allureLogger;

    @BeforeEach
    void setUp() {
        TestConfiguration testConfiguration = new TestConfiguration();
        ApiClient apiClient = new ApiClient();
        usersApi = new UsersApi(apiClient);
        userUtils = new UserUtils();
        userHelper = new UserHelper(usersApi, userUtils, testConfiguration);
        allureLogger = new AllureLogger();
    }

    @Test
    @DisplayName("Создание нового пользователя")
    @AllureId("1")
    @Epic("Получить Работу в МТС")
    @Feature("Демо для будущих коллег")

    public void checkUserCreation() {
        //log.info("HELLO WORLD");
        //UserResponse response = userHelper.createUser(userUtils.getCommonFemaleUserModel());
        //UserResponse response = userHelper.createUser(userUtils.getRandomActiveMaleUserModel());

        CreatableUser newUserModel = userUtils.getRandomActiveMaleUserModel();
        UserResponse response = userHelper.createUser(newUserModel);

        step("Проверяем что метод был выполнен, код ответа и поля обекта совпадают с ожидаемыми", () ->
                {
                    assertAll(
                            () -> assertThat(response.getCode(),
                                    allureLogger.logMatcherWithText("Запрос выполнен, код ответа совпадает с ожидаемым",
                                            equalTo(201))),

                            () -> assertThat(response.getData().getName(),
                                    allureLogger.logMatcherWithText("Имя пользователя из ответа совпадает с именем из запроса",
                                            equalTo(newUserModel.getName()))),

                            () -> assertThat(response.getData().getEmail(),
                                    allureLogger.logMatcherWithText("email пользователя из ответа совпадает с email из запроса",
                                            equalTo(newUserModel.getEmail()))),

                            () -> assertThat(response.getData().getGender(),
                                    allureLogger.logMatcherWithText("gender пользователя из ответа совпадает с gender из запроса",
                                            equalTo(newUserModel.getGender()))),

                            () -> assertThat(response.getData().getStatus(),
                                    allureLogger.logMatcherWithText("status пользователя из ответа совпадает с status из запроса",
                                            equalTo(newUserModel.getStatus()))));
                }
        );
    }
}
