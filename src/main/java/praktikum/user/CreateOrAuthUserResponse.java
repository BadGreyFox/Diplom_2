package praktikum.user;

import lombok.Data;

@Data
public class CreateOrAuthUserResponse {
    private boolean success;
    private User user;
    private String accessToken;
    private String refreshToken;
    private String message;

    public String getToken(){
        return accessToken.substring(7);
    }
}
