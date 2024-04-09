package praktikum.test.order.create;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.ingredient.Ingredients;
import praktikum.order.CreateOrderResponse;
import praktikum.test.order.OrderTest;
import praktikum.user.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;
import static praktikum.api.IngredientAPI.getIngredients;
import static praktikum.api.UserAPI.deleteUser;
import static praktikum.test.steps.OrderSteps.getTokenForOrder;
import static praktikum.test.steps.OrderSteps.setUpOrderAuth;
import static praktikum.user.User.create;

public class CreateOrderWithAuthTest extends OrderTest {
    private String token;

    private Ingredients ingredients;

    private User user;

    @Before
    public void setUp(){
        ingredients = getIngredients().as(Ingredients.class);

        user = create();

        token = getTokenForOrder(user);

        response = setUpOrderAuth(ingredients, token);

        responseBody = response.as(CreateOrderResponse.class);
    }

    @Test
    @DisplayName("Метод проверки статус-кода при создании заказа с авторизацией")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkCreateOrderAuthUserStatusCode(){
        response.
                then()
                .statusCode(SC_OK);

        assertTrue("Заказ не создан, success = false",
                responseBody.isSuccess());

        assertNotNull("В ответе метода не пришло имя заказа",
                responseBody.getName());

        assertEquals("id ингредиентов не совпадает",
                ingredients
                        .getData()
                        .get(0)
                        .get_id(),
                responseBody
                        .getOrder()
                        .getIngredients()
                        .get(0)
                        .get_id());

        assertNotNull("В ответе метода не пришло id товара",
                responseBody
                        .getOrder()
                        .get_id());

        assertEquals("Имя клиента не совпадает с авторизованным",
                user.getName(),
                responseBody
                        .getOrder()
                        .getOwner()
                        .getName());

        assertEquals("Почта клиента не совпадает с авторизованным",
                user.getEmail()
                        .toLowerCase(),
                responseBody
                        .getOrder()
                        .getOwner()
                        .getEmail());

        assertNotNull("В ответе метода не пришел статус заказа",
                responseBody.getOrder().getStatus());

        assertNotNull("В ответе метода не пришло имя заказа",
                responseBody.getOrder().getName());

        assertNotNull("В ответе метода не пришла дата создания заказа",
                responseBody.getOrder().getCreatedAt());

        assertNotNull("В ответе метода не пришла дата обновления заказа",
                responseBody.getOrder().getUpdatedAt());

        assertNotEquals("В ответе метода не пришел номер заказа",
                0,
                responseBody.getOrder().getNumber());

        assertNotEquals("В ответе метода не пришла итоговая стоимость заказа",
                0,
                responseBody.getOrder().getPrice());
    }

    @After
    public void cleanUp(){
        deleteUser(token);
    }

}
