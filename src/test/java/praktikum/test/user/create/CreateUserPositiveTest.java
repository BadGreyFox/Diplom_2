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
import static praktikum.api.UserAPI.createUser;
import static praktikum.api.UserAPI.deleteUser;
import static praktikum.user.User.create;


public class CreateUserPositiveTest extends UserTest {
    @Before
    public void setAndCreateUser(){
        user = create();
        response = createUser(user);
    }

    @Test
    @DisplayName("Метод проверки создания Пользователя")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (200) и body на соответствие и наличие")
    public void checkCreatingUserStatusCode(){
        response.
                then()
                .statusCode(SC_OK);

        assertTrue("Пользователь не создан, success = false",
                response.as(CreateOrAuthUserResponse.class)
                        .isSuccess());

        assertEquals("Ответ метода вернул некорректные данные по email",
                user.getEmail().toLowerCase(),
                response.as(CreateOrAuthUserResponse.class)
                        .getUser()
                        .getEmail()
                        .toLowerCase());

        assertEquals("Ответ метода вернул некорректные данные по имени",
                user.getName(),
                response.as(CreateOrAuthUserResponse.class)
                        .getUser()
                        .getName());

        assertNotNull("Метод не вернул токен доступа",
                response.as(CreateOrAuthUserResponse.class)
                        .getAccessToken());

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
