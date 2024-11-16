import courier.*;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Step;
import org.junit.*;
import io.restassured.response.Response;

public class CourierLoginTest {
    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = Courier.getRandom();
        courierClient.postCreateToCourier(courier);
    }

    @Test
    @DisplayName("Проверка входа с валидными данными")
    public void checkCourierCanLoginWithValidCredentialsResponse200() {
        CourierCredentials courierCredentialsCorrect = CourierCredentials.from(courier);
        Response response = loginCourier(courierCredentialsCorrect);
        courierClient.compareLoginResponseAndBodyIdNotNull(response);
    }

    @Test
    @DisplayName("Проверка входа с пустыми логином и паролем")
    public void checkLoginCourierWithInvalidCredentialsResponse400() {
        CourierCredentials courierCredentialsIncorrect = new CourierCredentials("", "");
        Response response = loginCourier(courierCredentialsIncorrect);
        courierClient.compareLoginResponseCodeAndBody400Message(response);
    }

    @Test
    @DisplayName("Проверка входа с пустым логином")
    public void checkLoginCourierWitEmptyLoginResponse400() {
        CourierCredentials courierCredentialsIncorrect = new CourierCredentials("", courier.getPassword());
        Response response = loginCourier(courierCredentialsIncorrect);
        courierClient.compareLoginResponseCodeAndBody400Message(response);
    }

    @Test
    @DisplayName("Проверка входа с пустым паролем")
    public void checkLoginCourierWitEmptyPasswordResponse400() {
        CourierCredentials courierCredentialsIncorrect = new CourierCredentials(courier.getLogin(), "");
        Response response = loginCourier(courierCredentialsIncorrect);
        courierClient.compareLoginResponseCodeAndBody400Message(response);
    }

    @Test
    @DisplayName("Проверка входа с не валидным логином")
    public void checkLoginCourierIncorrectLoginNameResponse404() {
        CourierCredentials courierCredentialsIncorrect = new CourierCredentials("Lazutina", courier.getPassword());
        Response response = loginCourier(courierCredentialsIncorrect);
        courierClient.compareLoginResponseCodeAndBody404Message(response);
    }

    @Test
    @DisplayName("Проверка входа с не валидным паролем")
    public void checkLoginCourierIncorrectPasswordResponse404() {
        CourierCredentials courierCredentialsIncorrect = new CourierCredentials(courier.getLogin(), "Lazutina");
        Response response = loginCourier(courierCredentialsIncorrect);
        courierClient.compareLoginResponseCodeAndBody404Message(response);
    }

    @After
    public void tearDown() {
        CourierCredentials courierCredentialsCorrect = CourierCredentials.from(courier);
        int courierId = courierClient.postToCourierLogin(courierCredentialsCorrect)
                .then().extract().path("id");
        Response response = courierClient.deleteCourier(courierId);
        courierClient.compareDeleteResponseCodeAndBodyOk(response);
    }

    @Step("Авторизация курьера")
    public Response loginCourier(CourierCredentials courierCredentials) {
        return courierClient.postToCourierLogin(courierCredentials);
    }
}
