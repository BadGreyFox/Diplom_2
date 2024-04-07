package praktikum.test.user.auth;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.test.user.UserTest;
import praktikum.user.CreateOrAuthUserResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;
import static praktikum.api.UserAPI.*;
import static praktikum.user.User.create;

public class AuthUserPositiveTest extends UserTest {
    @Before
    public void setAndCreateUser(){
        user = create();

        createUser(user);

        response = authUser(user);
    }
    @Test
    @DisplayName("Метод проверки статус-кода при авторизации Пользователя")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkAuthUserStatusCode(){
        response.
                then()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Метод проверки параметра success при авторизации Пользователя")
    @Description("Метод проверяет соответствие параметра success ожидаемому (true)")
    public void checkAuthUserSuccess(){
        assertTrue("Пользователь не авторизован, success = false",
                response.as(CreateOrAuthUserResponse.class)
                        .isSuccess());
    }
    @Test
    @DisplayName("Метод проверки email при авторизации Пользователя")
    @Description("Метод проверяет соответствие выходного параметра email переданному на вход")
    public void checkAuthUserReturnEmail(){
        assertEquals("Ответ метода вернул некорректные данные по email",
                user.getEmail()
                        .toLowerCase(),
                response.as(CreateOrAuthUserResponse.class)
                        .getUser()
                        .getEmail()
                        .toLowerCase());
    }
    @Test
    @DisplayName("Метод проверки name при авторизации Пользователя")
    @Description("Метод проверяет соответствие выходного параметра name переданному на вход")
    public void checkAuthUserReturnName(){
        assertEquals("Ответ метода вернул некорректные данные по имени",
                user.getName(),
                response.as(CreateOrAuthUserResponse.class)
                        .getUser()
                        .getName());
    }
    @Test
    @DisplayName("Метод проверки accessToken при авторизации Пользователя")
    @Description("Метод проверяет, что выходных параметрах возвращается токен доступа")
    public void checkAuthUserReturnAccessToken(){
        assertNotNull("Метод не вернул токен доступа",
                response.as(CreateOrAuthUserResponse.class)
                        .getAccessToken());
    }
    @Test
    @DisplayName("Метод проверки refreshToken при авторизации Пользователя")
    @Description("Метод проверяет, что выходных параметрах возвращается токен обновления")
    public void checkAuthUserReturnRefreshToken(){
        assertNotNull("Метод не вернул токен обновления",
                response.as(CreateOrAuthUserResponse.class)
                        .getRefreshToken());
    }
    @After
    public void cleanUp(){
        deleteUser(response
                .as(CreateOrAuthUserResponse.class)
                .getToken());
    }
}
