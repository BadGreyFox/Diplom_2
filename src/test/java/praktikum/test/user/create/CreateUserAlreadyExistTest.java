package praktikum.test.user.create;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.test.user.UserTest;
import praktikum.user.CreateOrAuthUserResponse;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static praktikum.api.UserAPI.createUser;
import static praktikum.api.UserAPI.deleteUser;
import static praktikum.constance.Errors.ALREADY_EXISTS;

public class CreateUserAlreadyExistTest extends UserTest {
    @Before
    public void setAndCreateUser(){
        setUpUsualUser();
    }
    @Test
    @DisplayName("Метод проверки статус-кода при повторном создании Пользователя с одинаковыми данными")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (403)")
    public void checkCreatingExistingUserStatusCode(){
        createUser(user)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN);
    }
    @Test
    @DisplayName("Метод проверки параметра success при повторном создании Пользователя с одинаковыми данными")
    @Description("Метод проверяет соответствие параметра success ожидаемому (false)")
    public void checkCreatingExistingUserSuccess(){
        assertFalse("Ожидается параметр false",
                createUser(user)
                        .as(CreateOrAuthUserResponse.class)
                        .isSuccess());
    }
    @Test
    @DisplayName("Метод проверки параметра message при повторном создании Пользователя с одинаковыми данными")
    @Description("Метод проверяет соответствие сообщения об ошибке ожидаемому")
    public void checkCreatingExistingUserMessage(){
        assertEquals("Метод вернул некорректное сообщение об ошибке",
                ALREADY_EXISTS,
                createUser(user)
                        .as(CreateOrAuthUserResponse.class)
                        .getMessage());
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
