package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.User;
import static io.restassured.RestAssured.given;

public class UserClient {

    @Step("Создание пользователя")
    public Response create(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BaseApiTest.REGISTER_URL);
    }

    @Step("Логин пользователя")
    public Response login(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BaseApiTest.LOGIN_URL);
    }

    @Step("Обновление данных пользователя")
    public Response update(User user, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(user)
                .patch(BaseApiTest.USER_URL);
    }

    @Step("Обновление данных пользователя без авторизации")
    public Response updateWithoutAuth(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .patch(BaseApiTest.USER_URL);
    }

    @Step("Удаление пользователя")
    public Response delete(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .delete(BaseApiTest.USER_URL);
    }
}