/*package praktikum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateOrderColor {

    public String firstName;
    public String lastName;
    public String address;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public String[] color;


    *//*public CreateOrderColor(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;

        Map<String, Object> data = new HashMap<>();
        data.put("firstName", firstName);
        data.put("lastName", lastName);
        data.put("address", "Konoha, 142 apt.");
        data.put("metroStation", 4);
        data.put("phone", "+7 800 355 35 35");
        data.put("rentTime", 5);
        data.put("deliveryDate", "2020-06-06");
        data.put("comment", "Saske, come back to Konoha");
        data.put("color", color);
        String json = new ObjectMapper().writeValueAsString(data);

        return response = given()
                .spec(getBaseSpec())
                .body(json)
                .when()
                .post(ORDER_PATH);
    }*//*

    public CreateOrderColor(Map<String, Object> data) {
        String json = new ObjectMapper().writeValueAsString(data);

        return response = given()
                .spec(getBaseSpec())
                .body(json)
                .when()
                .post(ORDER_PATH);
    }


}*/
