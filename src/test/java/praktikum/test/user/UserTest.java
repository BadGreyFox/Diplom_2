package praktikum.test.user;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.user.User;

import static praktikum.api.UserAPI.createUser;
import static praktikum.user.User.create;

public abstract class UserTest {
    protected User user;

    protected Response response;

    @Step("Метод создания готового Пользователя")
    protected void setUpUsualUser(){
        user = create();
        response = createUser(user);
    }
}
