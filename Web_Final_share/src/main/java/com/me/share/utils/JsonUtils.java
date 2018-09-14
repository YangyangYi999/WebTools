package com.me.share.utils;


public class JsonUtils {

    public static String toJsonField(String name, String value) {
        return "\"" + name + "\":\"" + value + "\"";
    }
    
    public static String toJsonField(String name, boolean value) {
        return "\"" + name + "\":\"" + value + "\"";
    }
}
