package fr.uparis.backapp.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Config instance = null;
    private static Properties prop;

    private Config() {
        prop = new Properties();
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            prop.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors du chargement des propriétés", e);
        }
    }

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
