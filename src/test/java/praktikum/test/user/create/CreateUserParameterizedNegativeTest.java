package praktikum.test.user.create;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.test.user.UserTest;
import praktikum.user.CreateOrAuthUserResponse;
import praktikum.user.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static praktikum.api.UserAPI.createUser;
import static praktikum.constance.Errors.WRONG_CREDITS_REG;
import static praktikum.user.User.*;

@RunWith(Parameterized.class)
public class CreateUserParameterizedNegativeTest extends UserTest {
    public CreateUserParameterizedNegativeTest(User user) {
        super.user = user;
    }

    @Parameterized.Parameters()
    public static Object[][] getUsers() {
        return new Object[][]{
                {createWithoutPass()},
                {createWithoutEmail()},
                {createWithoutName()}
        };
    }
    @Test
    @DisplayName("Метод проверки создания Пользователя при некорректных данных")
    @Description("Метод проверяет соответствие статус-кода ожидаемому (403) и body на соответствие и наличие")
    public void checkCreatingUserNegativeStatusCode(){
        createUser(user)
                .then()
                .statusCode(SC_FORBIDDEN);

        assertFalse("Ожидается параметр false",
                createUser(user)
                        .as(CreateOrAuthUserResponse.class)
                        .isSuccess());

        assertEquals("Текст ошибки не совпадает с ожидаемым",
                WRONG_CREDITS_REG,
                createUser(user)
                        .as(CreateOrAuthUserResponse.class)
                        .getMessage());
    }
}
