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
    }
    @Test
    @DisplayName("Метод проверки success при создании заказа с авторизацией")
    @Description("Метод проверяет соответствие success ожидаемому (true)")
    public void checkCreateOrderAuthUserSuccess(){
        assertTrue("Заказ не создан, success = false",
                responseBody.isSuccess());
    }
    @Test
    @DisplayName("Метод проверки name при создании заказа с авторизацией")
    @Description("Метод проверяет наличие параметра name")
    public void checkCreateOrderAuthUserName(){
        assertNotNull("В ответе метода не пришло имя заказа",
                responseBody.getName());
    }
    @Test
    @DisplayName("Метод проверки параметра id ингредиента при создании заказа с авторизацией")
    @Description("Метод проверяет соответствие в выходных параметрах id ингредиента тому," +
            "который передали на вход при создании заказа")
    public void checkCreateOrderAuthUserOrderIngredients(){
        for (int i = 0; i < 2; i++) {
            assertEquals("id ингредиентов не совпадает",
                    ingredients
                            .getData()
                            .get(i)
                            .get_id(),
                    responseBody
                            .getOrder()
                            .getIngredients()
                            .get(i)
                            .get_id());
        }
    }
    @Test
    @DisplayName("Метод проверки наличия параметра _id заказа при создании заказа с авторизацией")
    @Description("Метод проверяет наличие параметра параметра _id")
    public void checkCreateOrderAuthUserOrderId(){
        assertNotNull("В ответе метода не пришло id товара",
                responseBody
                        .getOrder()
                        .get_id());
    }
    @Test
    @DisplayName("Метод проверки наличия имени клиента при создании заказа с авторизацией")
    @Description("Метод проверяет наличие имени клиента")
    public void checkCreateOrderAuthUserOwnerName(){
        assertEquals("Имя клиента не совпадает с авторизованным",
                user.getName(),
                responseBody
                        .getOrder()
                        .getOwner()
                        .getName());
    }
    @Test
    @DisplayName("Метод проверки наличия почты клиента при создании заказа с авторизацией")
    @Description("Метод проверяет наличие почты клиента")
    public void checkCreateOrderAuthUserOwnerEmail(){
        assertEquals("Почта клиента не совпадает с авторизованным",
                user.getEmail()
                        .toLowerCase(),
                responseBody
                        .getOrder()
                        .getOwner()
                        .getEmail());
    }
    @Test
    @DisplayName("Метод проверки наличия статуса заказа при создании заказа с авторизацией")
    @Description("Метод проверяет наличие статуса заказа")
    public void checkCreateOrderAuthUserOrderStatus(){
        assertNotNull("В ответе метода не пришел статус заказа",
                responseBody.getOrder().getStatus());
    }
    @Test
    @DisplayName("Метод проверки наличия имени заказа при создании заказа с авторизацией")
    @Description("Метод проверяет наличие имени заказа")
    public void checkCreateOrderAuthUserOrderName(){
        assertNotNull("В ответе метода не пришло имя заказа",
                responseBody.getOrder().getName());
    }
    @Test
    @DisplayName("Метод проверки наличия даты создания заказа при создании заказа с авторизацией")
    @Description("Метод проверяет наличие даты создания заказа")
    public void checkCreateOrderAuthUserOrderCreatedAt(){
        assertNotNull("В ответе метода не пришла дата создания заказа",
                responseBody.getOrder().getCreatedAt());
    }
    @Test
    @DisplayName("Метод проверки наличия даты обновления заказа при создании заказа с авторизацией")
    @Description("Метод проверяет наличие даты обновления заказа")
    public void checkCreateOrderAuthUserOrderUpdatedAt(){
        assertNotNull("В ответе метода не пришла дата обновления заказа",
                responseBody.getOrder().getUpdatedAt());
    }
    @Test
    @DisplayName("Метод проверки наличия номера заказа при создании заказа с авторизацией")
    @Description("Метод проверяет наличие номера заказа")
    public void checkCreateOrderAuthUserOwnerNumber(){
        assertNotEquals("В ответе метода не пришел номер заказа",
                0,
                responseBody.getOrder().getNumber());
    }
    @Test
    @DisplayName("Метод проверки наличия итоговой стоимости заказа при создании заказа с авторизацией")
    @Description("Метод проверяет наличие итоговой стоимости заказа")
    public void checkCreateOrderAuthUserOwnerPrice(){
        assertNotEquals("В ответе метода не пришла итоговая стоимость заказа",
                0,
                responseBody.getOrder().getPrice());
    }
    @After
    public void cleanUp(){
        deleteUser(token);
    }

}
