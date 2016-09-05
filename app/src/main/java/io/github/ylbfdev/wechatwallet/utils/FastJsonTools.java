package io.github.ylbfdev.wechatwallet.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ylbf_ on 2016/9/5.
 */
public class FastJsonTools {
    public FastJsonTools() {

    }

    /**
     * 对单个javabean的解析
     *
     * @param jsonString
     * @param cls
     *
     * @return
     */
    public static <T> T getDataObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return t;
    }

    public static <T> List<T> getDataList(String jsonStriung, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStriung, cls);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }

    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {
            });
        } catch (Exception e) {
            // TODO: handle exception
        }
        return list;
    }
}
