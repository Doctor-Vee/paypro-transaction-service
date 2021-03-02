package com.reloadly.paypro.transactionservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    public static String toJsonString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
