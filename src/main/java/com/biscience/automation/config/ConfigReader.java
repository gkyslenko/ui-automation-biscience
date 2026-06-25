package com.biscience.automation.config;

import com.biscience.automation.enums.Environment;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public final class ConfigReader {
    public static final Environment ENV = (System.getProperty("env") != null)
            ? Environment.valueOf(System.getProperty("env").toUpperCase())
            : Environment.STAGING;
    public static final Properties PROPERTIES = new Properties();

    static {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream stream = loader.getResourceAsStream("config/" + ENV.toString().toLowerCase() + ".properties");
        InputStream passwordsStream = loader.getResourceAsStream("config/passwords.properties");
        try {
            PROPERTIES.load(stream);
            PROPERTIES.load(passwordsStream);

            for (String key : new String[]{"browser", "headless", "env"}) {
                String value = System.getProperty(key);
                if (value != null) {
                    PROPERTIES.setProperty(key, value);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private ConfigReader() {
    }
}
