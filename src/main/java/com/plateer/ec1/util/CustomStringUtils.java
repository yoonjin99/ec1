package com.plateer.ec1.util;

import org.h2.util.StringUtils;

import java.util.Arrays;

public class CustomStringUtils{

    public static boolean isNullOrEmpty(String...s) {
        return Arrays.stream(s).noneMatch(str -> str == null || str.isEmpty());
    }
}
