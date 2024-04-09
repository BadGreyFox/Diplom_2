package praktikum.order;

import lombok.Data;

import java.util.List;

@Data
public class Orders {
    private String _id;
    private List<String> ingredients;
    private Owner owner;
    private String status;
    private String name;
    private String createdAt;
    private String updatedAt;
    private int number;
    private int price;
}
