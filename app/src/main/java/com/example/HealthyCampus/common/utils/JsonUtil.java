package com.example.HealthyCampus.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

public final class JsonUtil {

    private static Gson gson = new Gson();

    public static <T> T format(String data, Class<T> cls) {
        if (StringUtil.isEmpty(data)) {
            throw new JsonSyntaxException("Invalid JSON string.");
        }
        return gson.fromJson(data, cls);
    }

    public static Map<String, Object> formatToMap(String data) throws JsonSyntaxException {
        GsonBuilder gb = new GsonBuilder();
        Gson g = gb.create();
        Map<String, Object> map = g.fromJson(data, new TypeToken<Map<String, Object>>() {}.getType());
        return map;
    }

    public static String toJson(Object data) {
        return JsonUtil.gson.toJson(data);
    }


}
