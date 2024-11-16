package courier;
import io.restassured.response.Response;
import rest.ScooterRestClient;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.*;
public class CourierClient extends ScooterRestClient {
    private final String ROOT = "/api/v1/courier";
    public Response postToCourierLogin(CourierCredentials courierLogin) {
        String LOGIN = ROOT + "/login";
        return reqSpec
                .and()
                .body(courierLogin)
                .when()
                .post(LOGIN);
    }
    public Response postCreateToCourier(Courier courier) {
        return reqSpec
                .and()
                .body(courier)
                .when()
                .post(ROOT);
    }
    public Response deleteCourier(int courierId) {
        String json = "{\"id\": \"" + courierId + "\"}";
        String DELETE = ROOT + "/{courierId}";
        return reqSpec
                .pathParam("courierId", courierId)
                .and()
                .body(json)
                .when()
                .delete(DELETE);
    }
    public void compareResponseCodeAndBodyAboutCreation(Response response) {
        response.then().assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok", is(true));
    }
    public void compareDeleteResponseCodeAndBodyOk(Response response) {
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("ok", is(true));
    }
    public void compareResponseCodeAndMessageWithError409(Response response) {
        response.then().assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
        //отличается текст startsWith
    }
    public void compareCodeAndMessageWithError400(Response response) {
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
    public void compareLoginResponseAndBodyIdNotNull(Response response) {
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());
    }
    public void compareLoginResponseCodeAndBody400Message(Response response) {
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }
    public void compareLoginResponseCodeAndBody404Message(Response response) {
        response.then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }
}
