package api;

import io.qameta.allure.*;
import model.User;
import model.Order;
import org.junit.After;
import org.junit.Test;
import utils.OrderGenerator;
import utils.UserGenerator;

import static org.hamcrest.Matchers.*;

@Epic("Stellar Burgers API")
@Feature("Создание заказов")
@Owner("Имя")
public class OrderCreateTest extends BaseApiTest {
    private OrderClient orderClient = new OrderClient();
    private UserClient userClient = new UserClient();
    private String accessToken;
    private User user;


    @Test
    @Story("Создание заказа с авторизацией")
    @Description("Пользователь с авторизацией может создать заказ")
    public void createOrderWithAuthShouldBeSuccessful() {
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
    }

    @Test
    @Story("Создание заказа без авторизации")
    @Description("Пользователь без авторизации тоже может создать заказ")
    public void createOrderWithoutAuthShouldBeSuccessful() {
        Order order = OrderGenerator.getValidOrder();
        orderClient.create(order, null)
                .then()
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @Story("Создание заказа без ингредиентов")
    @Description("Проверка ошибки при попытке создать заказ без ингредиентов")
    public void createOrderWithoutIngredientsShouldFail() {
        orderClient.create(OrderGenerator.getOrderWithoutIngredients(), null)
                .then()
                .statusCode(400)
                .body("success", is(false))
                .body("message", containsString("Ingredient ids must be provided"));
    }

    @Test
    @Story("Создание заказа с невалидным ингредиентом")
    @Description("Проверка 500 ошибки при несуществующем ингредиенте")
    public void createOrderWithInvalidIngredientShouldFail() {
        orderClient.create(OrderGenerator.getOrderWithInvalidIngredient(), null)
                .then()
                .statusCode(500);
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