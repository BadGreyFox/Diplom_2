package praktikum.test.order;

import io.restassured.response.Response;
import praktikum.order.CreateOrderResponse;

public abstract class OrderTest {
    protected Response response;
    protected CreateOrderResponse responseBody;
}
