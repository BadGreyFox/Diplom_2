package praktikum.test.user.change;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.test.user.UserTest;
import praktikum.user.ChangeUserResponse;
import praktikum.user.CreateOrAuthUserResponse;
import praktikum.user.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static praktikum.api.UserAPI.*;
import static praktikum.user.User.create;

public class ChangeUserPositiveTest extends UserTest {
    private User user;
    private String token;
    @Before
    public void setCreateAndChangeUser(){
        user = create();
        token = createUser(create())
                .as(CreateOrAuthUserResponse.class)
                .getToken();

        response = changeUser(user, token);
    }
    @Test
    @DisplayName("Метод проверки статус-кода при изменении авторизованного Пользователя")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200)")
    public void checkChangeUserStatusCode(){
        response.
                then()
                .statusCode(SC_OK);
    }
    @Test
    @DisplayName("Метод проверки параметра success при изменении авторизованного Пользователя")
    @Description("Метод проверяет соответствие параметра success ожидаемому (true)")
    public void checkChangeUserSuccess(){
        assertTrue("Пользователь не изменен, success = false",
                response.as(ChangeUserResponse.class).isSuccess());
    }
    @Test
    @DisplayName("Метод проверки email при изменении авторизованного Пользователя")
    @Description("Метод проверяет соответствие выходного параметра email переданному на вход")
    public void checkChangeUserReturnEmail(){
        assertEquals("Ответ метода вернул некорректные данные по email",
                user.
                        getEmail().toLowerCase(),
                response.as(ChangeUserResponse.class).
                        getUser()
                        .getEmail().toLowerCase());
    }
    @After
    @DisplayName("Метод удаления созданного Пользователя")
    @Description("Метод удаляет Пользователя после создания в тестовых методах")
    public void cleanUp(){
        deleteUser(token);
    }

}
