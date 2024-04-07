package praktikum.test.user.change;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import praktikum.test.user.UserTest;
import praktikum.user.ChangeUserResponse;

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static praktikum.api.UserAPI.changeUserWithoutAuth;
import static praktikum.constance.Errors.NON_AUTH_USER;
import static praktikum.user.User.create;

public class ChangeUserWithoutAuthTest extends UserTest {
    @Before
    public void setCreateAndChangeUser(){
       response = changeUserWithoutAuth(create());
    }
    @Test
    @DisplayName("Метод проверки статус-кода при изменении неавторизованного Пользователя")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (401)")
    public void checkChangeUserStatusCode(){
        response.
                then()
                .statusCode(SC_UNAUTHORIZED);
    }
    @Test
    @DisplayName("Метод проверки параметра success при изменении неавторизованного Пользователя")
    @Description("Метод проверяет соответствие параметра success ожидаемому (false)")
    public void checkChangeUserSuccess(){
        assertFalse("Пользователь не должен быть изменен, success = true",
                response.as(ChangeUserResponse.class).isSuccess());
    }
    @Test
    @DisplayName("Метод проверки параметра message при изменении неавторизованного Пользователя")
    @Description("Метод проверяет соответствие сообщения об ошибке ожидаемому")
    public void checkCreatingUserNegativeMessage(){
        assertEquals(NON_AUTH_USER,
                response
                        .as(ChangeUserResponse.class)
                        .getMessage());
    }
}
