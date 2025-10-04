package api;

import io.qameta.allure.*;
import model.User;
import org.junit.After;
import org.junit.Test;
import utils.UserGenerator;

import static org.hamcrest.Matchers.*;

@Epic("Stellar Burgers API")
@Feature("Обновление данных пользователя")
@Owner("Имя")
public class UserUpdateTest extends BaseApiTest {
    private UserClient userClient = new UserClient();
    private String accessToken;
    private User user;


    @Test
    @Story("Обновление данных с авторизацией")
    @Description("Авторизованный пользователь может обновить email/имя")
    public void updateUserDataWithAuthShouldBeSuccessful() {
        user = UserGenerator.getRandomUser();
        accessToken = userClient.create(user)
                .then()
                .extract()
                .path("accessToken");

        User updatedUser = new User("updated" + System.currentTimeMillis() + "@yandex.ru", "password", "UpdatedName");

        userClient.update(updatedUser, accessToken)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("user.email", equalTo(updatedUser.getEmail()))
                .body("user.name", equalTo(updatedUser.getName()));
    }

    @Test
    @Story("Обновление данных без авторизации")
    @Description("Ошибка, если пользователь не авторизован")
    public void updateUserDataWithoutAuthShouldFail() {
        User updatedUser = new User("test" + System.currentTimeMillis() + "@yandex.ru", "password", "UpdatedName");

        userClient.updateWithoutAuth(updatedUser)
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", containsString("You should be authorised"));
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