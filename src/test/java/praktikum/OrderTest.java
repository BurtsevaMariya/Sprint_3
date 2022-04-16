package praktikum;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(Parameterized.class)

public class OrderTest {
    final Map<String, Object> data;
    private int orderTrack;
    Order order = new Order();

    public OrderTest(Map<String, Object> data) {
        this.data = data;
    }

    @After
    public void tearDown() {
        order.deleteOrder(orderTrack);
    }

    @Parameterized.Parameters
    public static Object[][] ColorOrderCreateParam() {
        Map<String, Object> data1 = new HashMap<>();
        data1.put("firstName", "Naruto");
        data1.put("lastName", "Uchiha");
        data1.put("address", "Konoha, 142 apt.");
        data1.put("metroStation", "4");
        data1.put("phone", "+7 800 355 35 35");
        data1.put("rentTime", 5);
        data1.put("deliveryDate", "2020-06-06");
        data1.put("comment", "Saske, come back to Konoha");

        Map<String, Object> data2 = new HashMap<>(data1);
        Map<String, Object> data3 = new HashMap<>(data1);
        Map<String, Object> data4 = new HashMap<>(data1);
        Map<String, Object> data5 = new HashMap<>(data1);

        String[] color1 = {"BLACK"};
        String[] color2 = {"GREY"};
        String[] color3 = {"BLACK", "GREY"};
        String[] color4 = {null};
        String[] color5 = {""};

        data1.put("color", color1);
        data2.put("color", color2);
        data3.put("color", color3);
        data4.put("color", color4);
        data5.put("color", color5);

        return new Object[][]{
                {data1},
                {data2},
                {data3},
                {data4},
                {data5}
        };
    }

    @Test
    @DisplayName("Проверяю возможность создания заказа с разными цветами")
    @Description("Тестирую endpoint: /api/v1/orders")
    public void checkingOrderCreateColor() throws JsonProcessingException {
        System.out.println(data.get("color"));
        Response response = order.createOrderColor(data);
        orderTrack = response.then().extract().body().path("track");
        response.then().assertThat().statusCode(SC_CREATED);
        assertThat("Order invalid track", orderTrack, is(not(0)));
    }
}
