package praktikum.test.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.ingredient.Ingredients;
import praktikum.order.CreateOrderReq;
import praktikum.user.CreateOrAuthUserResponse;
import praktikum.user.User;

import java.util.List;

import static praktikum.api.OrderAPI.createOrder;
import static praktikum.api.OrderAPI.createOrderWithAuth;
import static praktikum.api.UserAPI.createUser;

public class OrderSteps {
    @Step("Сетап Заказа с авторизацией пользователя")
    public static Response setUpOrderAuth(Ingredients ingredients, String token){
        return createOrderWithAuth(new CreateOrderReq(
                        List.of(
                                ingredients
                                        .getData()
                                        .get(0)
                                        .get_id())),
                token);
    }
    @Step("Сетап Заказа без авторизации пользователя")
    public static Response setUpOrderNoAuth(Ingredients ingredients){
        return createOrder(new CreateOrderReq(
                        List.of(
                                ingredients
                                        .getData()
                                        .get(0)
                                        .get_id())
                )
        );
    }
    @Step("Получение токена пользователя для заказа")
    public static String getTokenForOrder(User user){
        return createUser(user)
                .as(CreateOrAuthUserResponse.class)
                .getToken();
    }
}
