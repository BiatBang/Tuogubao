package com.jbgroup.tuogubao.util;

public class ClassUtil {

    public static boolean isClass(String name){
        try {
            Class.forName(name);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
