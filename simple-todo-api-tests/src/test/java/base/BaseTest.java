package base;

import org.testng.annotations.BeforeClass;
import io.restassured.RestAssured;
import utils.ConfigReader;

public class BaseTest {
    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = ConfigReader.get("base.uri");
    }
}
