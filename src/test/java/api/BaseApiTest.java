package api;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.Before;

public class BaseApiTest {

    protected static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    protected static final String REGISTER_URL = "/api/auth/register";
    protected static final String LOGIN_URL = "/api/auth/login";
    protected static final String USER_URL = "/api/auth/user";
    protected static final String ORDERS_URL = "/api/orders";
    protected static final String INGREDIENTS_URL = "/api/ingredients";

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new AllureRestAssured());
    }
}