package utils;

import io.qameta.allure.Step;
import model.User;

public class UserGenerator {

    @Step("Создание случайного пользователя")
    public static User getRandomUser() {
        String email = "test" + System.currentTimeMillis() + "@yandex.ru";
        String password = "password";
        String name = "TestUser";
        return new User(email, password, name);
    }

    @Step("Создание пользователя без email")
    public static User getUserWithoutEmail() {
        return new User(null, "password", "TestUser");
    }

    @Step("Создание пользователя без пароля")
    public static User getUserWithoutPassword() {
        return new User("test" + System.currentTimeMillis() + "@yandex.ru", null, "TestUser");
    }

    @Step("Создание пользователя без имени")
    public static User getUserWithoutName() {
        return new User("test" + System.currentTimeMillis() + "@yandex.ru", "password", null);
    }
}