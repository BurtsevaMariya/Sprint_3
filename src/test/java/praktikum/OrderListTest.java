package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OrderListTest {

    Order order = new Order();

    @DisplayName("Проверяю, что список заказов приходит не пустой")
    @Description("Тестирую endpoint: /api/v1/orders")
    @Test
    public void checkingComingNotEmptyList(){
        ValidatableResponse response = order.getOrderList();
        List<Object> orderList = response.extract().jsonPath().getList("orders");

        response.assertThat().statusCode(SC_OK);
        assertThat(orderList.isEmpty(), is(false));
    }
}
