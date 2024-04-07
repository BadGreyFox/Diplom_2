package praktikum.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static praktikum.constance.Config.getSpec;

class BaseAPI {
    protected static Response postRequest(Object json, String api){
        return given()
                .spec(getSpec())
                .and()
                .body(json)
                .when()
                .post(api);
    }
    protected static Response postRequestWithAuth(Object json, String token, String api){
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .and()
                .body(json)
                .when()
                .post(api);
    }
    protected static Response getRequest(String token, String api){
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .when()
                .get(api);
    }
    protected static Response getRequestNoAuth(String api){
        return given()
                .spec(getSpec())
                .when()
                .get(api);
    }
    protected static Response patchRequest(Object json, String token, String api){
        return given()
                .spec(getSpec())
                .auth().oauth2(token)
                .and()
                .body(json)
                .when()
                .patch(api);
    }
    protected static Response patchRequestNoAuth(Object json, String api){
        return given()
                .spec(getSpec())
                .and()
                .body(json)
                .when()
                .patch(api);
    }
    protected static void deleteRequest(String token, String api){
        given()
                .spec(getSpec())
                .auth().oauth2(token)
                .when()
                .delete(api);
    }
}
