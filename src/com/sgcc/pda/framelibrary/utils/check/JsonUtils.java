package com.sgcc.pda.framelibrary.utils.check;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * JSON解析
 *
 * @author Ben
 */
public class JsonUtils {

    public static void main(String[] args) {
        String json = "{\"deviceId\":\"860820040213092\",\"token\":\"402528589ae-17ea-485d-947e-8718adbbf0c6\",\"params\":\"{\\\"userCode\\\":\\\"402\\\",\\\"appPageName\\\":\\\"com.sgcc.pda.hw.hardwaretest\\\",\\\"functionCode\\\":\\\"HZMeterAuth.getBusiAuth\\\",\\\"paraData\\\":{\\\"appType\\\":\\\"0004\\\",\\\"orderNo\\\":\\\"01\\\",\\\"devceType\\\":\\\"00\\\",\\\"meterAgreement\\\":\\\"03\\\",\\\"taskType\\\":\\\"04\\\",\\\"taskData\\\":{\\\"meterAddress\\\":\\\"000028565862\\\",\\\"endTime\\\":\\\"2019-7-29  18:42:29\\\"}}}\",\"os\":\"android\",\"target\":\"\",\"url\":\"http:\\/\\/10.218.42.43:18012\\/safeauth_service\"}";
        final String params = JsonUtils.getString(json, "params");
        final String paraData = JsonUtils.getString(params, "paraData");
        final String taskData = JsonUtils.getString(paraData, "taskData");

        System.out.println(isPackageName(""));
        System.out.println(isPackageName("com.sgcc.pda-pad_hardware_"));
        System.out.println(taskData);
        System.out.println( getGetBusiAuth(paraData).getAppType());
    }
    public static boolean isPackageName(String packageName){
        String REGEX_PACKAGE =  "([a-zA-Z_][a-zA-Z0-9_]*[.])*([a-zA-Z_][a-zA-Z0-9_]*)$";
        return packageName.matches(REGEX_PACKAGE);
    }

    private static ParamsChecker.GetBusiAuth getGetBusiAuth(String json) {
        try {
          // if (!(json.startsWith("\"") && json.endsWith("\""))){
                return new Gson().fromJson(json, ParamsChecker.GetBusiAuth.class);
         //  }else{

          // }

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
        //return null;
    }
    /**
     * 将字符串转obj化成JSONObject对象
     *
     * @param obj JSON字符串
     * @return
     */
    @NonNull
    public static JSONObject parseJSON(String obj) {
        JSONObject jsonObject = new JSONObject();
        if (notEmpty(obj)) {
            try {
                jsonObject = new JSONObject(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 将字符串array转化成JSONArray数组
     *
     * @param array JSONArray字符串
     * @return
     */
    @NonNull
    public static JSONArray parseArray(String array) {
        JSONArray jsonArray = new JSONArray();
        if (notEmpty(array)) {
            try {
                jsonArray = new JSONArray(array);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }


    /**
     * 通过key获取obj中的String
     *
     * @param obj          JSONObject对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(@Nullable JSONObject obj, String key, String defaultValue) {
        return obj != null ? obj.optString(key, defaultValue) : defaultValue;
    }

    /**
     * 通过key获取obj中的String
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    public static String getString(JSONObject obj, String key) {
        return getString(obj, key, "");
    }

    /**
     * 通过key获取obj中的String
     *
     * @param obj JSONObject字符串
     * @param key
     * @return
     */
    public static String getString(String obj, String key) {
        return getString(parseJSON(obj), key);
    }

    /**
     * 通过position获取array中的String
     *
     * @param array        JSONArray数组
     * @param position
     * @param defaultValue 默认值
     * @return
     */
    public static String getString(@Nullable JSONArray array, int position, String defaultValue) {
        String value = defaultValue;
        if (array != null && array.length() > 0 && array.length() > position) {
            value = array.optString(position, defaultValue);
        }
        return value;
    }

    /**
     * 通过position获取array中的String
     *
     * @param array    JSONArray数组
     * @param position
     * @return
     */
    public static String getString(JSONArray array, int position) {
        return getString(array, position, "");
    }


    /**
     * 通过key获取obj中的JSONObject对象
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    @NonNull
    public static JSONObject getJSONObject(JSONObject obj, String key) {
        //先解析出字符串，再转换成JSONObject
        return parseJSON(getString(obj, key));
    }

    /**
     * 通过key获取obj中的JSONArray数组
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    @NonNull
    public static JSONArray getJSONArray(JSONObject obj, String key) {
        //先解析出字符串，再转换成JSONArray
        return parseArray(getString(obj, key));
    }

    /**
     * 通过position获取array中的JSONObject对象
     *
     * @param array    JSONArray数组
     * @param position
     * @return
     */
    @NonNull
    public static JSONObject getJSONObject(JSONArray array, int position) {
        //先解析出字符串，再转换成JSONObject
        return parseJSON(getString(array, position));
    }


    /**
     * 通过position获取array中的JSONArray数组
     *
     * @param array    JSONArray数组
     * @param position
     * @return
     */
    @NonNull
    public static JSONArray getJSONArray(JSONArray array, int position) {
        //先解析出字符串，再转换成JSONArray
        return parseArray(getString(array, position));
    }


    /**
     * 通过key获取obj中的Object
     *
     * @param obj          JSONObject对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
//    public static Object getObject(JSONObject obj, String key, Object defaultValue) {
//        return obj != null ? obj.opt(key) : defaultValue;
//    }

    /**
     * 通过key获取obj中的Object
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
//    public static Object getObject(JSONObject obj, String key) {
//        return getObject(obj, key, "");
//    }

    /**
     * 通过key获取obj中的boolean
     *
     * @param obj          JSONObject对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static boolean getBoolean(JSONObject obj, String key, boolean defaultValue) {
        boolean value = defaultValue;
        //先解析成字符串
        String valueStr = getString(obj, key);
        if ("1".equals(valueStr) || "true".equals(valueStr)) {
            value = true;
        } else if ("0".equals(valueStr) || "false".equals(valueStr)) {
            value = false;
        }
        return value;
    }

    /**
     * 通过key获取obj中的int
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    public static int getInt(JSONObject obj, String key) {
        return getInt(obj, key, 0);
    }

    /**
     * 通过key获取obj中的int
     *
     * @param obj          JSONObject对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static int getInt(@Nullable JSONObject obj, String key, int defaultValue) {
        return obj != null ? obj.optInt(key, defaultValue) : defaultValue;
    }

    /**
     * 通过key获取中obj的float
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    public static float getFloat(JSONObject obj, String key) {
        return getFloat(obj, key, 0f);
    }

    /**
     * 通过key获取中obj的float
     *
     * @param obj          JSONObject对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static float getFloat(JSONObject obj, String key, float defaultValue) {
        float value = defaultValue;
        String valueStr = getString(obj, key);
        if (notEmpty(valueStr)) {
            try {
                value = Float.parseFloat(valueStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * 通过key获取obj中的double
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    public static double getDouble(JSONObject obj, String key) {
        return getDouble(obj, key, 0);
    }

    /**
     * 通过key获取obj中的double
     *
     * @param obj          JSONObject对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static double getDouble(@Nullable JSONObject obj, String key, double defaultValue) {
        return obj != null ? obj.optDouble(key, defaultValue) : defaultValue;
    }


    /**
     * 通过key获取obj中的long
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    public static long getLong(JSONObject obj, String key) {
        return getLong(obj, key, 0L);
    }

    /**
     * 通过key获取obj中的long
     *
     * @param obj          JSONObject对象
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    public static long getLong(@Nullable JSONObject obj, String key, long defaultValue) {
        return obj != null ? obj.optLong(key, defaultValue) : defaultValue;
    }

    /**
     * 通过key获取obj中的Date
     *
     * @param obj          JSONObject对象
     * @param key
     * @param pattern      格式
     * @param defaultValue 默认值
     * @return
     */
    public static String getDate(@Nullable JSONObject obj, String key, String pattern, String defaultValue) {
        return obj != null ? format(getLong(obj, key), pattern) : defaultValue;
    }

    /**
     * 通过key获取obj中的Date
     *
     * @param obj     JSONObject对象
     * @param key
     * @param pattern 格式
     * @return
     */
    public static String getDate(JSONObject obj, String key, String pattern) {
        return getDate(obj, key, pattern, "");
    }

    /**
     * 通过key获取obj中的默认格式的Date
     *
     * @param obj JSONObject对象
     * @param key
     * @return
     */
    public static String getDate(JSONObject obj, String key) {
        return getDate(obj, key, "");
    }

    /**
     * obj转换String
     *
     * @param obj JSONObject对象
     * @return
     */
    @NonNull
    public static String toString(@Nullable JSONObject obj) {
        String result = "";
        if (obj != null) {
            result = obj.toString();
        }
        return notEmpty(result) ? result : "";
    }

    /**
     * array转换String
     *
     * @param array JSONArray数组
     * @return
     */
    @NonNull
    public static String toString(@Nullable JSONArray array) {
        String result = "";
        if (array != null && array.length() > 0) {
            result = array.toString();
        }
        return notEmpty(result) ? result : "";
    }

    /**
     * JSONArray转换为String数组
     *
     * @param array
     * @return
     */
    @Nullable
    public static String[] toStringArray(@Nullable JSONArray array) {
        String[] strings = null;
        if (array != null) {
            strings = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                strings[i] = getString(array, i);
            }
        }
        return strings;
    }

    /**
     * array转化成List
     *
     * @param array JSONArray数组
     * @return
     */
    @NonNull
    public static List<String> toList(@Nullable JSONArray array) {
        List<String> list = new ArrayList<>();
        if (array != null && array.length() > 0) {
            for (int i = 0; i < array.length(); i++) {
                list.add(getString(array, i));
            }
        }
        return list;
    }

    /**
     * list转换JSONArray数组
     *
     * @param list
     * @return
     */
    @NonNull
    public static JSONArray toJSONArray(@Nullable List<?> list) {
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                array.put(obj);
            }
        }
        return array;
    }

    /**
     * list转换JSONArray数组
     *
     * @param list
     * @return
     */
    @NonNull
    public static String toArrayString(@Nullable Set<?> list) {
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                array.put(obj);
            }
        }
        return toString(array);
    }

    /**
     * key和value转换JSONObject对象
     *
     * @param keyList   key的集合
     * @param valueList value的集合
     * @return
     */
    @NonNull
    public static JSONObject toJSONObject(@Nullable List<String> keyList, @Nullable List<Object> valueList) {
        JSONObject jsonObject = new JSONObject();
        if (keyList != null && keyList.size() > 0 && valueList != null && valueList.size() > 0) {
            for (int i = 0; i < keyList.size(); i++) {
                String key = keyList.get(i);
                Object value = i < valueList.size() ? valueList.get(i) : "";
                if (notEmpty(key)) {
                    if (value == null) {
                        value = "";
                    }
                    try {
                        jsonObject.put(key, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonObject;
    }

    /**
     * Map 转换为 JSONObject
     *
     * @param map
     * @return
     */
    public static JSONObject toJSONObject(Map<String, String> map) {
        JSONObject jsonObject = new JSONObject();
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (notEmpty(key)) {
                    if (value == null) {
                        value = "";
                    }
                    try {
                        jsonObject.put(key, value);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return jsonObject;
    }


    /**
     * 格式化时间
     *
     * @param time
     * @param pattern 格式，默认"yyyy-MM-dd HH:mm:ss"
     * @return
     */
    private static String format(long time, String pattern) {
        if (isEmpty(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (time < 1_000L) {
            return "";
        }
        //PHP时间戳为秒,Java为毫秒
        return new SimpleDateFormat(pattern, Locale.US).format(new Date(time * 1_000L));
    }


    /**
     * 判断str是否 空字符串，"null"，"{}"，"[]"
     *
     * @param value
     * @return
     */
    private static boolean isEmpty(@Nullable String value) {
        return null == value || value.replace("null", "")
                .replace("{", "")
                .replace("}", "")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim().length() == 0;
    }

    /**
     * 判断str是否 不为 空字符串，"null"，"{}"，"[]"
     *
     * @param value
     * @return
     */
    private static boolean notEmpty(String value) {
        return !isEmpty(value);
    }
}
