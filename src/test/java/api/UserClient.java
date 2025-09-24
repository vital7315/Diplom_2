package api;

import io.restassured.response.Response;
import model.User;
import static io.restassured.RestAssured.given;

public class UserClient {

    public Response create(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BaseApiTest.REGISTER_URL);
    }

    public Response login(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BaseApiTest.LOGIN_URL);
    }

    public Response update(User user, String accessToken) {
        return given()
                .header("Content-type", "application/json")
                .header("Authorization", accessToken)
                .body(user)
                .patch(BaseApiTest.USER_URL);
    }

    public Response updateWithoutAuth(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .patch(BaseApiTest.USER_URL);
    }

    public Response delete(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .delete(BaseApiTest.USER_URL);
    }
}