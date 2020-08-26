package com.jbgroup.tuogubao.util;

public interface IJSONMapper<K, B> {
    K parse();
    void setJsonStr(String jsonStr);
}
