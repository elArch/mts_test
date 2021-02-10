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
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;


public class UserReceptionByIdTest {
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
    @DisplayName("Получение пользователя по его id")
    @AllureId("2")
    @Epic("Получить работу в МТС")
    @Feature("Демо для будущих коллег")
    public void checkUserCreation() {
        CreatableUser newUserModel = userUtils.getRandomActiveMaleUserModel();
        UserResponse createdUser = userHelper.createUser(newUserModel);

        assertThat(createdUser.getCode(),
                allureLogger.logMatcherWithText("Запрос выполнен, код ответа совпадает с ожидаемым", equalTo(201)));
        assertThat(createdUser.getData(),
                allureLogger.logMatcherWithText("Объект создан и не пуст", is(notNullValue())));

        UserResponse returnedUser = userHelper.getUser(createdUser.getData().getId());

        step("Проверяем что метод был выполнен, код ответа и поля обекта совпадают с ожидаемыми", () -> {
            assertAll(
                    () -> assertThat(returnedUser.getCode(),
                            allureLogger.logMatcherWithText("Запрос выполнен, код ответа совпадает с ожидаемым",
                                    equalTo(200))),
                    () -> assertThat(returnedUser.getData().getName(),
                            allureLogger.logMatcherWithText("Имя пользователя совпадает с именем пользователя полученным по id",
                                    equalTo(createdUser.getData().getName()))),
                    () -> assertThat(returnedUser.getData().getEmail(),
                            allureLogger.logMatcherWithText("email пользователя совпадает с email пользователя полученным по id",
                                    equalTo(createdUser.getData().getEmail()))),
                    () -> assertThat(returnedUser.getData().getGender(),
                            allureLogger.logMatcherWithText("gender пользователя совпадает с gender пользователя полученным по id",
                                    equalTo(createdUser.getData().getGender()))),
                    () -> assertThat(returnedUser.getData().getStatus(),
                            allureLogger.logMatcherWithText("status пользователя совпадает с status пользователя полученным по id",
                                    equalTo(createdUser.getData().getStatus()))),
                    () -> assertThat(returnedUser.getData().getCreatedAt(),
                            allureLogger.logMatcherWithText("Дата создания пользователя отлична от NULL", is(notNullValue()))),
                    () -> assertThat(returnedUser.getData().getUpdatedAt(),
                            allureLogger.logMatcherWithText("Дата последнего обновления пользователя отлична от", is(notNullValue()))));
        });
    }
}



