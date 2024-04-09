package praktikum.test.order.create;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.ingredient.Ingredients;
import praktikum.order.CreateOrderResponse;
import praktikum.test.order.OrderTest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;
import static praktikum.api.IngredientAPI.getIngredients;
import static praktikum.test.steps.OrderSteps.setUpOrderNoAuth;

public class CreateOrderWithoutAuthTest extends OrderTest {
    @Before
    public void setUpOrder(){
        Ingredients ingredients = getIngredients().as(Ingredients.class);

        response = setUpOrderNoAuth(ingredients);

        responseBody = response.as(CreateOrderResponse.class);
    }

    @Test
    @DisplayName("Метод проверки статус-кода при создании заказа без авторизации")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkCreateOrderNoAuthUserStatusCode(){
        response.
                then()
                .statusCode(SC_OK);

        assertTrue("Заказ не создан, success = false",
                responseBody.isSuccess());

        assertNotNull("В ответе метода не пришло имя заказа",
                responseBody.getName());

        assertNotEquals("В ответе метода не пришел номер заказа",
                0,
                responseBody.getOrder().getNumber());
    }
}
