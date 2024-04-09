package praktikum.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import praktikum.user.User;

import static praktikum.api.BaseAPI.*;

public class UserAPI {
    private final static String USER_REGISTRATION = "/api/auth/register";
    private final static String USER_AUTH = "/api/auth/login";
    private final static String USER_INFO = "/api/auth/user";
    @Step("Вызов api создания Пользователя")
    public static Response createUser(User req){
        return postRequest(req, USER_REGISTRATION);
    }
    @Step("Вызов api авторизации Пользователя")
    public static Response authUser(User req){
        return postRequest(req, USER_AUTH);
    }
    @Step("Вызов api изменения Пользователя с токеном")
    public static Response changeUser(User newData, String token){
        return patchRequest(newData, token, USER_INFO);
    }
    @Step("Вызов api изменения Пользователя без токеном")
    public static Response changeUserWithoutAuth(User newData){
        return patchRequestNoAuth(newData, USER_INFO);
    }
    @Step("Вызов api удаления Пользователя")
    public static void deleteUser(String token){
         deleteRequest(token, USER_INFO);
    }

}
