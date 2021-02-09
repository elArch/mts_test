package ru.mts.test.example.gorest.utils;

import com.github.javafaker.Faker;
import gorest.api.users.model.CreatableUser;
import gorest.api.users.model.UserGender;
import gorest.api.users.model.UserStatus;

public class UserUtils {

    private Faker faker = new Faker();

    public CreatableUser getCommonMaleUserModel() {
        CreatableUser model = new CreatableUser();
        model.setName("John Doe");
        model.setEmail("johndoew@never.where");
        model.setGender(UserGender.MALE);
        model.setStatus(UserStatus.ACTIVE);
        return model;
    }

    public CreatableUser getCommonFemaleUserModel() {
        CreatableUser model = new CreatableUser();
        model.setName("Joahn Doe");
        model.setEmail("joahndoew@never.where");
        model.setGender(UserGender.FEMALE);
        model.setStatus(UserStatus.INACTIVE);
        return model;
    }

    public CreatableUser getRandomActiveMaleUserModel() {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String fullName = firstName + " " + lastName;
        String email = firstName + "." + lastName + "@never.where";

        CreatableUser model = new CreatableUser();
        model.setName(fullName);
        model.setEmail(email);
        model.setGender(UserGender.MALE);
        model.setStatus(UserStatus.ACTIVE);
        return model;
    }

    /* Draft
    public int countUsers(UsersResponse usersResponse) {
s
        return 0;
    }
    */
}