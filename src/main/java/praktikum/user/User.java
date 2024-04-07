package praktikum.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@AllArgsConstructor
public class User {
    private String email;
    private String name;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static User create(){
        return new User(
                RandomStringUtils.randomAlphabetic(9) + "@gmail.com",
                RandomStringUtils.randomAlphabetic(9),
                RandomStringUtils.randomAlphabetic(9)
        );
    }
    public static User createWithoutEmail(){
        return new User(
                "",
                RandomStringUtils.randomAlphabetic(9),
                RandomStringUtils.randomAlphabetic(9)
        );
    }
    public static User createWithoutPass(){
        return new User(
                RandomStringUtils.randomAlphabetic(9) + "@gmail.com",
                RandomStringUtils.randomAlphabetic(9),
                ""
        );
    }
    public static User createWithoutName(){
        return new User(
                RandomStringUtils.randomAlphabetic(9) + "@gmail.com",
                "",
                RandomStringUtils.randomAlphabetic(9)
        );
    }

}
