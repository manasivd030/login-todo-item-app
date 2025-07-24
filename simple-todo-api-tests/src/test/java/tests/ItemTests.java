
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

public class ItemTests extends BaseTest {
    static String itemId;
    String itemsEndpoint;
    String invalidIdEndpoint;
    String nonexistentIdEndpoint;

    @BeforeClass
    public void setupItems() {
        itemsEndpoint = ConfigReader.get("endpoint.items");
        invalidIdEndpoint = ConfigReader.get("endpoint.invalid.id");
        nonexistentIdEndpoint = ConfigReader.get("endpoint.nonexistent.id");
    }

    @Test(priority = 1)
    public void testCreateItem() {
        Map<String, String> payload = TestDataHelper.createItemPayload();
        String expectedText = payload.get("text");

        itemId =
                given()
                        .contentType(ContentType.JSON)
                        .body(payload)
                        .when()
                        .post(itemsEndpoint)
                        .then()
                        .statusCode(201)
                        .body("text", equalTo(expectedText))
                        .extract()
                        .path("id");
    }

    @Test(priority = 2)
    public void testGetItems() {
        given()
                .when()
                .get(itemsEndpoint)
                .then()
                .statusCode(200)
                .body("text", notNullValue());  // changed from empty() check
    }

    @Test(priority = 3)
    public void testUpdateItem() {
        Map<String, String> payload = TestDataHelper.updateItemPayload();
        String updatedText = payload.get("text");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(itemsEndpoint + "/" + itemId)
                .then()
                .statusCode(200)
                .body("text", equalTo(updatedText));
    }

    @Test(priority = 4)
    public void testDeleteItem() {
        given()
                .when()
                .delete(itemsEndpoint + "/" + itemId)
                .then()
                .statusCode(204);
    }

    @Test(priority = 5)
    public void testDeleteInvalidItem() {
        given()
                .when()
                .delete(invalidIdEndpoint)
                .then()
                .statusCode(404)
                .body("message", equalTo("Item not found"));
    }

    @Test(priority = 6)
    public void testUpdateInvalidItem() {
        Map<String, String> payload = TestDataHelper.updateItemPayload();

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(nonexistentIdEndpoint)
                .then()
                .statusCode(404)
                .body("message", equalTo("Item not found"));
    }

    @Test(priority = 7)
    public void testCreateItemWithBlankText() {
        Map<String, String> payload = new HashMap<>();
        payload.put("text", "");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(itemsEndpoint)
                .then()
                .statusCode(400)
                .body("message", containsString("Text is required"));
    }

    @Test(priority = 8)
    public void testUpdateItemWithBlankText() {
        // Create valid item first
        Map<String, String> newItemPayload = TestDataHelper.createItemPayload();
        String id =
                given()
                        .contentType(ContentType.JSON)
                        .body(newItemPayload)
                        .when()
                        .post(itemsEndpoint)
                        .then()
                        .statusCode(201)
                        .extract()
                        .path("id");

        Map<String, String> payload = new HashMap<>();
        payload.put("text", "");

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put(itemsEndpoint + "/" + id)
                .then()
                .statusCode(400)
                .body("message", containsString("Text is required"));
    }
}
