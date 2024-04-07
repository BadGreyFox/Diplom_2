package praktikum.constance;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config {
    private final static String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static RequestSpecification getSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }
}
