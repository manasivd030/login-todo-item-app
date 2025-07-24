package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
private static final Properties props = new Properties();

static {
    try {
        FileInputStream fis = new FileInputStream("src/test/java/utils/resources/config.properties");
        props.load(fis);
    } catch (IOException e) {
        throw new RuntimeException("Failed to load config.properties file", e);
    }
}

public static String get(String key) {
    return props.getProperty(key);
}
}

