
package tests;

import base.BaseTest;
import io.restassured.http.ContentType;
import org.testng.annotations.*;

import utils.ConfigReader;
import utils.TestDataHelper;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class LoginTests extends BaseTest {
    String loginEndpoint;

    @BeforeClass
    public void setupLogin() {
        loginEndpoint = ConfigReader.get("endpoint.login");
    }

    @Test(priority = 1)
    public void testLoginSuccess() {
        Map<String, String> payload = TestDataHelper.validLoginPayload();

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(loginEndpoint)
                .then()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 2)
    public void testLoginFailure() {
        Map<String, String> payload = TestDataHelper.invalidLoginPayload();

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(loginEndpoint)
                .then()
                .statusCode(401)
                .body("success", equalTo(false));
    }

    @Test(priority = 3)
    public void testLoginWithBlankFields() {
        Map<String, String> payload = new HashMap<>();
        payload.put("username", "");
        payload.put("password", "");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(loginEndpoint)
                .then()
                .statusCode(400)
                .body("success", equalTo(false));
    }
}
