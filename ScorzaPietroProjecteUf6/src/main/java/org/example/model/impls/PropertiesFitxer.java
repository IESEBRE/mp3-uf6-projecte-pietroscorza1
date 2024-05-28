package org.example.model.impls;

import java.io.InputStream;
import java.util.Properties;

/**
 * Classe que llegeix el fitxer de propietats
 */
public class PropertiesFitxer {
    private Properties properties = new Properties();

    public PropertiesFitxer() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("a.properties")) {
            if (input == null) {
                System.out.println("Lo siento, no se pudo encontrar el archivo database.properties");
                return;
            }
            // Cargar el archivo properties
            properties.load(input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getUrl() {
        return properties.getProperty("db.url");
    }

    public String getUsername() {
        return properties.getProperty("db.username");
    }

    public String getPassword() {
        return properties.getProperty("db.password");
    }
}
