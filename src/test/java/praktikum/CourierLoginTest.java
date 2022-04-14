package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private CourierClient courierClient;
    public int courierId;
    Courier courier = Courier.getRandom();

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Проверяю возможность авторизации курьера")
    @Description("Тестирую endpoint: /api/v1/courier/login")
    public void checkingCourierLogin(){
        courierClient.create(courier);

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.login, courier.password));
        courierId = validatableResponse.extract().path("id");

        assertThat("Courier Id invalid", courierId, is(not(0)));
        validatableResponse.assertThat().statusCode(SC_OK);

        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверяю попытку авторизации с невалидным логином")
    @Description("Тестирую endpoint: /api/v1/courier/login")
    public void checkingCourierInvalidLogin(){

        courierClient.create(courier);
        String login = "Иванов Дмитрий Сергеевич";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(login, courier.password));

        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
        validatableResponse.assertThat().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Проверяю попытку авторизации с невалидным паролем")
    @Description("Тестирую endpoint: /api/v1/courier/login")
    public void checkingCourierInvalidPassword(){

        courierClient.create(courier);
        String password = "1234567890";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.login, password));

        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
        validatableResponse.assertThat().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Проверяю авторизацию с несуществующим пользователем")
    @Description("Тестирую endpoint: /api/v1/courier/login")
    public void checkingCourierNotExistent(){

        courierClient.create(courier);
        String login = "   ";
        String password = "123456789";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(login, password));

        validatableResponse.assertThat().body("message", equalTo("Учетная запись не найдена"));
        validatableResponse.assertThat().statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Проверяю авторизацию с отсутствующем логином")
    @Description("Тестирую endpoint: /api/v1/courier/login")
    public void checkingCourierNotLogin(){

        courierClient.create(courier);
        String login = "";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(login, courier.password));

        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
        validatableResponse.assertThat().statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Проверяю авторизацию с отсутствующем логином")
    @Description("Тестирую endpoint: /api/v1/courier/login")
    public void checkingCourierNotPassword(){

        courierClient.create(courier);
        String password = "";

        ValidatableResponse validatableResponse = courierClient.login(new CourierCredentials(courier.login, password));

        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для входа"));
        validatableResponse.assertThat().statusCode(SC_BAD_REQUEST);
    }
}
