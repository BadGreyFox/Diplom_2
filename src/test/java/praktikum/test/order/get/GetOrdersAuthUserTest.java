package praktikum.test.order.get;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.ingredient.Ingredients;
import praktikum.order.GetOrdersOnClient;
import praktikum.test.order.OrderTest;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;
import static praktikum.api.IngredientAPI.getIngredients;
import static praktikum.api.OrderAPI.getOrdersOnClientWithAuth;
import static praktikum.api.UserAPI.deleteUser;
import static praktikum.test.steps.OrderSteps.getTokenForOrder;
import static praktikum.test.steps.OrderSteps.setUpOrderAuth;
import static praktikum.user.User.create;

public class GetOrdersAuthUserTest extends OrderTest {
    private String token;
    private Ingredients ingredients;

    @Before
    public void setUpUserAndOrder(){
        ingredients = getIngredients().as(Ingredients.class);

        token = getTokenForOrder(create());

        setUpOrderAuth(ingredients, token);

        response = getOrdersOnClientWithAuth(token);
    }

    @Test
    @DisplayName("Метод проверки статус-кода при получении списка заказов пользователя")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkGetOrdersAuthUserStatusCode(){
        response.then()
                .assertThat()
                .statusCode(SC_OK);

        assertTrue("Метод не вернул успешный статус вызова, success = false",
                response.as(GetOrdersOnClient.class).isSuccess());

        assertNotEquals("Метод не вернул параметр total",
                0,
                response.as(GetOrdersOnClient.class)
                        .getTotal());

        assertNotEquals("Метод не вернул параметр totalToday",
                0,
                response.as(GetOrdersOnClient.class)
                        .getTotalToday());

        assertNotNull("У заказа отсутствует id",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .get_id());

        assertEquals("id ингредиентов не совпадает",
                ingredients
                        .getData()
                        .get(0)
                        .get_id(),
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getIngredients()
                        .get(0));

        assertNotNull("В ответе метода не пришел статус заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getStatus());

        assertNotNull("В ответе метода не пришло имя заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getName());

        assertNotNull("В ответе метода не пришла дата создания заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getCreatedAt());

        assertNotNull("В ответе метода не пришла дата обновления заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getUpdatedAt());

        assertNotEquals("В ответе метода не пришел номер заказа",
                0,
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getNumber());
    }

    @After
    public void cleanUp(){
        deleteUser(token);
    }
}
