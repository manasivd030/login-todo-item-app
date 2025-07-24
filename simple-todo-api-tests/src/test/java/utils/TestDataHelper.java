package utils;

import java.util.HashMap;
import java.util.Map;
import utils.ConfigReader;
import com.github.javafaker.Faker;

public class TestDataHelper {

    final static Faker faker = new Faker();

public static Map<String, String> validLoginPayload() {
    
    String username = ConfigReader.get("username");
    String password = ConfigReader.get("password");
   
    Map<String, String> payload = new HashMap<>();
    payload.put("username", username);
    payload.put("password", password);
    return payload;
}

public static Map<String, String> invalidLoginPayload() {
   Map<String, String> payload = new HashMap<>();
        payload.put("username", faker.name().username());  // generates fake username
        payload.put("password", faker.internet().password());  // generates fake password
        return payload;
}

public static Map<String, String> createItemPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("text", "Buy " + faker.food().ingredient()); // e.g. Buy Sugar
        return payload;
    }

    public static Map<String, String> updateItemPayload() {
        Map<String, String> payload = new HashMap<>();
        payload.put("text", "Buy " + faker.food().ingredient());
        return payload;
    }
}