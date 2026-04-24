package utils;

import java.io.InputStream;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertiesLoader(String fileName) {
        Properties properties = new Properties();

        try (InputStream is = PropertyUtils.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                throw new RuntimeException("File not found: " + fileName);
            }

            properties.load(is);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties file: " + fileName, e);
        }

        return properties;
    }
}