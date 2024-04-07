package praktikum.ingredient;

import lombok.Data;

import java.util.List;

@Data
public class Ingredients {
    private boolean success;
    private List <Ingredient> data;
}
