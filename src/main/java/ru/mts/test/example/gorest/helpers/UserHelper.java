package ru.mts.test.example.gorest.helpers;

import gorest.api.users.UsersApi;
import gorest.api.users.model.CreatableUser;
import gorest.api.users.model.UserResponse;
import io.qameta.allure.Step;
import ru.mts.test.example.gorest.utils.TestConfiguration;
import ru.mts.test.example.gorest.utils.UserUtils;

public class UserHelper {
    private final UsersApi usersApi;
    private final UserUtils userUtils;
    private final TestConfiguration testConfiguration;

    public UserHelper(UsersApi usersApi, UserUtils userUtils, TestConfiguration testConfiguration) {
        this.usersApi = usersApi;
        this.userUtils = userUtils;
        this.testConfiguration = testConfiguration;
    }

    @Step("Создаем нового пользователя")
    public UserResponse createUser(CreatableUser request) {
        usersApi.getApiClient().setBearerToken(testConfiguration.getApiProperties().getAuthToken());
        return usersApi.createUser(request);
    }

    @Step("Получаем пользователя по его id")
    public UserResponse getUser(Long userId) {
        return usersApi.getUserById(userId);
    }

    @Step("Получаем список пользователей {page}")
    public void getUserList() {
        usersApi.getUsersList(null);

    }
}