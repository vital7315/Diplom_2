package api;

import io.qameta.allure.*;
import model.User;
import org.junit.After;
import org.junit.Test;
import utils.UserGenerator;

import static org.hamcrest.Matchers.*;

@Epic("Stellar Burgers API")
@Feature("Логин пользователя")
@Owner("Имя")
public class UserLoginTest extends BaseApiTest {
    private UserClient userClient = new UserClient();
    private String accessToken;
    private User user;


    @Test
    @Story("Вход с валидными данными")
    @Description("Пользователь может войти, если указал правильный email и пароль")
    public void loginWithValidCredentialsShouldBeSuccessful() {
        user = UserGenerator.getRandomUser();
        accessToken = userClient.create(user)
                .then()
                .extract()
                .path("accessToken");

        userClient.login(user)
                .then().statusCode(200)
                .body("success", is(true));
    }

    @Test
    @Story("Вход с невалидными данными")
    @Description("Пользователь не может войти с неправильным паролем")
    public void loginWithInvalidCredentialsShouldFail() {
        User invalidUser = new User("fake@mail.ru", "wrongpassword", "TestUser");

        userClient.login(invalidUser)
                .then().statusCode(401)
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