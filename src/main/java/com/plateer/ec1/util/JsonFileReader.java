package com.plateer.ec1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class JsonFileReader {
    private static final String PATH = "./src/main/resources/";

    public static <T> T getObject(String fileName, Class<T> value) {
        Path file = Paths.get(PATH + fileName);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // json의 모든 데이터를 파싱하는것이 아닌 내가 필드로 선언한 데이터들만 파싱할 수 있게 해줌

        T t = null;

        try {
            t = mapper.readValue(file.toFile(), value);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }

        return t;
    }

    public static <T> T getObject(String fileName, TypeReference<T> typeReference) {
        Path file = Paths.get(PATH + fileName);
        ObjectMapper mapper = new ObjectMapper();

        T t=null;
        try {
            t = mapper.readValue(file.toFile(), typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return t;
    }

    public static String parseToJson(Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.registerModule(new JavaTimeModule()).writeValueAsString(obj);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}

