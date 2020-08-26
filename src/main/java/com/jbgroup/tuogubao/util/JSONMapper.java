package com.jbgroup.tuogubao.util;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.jbgroup.tuogubao.util.ClassUtil.isClass;
import static com.jbgroup.tuogubao.util.StringUtil.getSetter;

/*
* author: laoma
* map a stringify json object into a specified class
* */
public class JSONMapper<K, B> implements IJSONMapper<K, B> {
    private String jsonStr;
    private K result;
    private Class<K> modelClass;

    public JSONMapper(Class<K> modelClass, String jsonStr) {
        this.modelClass = modelClass;
        this.jsonStr = jsonStr;
    }

    public JSONMapper() {}

    /*
    * the stringified json object would like this
    * "{"key1": "value1", "key2": "value2", "key3": [{"kk1": "vv1"}, {"kk2": "vv2"}], ...}"
    * edge cases:
    * 1. key or value includes ,
    * 2. key or value includes "
    * 3. key or value includes :
    * 4. key or value includes { or }
    * 5. key or value includes "," or ", "
    * 6. inserted and inserted and endless layers of inserted jsons or arrays
    * eg:
    * 1. {"key1": "value 0.5, value 0.5"}
    * 2. {"key1": ""so called value""}
    * 3. {"words": "he said: "go play""}
    * 4. {"key1": "{1, 2, "he said: "go play"}"}
    * 5. {"key1": ""go play", "play yourself"}
    * 6. {"key1": [{"kk1": [{"kkk1": "vvv1"}, {"kkk2": "vvv2"}], {"kk2": "vv2"}}]}
    * is it too extreme? looks really too much
    * it doesn't make a lot sense if we split it by ", "
    *
    * wait, do those nonsense make any sense ?
    * NO
    * JSON.stringify doesn't allow " inside either key or value
    *
    * 1. {"key1": "value 0.5, value 0.5"} -> exists
    * 2. {"key1": ""so called value""} -> nonsense
    * 3. {"words": "he said: "go play""} -> nonsense
    * 4. {"key1": "{1, 2, "he said: "go play"}"} -> nonsense if without the "
    * 5. {"key1": ""go play", "play yourself"} -> nonsense
    * 6. {"key1": [{"kk1": [{"kkk1": "vvv1"}, {"kkk2": "vvv2"}], {"kk2": "vv2"}}]} -> nonsense if without "
    *
    * so, it's a recursively called process
    * value could be 3 formats: string(""), json object({}), array([])
    *
    * after practicing, it's not allowed
    *
    * since JAVA doesn't support runtime checking type for a generic type K, embedded is not allowed
    * */
    private K parseString(String jsonStr) {

        if(jsonStr == null) throw new IllegalArgumentException("no json string detected");
        JSONObject jsonObject = new JSONObject(jsonStr);

        K result = analyzeJSON(jsonObject);
        // map the pairs into the type
        return result;
    }

    public K analyzeJSON(JSONObject jsonObject) {
        K result = null;
        try {
            Class[] params = null;
            result = this.modelClass.getDeclaredConstructor(null).newInstance();
            Field[] fields = this.modelClass.getDeclaredFields();
            boolean hasBuilder = false;
            Class<B> builderClass = null;

            // invoke a Builder of itself
            Class[] innerCls = this.modelClass.getClasses();
            if(innerCls.length > 0) {
                hasBuilder = true;
                builderClass = innerCls[0];
            }

            if(hasBuilder) {
                // if there is a Builder class, use it instead of setters
                B builder = builderClass.getDeclaredConstructor(null).newInstance();
                for(Field field: fields) {
                    // this field is a "key". Transform it to a string
                    String name = field.getName();
                    if(name.equals("_id")) continue;
                    if(!jsonObject.has(name)) continue;
                    String methodName = getSetter(name);
                    // if there is a Builder class, use it instead of setters
                    Method setterMethod = builderClass.getDeclaredMethod(methodName, String.class);
                    setterMethod.invoke(builder, jsonObject.get(name));
                }
                Method buildMethod = builderClass.getMethod("build");
                result = (K)buildMethod.invoke(builder);
            }

            else {
                for(Field field: fields) {
                    // this field is a "key". Transform it to a string
                    String name = field.getName();
                    if(name.equals("_id")) continue;
                    if(!jsonObject.has(name)) continue;
                    String methodName = getSetter(name);
                    // if there is no Builder, use setters
                    // getMethod get public methods
                    // getDeclaredMethod get all kinds of methods
                    Method setterMethod = this.modelClass.getMethod(methodName, String.class);
                    setterMethod.invoke(result, jsonObject.get(name));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public K parse() {
        return this.parseString(this.jsonStr);
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
