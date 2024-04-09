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
import static praktikum.user.User.create;

public class CreateUserAlreadyExistTest extends UserTest {
    @Before
    public void setAndCreateUser(){
        user = create();
        response = createUser(user);
    }
    @Test
    @DisplayName("Метод проверки создании Пользователей с одинаковыми данными")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (403) и body на соответствие и наличие")
    public void checkCreatingExistingUserStatusCode(){
        createUser(user)
                .then()
                .assertThat()
                .statusCode(SC_FORBIDDEN);

        assertFalse("Ожидается параметр false",
                createUser(user)
                        .as(CreateOrAuthUserResponse.class)
                        .isSuccess());

        assertEquals("Метод вернул некорректное сообщение об ошибке",
                ALREADY_EXISTS,
                createUser(user)
                        .as(CreateOrAuthUserResponse.class)
                        .getMessage());
    }
    @After
    public void cleanUp(){
        deleteUser(response
                .as(CreateOrAuthUserResponse.class)
                .getToken());
    }
}
