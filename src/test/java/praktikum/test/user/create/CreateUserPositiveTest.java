package praktikum.test.user.create;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.test.user.UserTest;
import praktikum.user.CreateOrAuthUserResponse;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.*;
import static praktikum.api.UserAPI.deleteUser;

public class CreateUserPositiveTest extends UserTest {
    @Before
    public void setAndCreateUser(){
        setUpUsualUser();
    }

    @Test
    @DisplayName("Метод проверки статус-кода при создании Пользователя")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkCreatingUserStatusCode(){
        response.
                then()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Метод проверки параметра success при создании Пользователя")
    @Description("Метод проверяет соответствие параметра success ожидаемому (true)")
    public void checkCreatingUserSuccess(){
        assertTrue("Пользователь не создан, success = false",
                response.as(CreateOrAuthUserResponse.class)
                        .isSuccess());
    }
    @Test
    @DisplayName("Метод проверки email при создании Пользователя")
    @Description("Метод проверяет соответствие выходного параметра email переданному на вход")
    public void checkCreatingUserReturnEmail(){
        assertEquals("Ответ метода вернул некорректные данные по email",
                user.getEmail().toLowerCase(),
                response.as(CreateOrAuthUserResponse.class)
                        .getUser()
                        .getEmail()
                        .toLowerCase());
    }
    @Test
    @DisplayName("Метод проверки name при создании Пользователя")
    @Description("Метод проверяет соответствие выходного параметра name переданному на вход")
    public void checkCreatingUserReturnName(){
        assertEquals("Ответ метода вернул некорректные данные по имени",
                user.getName(),
                response.as(CreateOrAuthUserResponse.class)
                        .getUser()
                        .getName());
    }
    @Test
    @DisplayName("Метод проверки accessToken при создании Пользователя")
    @Description("Метод проверяет, что выходных параметрах возвращается токен доступа")
    public void checkCreatingUserReturnAccessToken(){
        assertNotNull("Метод не вернул токен доступа",
                response.as(CreateOrAuthUserResponse.class)
                        .getAccessToken());
    }
    @Test
    @DisplayName("Метод проверки refreshToken при создании Пользователя")
    @Description("Метод проверяет, что выходных параметрах возвращается токен обновления")
    public void checkCreatingUserReturnRefreshToken(){
        assertNotNull("Метод не вернул токен обновления",
                response.as(CreateOrAuthUserResponse.class)
                        .getRefreshToken());
    }
    @After
    @DisplayName("Метод удаления созданного Пользователя")
    @Description("Метод удаляет Пользователя после создания в тестовых методах")
    public void cleanUp(){
        deleteUser(response
                .as(CreateOrAuthUserResponse.class)
                .getToken());
    }
}
