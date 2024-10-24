import orders.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step; // Импорт аннотации Step
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

@RunWith(Parameterized.class)
public class OrderCreationTest {

    private final List<String> colorScooter;
    int track;
    OrdersClient orderClient;

    public OrderCreationTest(List<String> colorScooter) {
        this.colorScooter = colorScooter;
    }

    @Parameterized.Parameters
    public static Object[] getOrderCreation() {
        return new Object[][]{
                {List.of()},
                {List.of("BLACK", "GREY")},
                {List.of("GREY")},
                {List.of("BLACK")},
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        orderClient = new OrdersClient();
    }

    @Test
    @DisplayName("Создать заказ с разным цветом скутера")
    public void orderCreateByScooterColor() {
        Order order = Order.createOrderWithColor(colorScooter);
        Response response = createOrder(order);
        track = extractTrack(response);
        Response responseGet = getTrackOrder(track);
        validateOrderStatus(responseGet);
    }

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        Response response = orderClient.sendPostCreateToOrders(order);
        orderClient.compareResponseCodeAndBodyAboutOrderCreation(response);
        return response;
    }

    @Step("Извлечение трека заказа")
    public int extractTrack(Response response) {
        return response.then().extract().path("track");
    }

    @Step("Получение статуса заказа по треку")
    public Response getTrackOrder(int track) {
        return orderClient.sendGetToTrackOrder(track);
    }

    @Step("Проверка статуса заказа")
    public void validateOrderStatus(Response response) {
        orderClient.compareResponse200(response);
    }
}
