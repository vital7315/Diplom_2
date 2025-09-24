package api;

import io.restassured.response.Response;
import model.Order;
import static io.restassured.RestAssured.given;

public class OrderClient {

    public Response create(Order order, String accessToken) {
        if (accessToken != null) {
            return given()
                    .header("Content-type", "application/json")
                    .header("Authorization", accessToken)
                    .body(order)
                    .post(BaseApiTest.ORDERS_URL);
        } else {
            return given()
                    .header("Content-type", "application/json")
                    .body(order)
                    .post(BaseApiTest.ORDERS_URL);
        }
    }

    public Response get(String accessToken) {
        return given()
                .header("Authorization", accessToken)
                .get(BaseApiTest.ORDERS_URL);
    }

    public Response getWithoutAuth() {
        return given()
                .get(BaseApiTest.ORDERS_URL);
    }
}