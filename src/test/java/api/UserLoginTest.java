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

    @After
    public void tearDown() {
        if (accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    @Test
    @Story("Вход с валидными данными")
    @Description("Пользователь может войти, если указал правильный email и пароль")
    public void loginWithValidCredentialsShouldBeSuccessful() {
        user = UserGenerator.getRandomUser();
        userClient.create(user);

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
}