package utils;

import io.qameta.allure.Step;
import model.Order;
import java.util.Collections;
import java.util.List;

public class OrderGenerator {

    @Step("Создание валидного заказа")
    public static Order getValidOrder() {
        List<String> ingredientIds = IngredientHelper.getFirstIngredients(2);
        return new Order(ingredientIds);
    }

    @Step("Создание заказа без ингредиентов")
    public static Order getOrderWithoutIngredients() {
        return new Order(Collections.emptyList());
    }

    @Step("Создание заказа с невалидным ингредиентом")
    public static Order getOrderWithInvalidIngredient() {
        return new Order(Collections.singletonList("invalid_ingredient_id"));
    }
}