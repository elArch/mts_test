package ru.mts.test.example.gorest.utils;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

public class TestConfiguration {

    @Getter
    private final ApiProperties apiProperties;

    @SneakyThrows
    public TestConfiguration() {
        Configurations configs = new Configurations();
        PropertiesConfiguration properties = configs.properties(new File("test.properties"));

        this.apiProperties = new ApiProperties(
                properties.getString("api.base.url"),
                properties.getString("api.auth.token")
        );
    }
}
