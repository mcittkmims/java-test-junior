package com.java.test.junior.utils;

import java.util.Arrays;
import java.util.List;

public class PublicUris {
    public static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
            "/login",
            "/register",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/error"
    );
}
