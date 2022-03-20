package praktikum;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)
public class OrderTest {

    private int orderTrack;
    private final String color;

    Order order = new Order();

    @After
    public void tearDown(){
        order.deleteOrder(orderTrack);
    }

    public OrderTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] ColorOrderCreateParam(){
        return new Object[][]{
                {"BLACK"},
                {"GREY"},
                {"BLACK, GREY"},
                {""},
        };
    }

    @Test
    @DisplayName("Проверяю возможность создания заказа с разными цветами")
    @Description("Тестирую endpoint: /api/v1/orders")
    public void checkingOrderCreateColor() throws JsonProcessingException {

        Response response = order.createOrder(new String[] {color});
        orderTrack = response.then().extract().body().path("track");

        response.then().assertThat().statusCode(SC_CREATED);
        assertThat("Order invalid track", orderTrack, is(not(0)));
    }
}
