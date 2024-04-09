package praktikum.test.order.get;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.order.GetOrdersOnClient;
import praktikum.test.order.OrderTest;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static praktikum.api.OrderAPI.getOrdersOnClient;
import static praktikum.constance.Errors.NON_AUTH_USER;

public class GetOrdersNoAuthUserTest extends OrderTest {

    @Before
    public void setUpResponse(){
        response = getOrdersOnClient();
    }

    @Test
    @DisplayName("Метод проверки статус-кода при получении списка заказов без авторизованного пользователя")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (401)")
    public void checkGetOrdersNoAuthUserStatusCode(){
        response
                .then()
                .assertThat()
                .statusCode(SC_UNAUTHORIZED);

        assertFalse("Заказ не создан, success = false",
                response.as(GetOrdersOnClient.class).isSuccess());

        assertEquals("Сообщение об ошибке не совпадает с ожидаемым",
                NON_AUTH_USER,
                response.as(GetOrdersOnClient.class).getMessage());
    }
}
