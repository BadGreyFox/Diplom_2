package praktikum.test.order.create;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.order.CreateOrderReq;
import praktikum.order.CreateOrderResponse;

import java.util.List;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static praktikum.api.OrderAPI.createOrder;
import static praktikum.constance.Errors.INVALID_INGREDIENTS;
import static praktikum.constance.Errors.NO_INGREDIENTS;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedNegativeTest{
    private final String message;
    private final CreateOrderReq req;
    public CreateOrderParameterizedNegativeTest(CreateOrderReq req, String message) {
        this.req = req;
        this.message = message;
    }

    @Parameterized.Parameters()
    public static Object[][] getUsers() {
        return new Object[][]{
                {new CreateOrderReq(List.of()), NO_INGREDIENTS},
                {new CreateOrderReq(List.of("61c0c5a71d1f82001bdaaa61")), INVALID_INGREDIENTS}
        };
    }
    @Test
    @DisplayName("Метод проверки статус-кода при создании заказа с некорректными ингредиентами")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (400)")
    public void checkCreateOrderNegativeStatusCode(){
        createOrder(req).
                then()
                .statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Метод проверки success при создании заказа с некорректными ингредиентами")
    @Description("Метод проверяет соответствие success ожидаемому (false)")
    public void checkCreateOrderNegativeSuccess(){
        assertFalse("Заказ не должен быть создан, success = true",
                createOrder(req).as(CreateOrderResponse.class).isSuccess());
    }
    @Test
    @DisplayName("Метод проверки message при создании заказа с некорректными ингредиентами")
    @Description("Метод проверяет соответствие текста ошибки ожидаемому")
    public void checkCreateOrderNegativeMessage(){
        assertEquals("Сообщение об ошибке не совпадает с ожидаемым",
                message,
                createOrder(req).as(CreateOrderResponse.class).getMessage()
        );
    }
}
