package api;

import io.qameta.allure.*;
import model.User;
import org.junit.After;
import org.junit.Test;
import utils.UserGenerator;

import static org.hamcrest.Matchers.*;

@Epic("Stellar Burgers API")
@Feature("Регистрация пользователя")
@Owner("Имя")
public class UserRegistrationTest extends BaseApiTest {
    private UserClient userClient = new UserClient();
    private String accessToken;
    private User user;


    @Test
    @Story("Валидная регистрация")
    @Description("Пользователь может зарегистрироваться с валидными данными")
    public void registrationWithValidDataShouldBeSuccessful() {
        user = UserGenerator.getRandomUser();
        accessToken = userClient.create(user)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .extract()
                .path("accessToken");
    }

    @Test
    @Story("Регистрация уже существующего пользователя")
    @Description("Ошибка, если пользователь уже существует")
    public void registrationWithAlreadyRegisteredUserShouldFail() {
        user = UserGenerator.getRandomUser();
        accessToken = userClient.create(user)
                .then()
                .extract()
                .path("accessToken");

        userClient.create(user)
                .then().statusCode(403)
                .body("success", is(false))
                .body("message", containsString("User already exists"));
    }

    @Test
    @Story("Регистрация без email")
    @Description("Ошибка, если не передан email")
    public void registrationWithoutEmailShouldFail() {
        User user = UserGenerator.getUserWithoutEmail();
        userClient.create(user)
                .then().statusCode(403)
                .body("success", is(false));
    }

    @After
    @Step("Удаление тестового пользователя")
    public void tearDown() {
        try {
            if (accessToken != null) {
                userClient.delete(accessToken);
            }
        } catch (Exception e) {
            System.out.println("Failed to delete user: " + e.getMessage());
        }
    }
}