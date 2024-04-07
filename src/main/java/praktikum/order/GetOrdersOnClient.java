package praktikum.order;

import lombok.Data;

import java.util.List;

@Data
public class GetOrdersOnClient {
    private boolean success;
    private List<Orders> orders;
    private int total;
    private int totalToday;
    private String message;
}
