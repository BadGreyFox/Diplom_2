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
    }
    @Test
    @DisplayName("Метод проверки параметра success при получении списка заказов пользователя")
    @Description("Метод проверяет соответствие параметра success ожидаемому (true)")
    public void checkGetOrdersAuthUserSuccess(){
        assertTrue("Метод не вернул успешный статус вызова, success = false",
                response.as(GetOrdersOnClient.class).isSuccess());
    }
    @Test
    @DisplayName("Метод проверки наличия total при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра total")
    public void checkGetOrdersAuthUserTotal(){
        assertNotEquals("Метод не вернул параметр total",
                0,
                response.as(GetOrdersOnClient.class)
                        .getTotal());
    }
    @Test
    @DisplayName("Метод проверки наличия параметра totalToday при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра totalToday")
    public void checkGetOrdersAuthUserTotalToday(){
        assertNotEquals("Метод не вернул параметр totalToday",
                0,
                response.as(GetOrdersOnClient.class)
                        .getTotalToday());
    }
    @Test
    @DisplayName("Метод проверки наличия параметра _id заказа при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра параметра _id")
    public void checkGetOrdersAuthUserOrderId(){
        assertNotNull("У заказа отсутствует id",
                response.as(GetOrdersOnClient.class)
                .getOrders()
                .get(0)
                .get_id());
    }
    @Test
    @DisplayName("Метод проверки параметра id ингредиента при получении списка заказов пользователя")
    @Description("Метод проверяет соответствие в выходных параметрах id ингредиента тому," +
            "который передали на вход при создании заказа")
    public void checkGetOrdersAuthUserOrderIngredients(){
        for (int i = 0; i < 2; i++) {
            assertEquals("id ингредиентов не совпадает",
                    ingredients
                            .getData()
                            .get(i)
                            .get_id(),
                    response.as(GetOrdersOnClient.class)
                            .getOrders()
                            .get(0)
                            .getIngredients()
                            .get(i));
        }
    }
    @Test
    @DisplayName("Метод проверки параметра status заказа при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра status")
    public void checkGetOrdersAuthUserOrderStatus(){
        assertNotNull("В ответе метода не пришел статус заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getStatus());
    }
    @Test
    @DisplayName("Метод проверки параметра name заказа при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра name")
    public void checkGetOrdersAuthUserOrderName(){
        assertNotNull("В ответе метода не пришло имя заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getName());
    }
    @Test
    @DisplayName("Метод проверки наличия параметра createdAt заказа при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра createdAt")
    public void checkGetOrdersAuthUserOrderCreatedAt(){
        assertNotNull("В ответе метода не пришла дата создания заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getCreatedAt());
    }
    @Test
    @DisplayName("Метод проверки наличия параметра updatedAt заказа при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра updatedAt")
    public void checkGetOrdersAuthUserOrderUpdatedAt(){
        assertNotNull("В ответе метода не пришла дата обновления заказа",
                response.as(GetOrdersOnClient.class)
                        .getOrders()
                        .get(0)
                        .getUpdatedAt());
    }
    @Test
    @DisplayName("Метод проверки наличия параметра number заказа при получении списка заказов пользователя")
    @Description("Метод проверяет наличия параметра number")
    public void checkCreateOrderAuthUserOwnerNumber(){
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
