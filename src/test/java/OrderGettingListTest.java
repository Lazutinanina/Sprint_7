import orders.OrdersClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

public class OrderGettingListTest {
    OrdersClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrdersClient();
    }
    @Test
    @DisplayName("Проверка, что список заказов содержится в теле ответа")
    public void checkListOfOrdersContainedInResponse() {
        Response response = orderClient.sendGetToOrders();
        orderClient.compareResponse200(response);
        orderClient.isResponseBodyHaveOrdersList(response);
    }
}
