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
import static org.junit.Assert.assertTrue;

public class CourierTest {

    private CourierClient courierClient;
    public int courierId;

    @Before
    public void setUp(){
        courierClient = new CourierClient();
    }

    @Test
    @DisplayName("Проверяю возможность создания курьера")
    @Description("Тестирую endpoint: /api/v1/courier")
    public void checkingCourierCanBeCreated(){

        Courier courier = Courier.getRandom();

        ValidatableResponse validatableResponse = courierClient.create(courier);
        boolean courierCreated  = validatableResponse.extract().path("ok");
        courierId = courierClient.login(CourierCredentials.form(courier)).extract().path("id");

        validatableResponse.assertThat().statusCode(SC_CREATED);
        assertTrue("Courier not created", courierCreated);
        assertThat("Courier ID invalid", courierId, is(not(0)));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Проверяю, что нельзя создать двух одинаковых курьеров (с повторяющимся логином)")
    @Description("Тестирую endpoint: /api/v1/courier")
    public void checkingImpossibleCreateTwoIdenticalCouriers(){

        Courier courier = Courier.getRandom();

        courierClient.create(courier);
        courierId = courierClient.login(CourierCredentials.form(courier)).extract().path("id");
        ValidatableResponse validatableResponse = courierClient.create(courier);

        validatableResponse.assertThat().statusCode(SC_CONFLICT);
        validatableResponse.assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Проверяю, что нельзя создать курьера без логина")
    @Description("Тестирую endpoint: /api/v1/courier")
    public void CheckingPossibilityCreatingCourierWithoutLogin(){

        Courier courier = Courier.getRandom(false, true, true);

        courierClient.create(courier);
        ValidatableResponse validatableResponse = courierClient.create(courier);

        validatableResponse.assertThat().statusCode(SC_BAD_REQUEST);
        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверяю, что нельзя создать курьера без пароля")
    @Description("Тестирую endpoint: /api/v1/courier")
    public void CheckingPossibilityCreatingCourierWithoutPassword(){

        Courier courier = Courier.getRandom(true, false, true);

        courierClient.create(courier);
        ValidatableResponse validatableResponse = courierClient.create(courier);

        validatableResponse.assertThat().statusCode(SC_BAD_REQUEST);
        validatableResponse.assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}
