package api;

import io.qameta.allure.*;
import model.User;
import model.Order;
import org.junit.After;
import org.junit.Test;
import utils.UserGenerator;
import utils.OrderGenerator;

import static org.hamcrest.Matchers.*;

@Epic("Stellar Burgers API")
@Feature("Получение заказов")
@Owner("Имя")
public class OrderGetTest extends BaseApiTest {
    private OrderClient orderClient = new OrderClient();
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
    @Story("Получение заказов с авторизацией")
    @Description("Пользователь с accessToken может получить список своих заказов")
    public void getOrdersWithAuthShouldBeSuccessful() {
        user = UserGenerator.getRandomUser();
        accessToken = userClient.create(user)
                .then()
                .extract()
                .path("accessToken");

        Order order = OrderGenerator.getValidOrder();
        orderClient.create(order, accessToken)
                .then()
                .statusCode(200)
                .body("success", is(true));

        orderClient.get(accessToken)
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("orders", not(empty()));
    }

    @Test
    @Story("Получение заказов без авторизации")
    @Description("Проверка ошибки при попытке получить заказы без accessToken")
    public void getOrdersWithoutAuthShouldFail() {
        orderClient.getWithoutAuth()
                .then()
                .statusCode(401)
                .body("success", is(false))
                .body("message", containsString("You should be authorised"));
    }
}