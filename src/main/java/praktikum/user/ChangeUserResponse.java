package praktikum.user;

import lombok.Data;

@Data
public class ChangeUserResponse {
    private boolean success;
    private User user;
    private String message;
}
