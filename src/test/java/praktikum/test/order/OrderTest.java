package praktikum.test.order;

import io.restassured.response.Response;
import praktikum.ingredient.Ingredients;
import praktikum.order.CreateOrderReq;
import praktikum.order.CreateOrderResponse;
import praktikum.user.CreateOrAuthUserResponse;
import praktikum.user.User;

import java.util.List;

import static praktikum.api.OrderAPI.createOrder;
import static praktikum.api.OrderAPI.createOrderWithAuth;
import static praktikum.api.UserAPI.createUser;

public abstract class OrderTest {
    protected Response response;
    protected CreateOrderResponse responseBody;

    protected String getTokenForOrder(User user){
        return createUser(user)
                .as(CreateOrAuthUserResponse.class)
                .getToken();
    }

    protected Response setUpOrderAuth(Ingredients ingredients, String token){
       return createOrderWithAuth(new CreateOrderReq(
                        List.of(
                                ingredients
                                        .getData()
                                        .get(0)
                                        .get_id(),
                                ingredients
                                        .getData()
                                        .get(1)
                                        .get_id())),
                                token);
    }
    protected Response setUpOrderNoAuth(Ingredients ingredients){
        return createOrder(new CreateOrderReq(
                List.of(
                        ingredients
                                .getData()
                                .get(0)
                                .get_id(),
                        ingredients
                                .getData()
                                .get(1)
                                .get_id())));
    }
}
