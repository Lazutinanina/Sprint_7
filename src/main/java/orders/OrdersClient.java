package orders;
import io.restassured.response.Response;
import rest.ScooterRestClient;
import java.util.List;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;
public class OrdersClient extends ScooterRestClient {
    private final String ORDERS = "/api/v1/orders";
    public Response sendPostCreateToOrders(Order order) {
        return
                reqSpec
                        .and()
                        .body(order)
                        .when()
                        .post(ORDERS);
    }
    public Response sendGetToOrders() {
        return
                reqSpecGet
                        .when()
                        .get(ORDERS);
    }
    public Response sendGetToTrackOrder(int track) {
        String TRACK = "/api/v1/orders/track?t={track}";
        return reqSpecGet
                .pathParam("track", track)
                .when()
                .get(TRACK);
    }
    public void compareResponseCodeAndBodyAboutOrderCreation(Response response) {
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }
    public void compareResponse200(Response response) {
        response.then().assertThat().statusCode(SC_OK);
    }
    public void isResponseBodyHaveOrdersList(Response response) {
        response.then().assertThat().body("orders.id",notNullValue());
        List<String> orderId = response.then().extract().path("orders.id");
        response.then().body("pageInfo.limit", is(orderId.size()));
    }
}
