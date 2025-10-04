package utils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.List;

public class IngredientHelper {

    @Step("Получение первых ингредиентов")
    public static List<String> getFirstIngredients(int count) {
        Response response = RestAssured
                .get("https://stellarburgers.nomoreparties.site/api/ingredients")
                .then()
                .statusCode(200)
                .extract()
                .response();
        List<String> ingredients = response.path("data._id");
        return ingredients.subList(0, Math.min(count, ingredients.size()));
    }
}