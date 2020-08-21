package com.jbgroup.tuogubao.util;

public class StringUtil {

    public static String getSetter(String name) {
        if(name.length() <= 0) throw new IllegalArgumentException("setter must have a name");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        String methodName = String.format("set%s", name);

        return methodName;
    }

    public static String getGetter(String name) {
        if(name.length() <= 0) throw new IllegalArgumentException("getter must have a name");
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        String methodName = String.format("get%s", name);

        return methodName;
    }
}
