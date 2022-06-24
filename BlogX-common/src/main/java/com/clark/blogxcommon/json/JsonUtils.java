package com.clark.blogxcommon.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object o) {
        if (o == null) {
            return null;
        } else {
            try {
                return objectMapper.writeValueAsString(o);
            } catch (JsonProcessingException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    public static <T> T fromJson(String json, Class<T> targetType) {
        if (null == json) {
            return null;
        } else {
            try {
                return objectMapper.readValue(json, targetType);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }
}
