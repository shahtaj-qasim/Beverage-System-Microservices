package de.uniba.dsg.jaxrs;

import javax.crypto.spec.OAEPParameterSpec;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Configuration {
    private static final Logger LOGGER = Logger.getLogger(Configuration.class.getName());

    public static Properties loadProperties() {
        try (InputStream stream = ExamplesApi.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties properties = new Properties();
            properties.load(stream);

            Set<String> keys = properties.stringPropertyNames();
            for (String key : keys) {
                Optional<String> environmentVariable = Optional.ofNullable(System.getenv(toUpperCase(key)));
                environmentVariable.ifPresent(newValue -> properties.setProperty(key, newValue));
            }
            return properties;
        } catch (IOException e) {
            LOGGER.severe("Cannot load configuration file.");
            return null;
        }
    }
    private static String toUpperCase(String propertyKey) {
        String[] splitOnUppercase = propertyKey.split("$=[A-Z]");
        return Arrays.stream(splitOnUppercase).map(substring -> substring.toUpperCase()).collect(Collectors.joining("_"));
    }
}
