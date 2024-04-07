package praktikum.test.user.auth;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.test.user.UserTest;
import praktikum.user.CreateOrAuthUserResponse;
import praktikum.user.User;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static praktikum.api.UserAPI.authUser;
import static praktikum.constance.Errors.WRONG_CREDITS_AUTH;
import static praktikum.user.User.createWithoutEmail;
import static praktikum.user.User.createWithoutPass;

@RunWith(Parameterized.class)
public class AuthUserNegativeTest extends UserTest {
    public AuthUserNegativeTest(User user) {
        super.user = user;
    }
    @Parameterized.Parameters()
    public static Object[][] getUsers() {
        return new Object[][]{
                {createWithoutPass()},
                {createWithoutEmail()}
        };
    }
    @Test
    @DisplayName("Метод проверки статус-кода при авторизации Пользователя с некорректными данными для входа")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (401)")
    public void checkAuthUserNegativeStatusCode(){
        authUser(user).then()
                .statusCode(SC_UNAUTHORIZED);
    }
    @Test
    @DisplayName("Метод проверки параметра success при авторизации Пользователя с некорректными данными для входа")
    @Description("Метод проверяет соответствие параметра success ожидаемому (false)")
    public void checkAuthUserNegativeSuccess(){
        assertFalse(authUser(user)
                .as(CreateOrAuthUserResponse.class)
                .isSuccess());
    }
    @Test
    @DisplayName("Метод проверки параметра message при авторизации Пользователя с некорректными данными для входа")
    @Description("Метод проверяет соответствие текста ошибки ожидаемому")
    public void checkAuthUserNegativeMessage(){
        assertEquals(WRONG_CREDITS_AUTH,
                authUser(user)
                        .as(CreateOrAuthUserResponse.class)
                        .getMessage());
    }
}
