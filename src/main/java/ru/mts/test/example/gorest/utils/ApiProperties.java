package ru.mts.test.example.gorest.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class ApiProperties {
    private final String basePath;
    private final String authToken;
}
