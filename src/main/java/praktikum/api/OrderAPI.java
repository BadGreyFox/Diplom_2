package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.order.CreateOrderReq;

import static praktikum.api.BaseAPI.*;

public class OrderAPI {
    private static final String CREATE_OR_GET_ORDER = "/api/orders";
    @Step("Вызов api создания Заказа с токеном")
    public static Response createOrderWithAuth(CreateOrderReq req, String token){
        return postRequestWithAuth(req, token, CREATE_OR_GET_ORDER);
    }
    @Step("Вызов api создания Заказа без токена")
    public static Response createOrder(CreateOrderReq req){
        return postRequest(req, CREATE_OR_GET_ORDER);
    }
    @Step("Вызов api получения списка Заказов по конкретному пользователю с токеном")
    public static Response getOrdersOnClientWithAuth(String token){
        return getRequest(token, CREATE_OR_GET_ORDER);
    }
    @Step("Вызов api получения списка Заказов по конкретному пользователю без токена")
    public static Response getOrdersOnClient(){
        return getRequestNoAuth(CREATE_OR_GET_ORDER);
    }
}
