package praktikum;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class ScooterRestClient {

    private static final String COURIER_PATH = "http://qa-scooter.praktikum-services.ru/";

    public RequestSpecification getBaseSpec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .setBaseUri(COURIER_PATH)
                .build();
    }
}
