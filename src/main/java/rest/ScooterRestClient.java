package rest;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class ScooterRestClient {
    protected final String URL = "https://qa-scooter.praktikum-services.ru";
    protected final RequestSpecification reqSpec = given()
            .baseUri(URL)
            .log().all()
            .header("Content-type", "application/json");
    protected final RequestSpecification reqSpecGet = given()
            .baseUri(URL)
            .log().all();
}
