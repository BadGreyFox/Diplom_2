package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static praktikum.api.BaseAPI.getRequest;
import static praktikum.api.BaseAPI.getRequestNoAuth;

public class IngredientAPI {
    private static final String GET_INGREDIENTS = "/api/ingredients";
    @Step("Вызов api получения списка Ингредиентов")
    public static Response getIngredients(){
        return getRequestNoAuth(GET_INGREDIENTS);
    }
}
