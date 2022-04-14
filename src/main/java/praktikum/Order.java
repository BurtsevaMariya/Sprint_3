package praktikum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Order extends ScooterRestClient{

    Response response;
    private static final String ORDER_PATH = "api/v1/orders/";

    @Step("Создание заказа")
public Response createOrderColor(Map<String, Object> data) throws JsonProcessingException {

        String json = new ObjectMapper().writeValueAsString(data);

            return response = given()
                    .spec(getBaseSpec())
                    .body(json)
                    .when()
                    .post(ORDER_PATH);
        }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList(){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse deleteOrder(int orderTrack) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(ORDER_PATH + "cancel/")
                .then();
    }
}
