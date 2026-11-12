package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader{
    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            // Ignored intentionally: allows GitHub Actions to inject values via -D System properties
        }
    }

    public static String getProperty(String key) {
        String systemValue = System.getProperty(key);
        return (systemValue != null) ? systemValue : properties.getProperty(key);
    }
}
