package com.thoughtworks.argocd.autosync.utils.yaml2props;

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.list.GrowthList;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.constructor.Constructor;


public class YamlToProps {

    /**
     * yml文件流转成单层map
     * 转Properties 改变了顺序
     */
    public static Map<String, Object> yamlToFlattenedMap(String yamlContent) {
        Yaml yaml = createYaml();
        Map<String, Object> propsMap = new HashMap<>();
        for (Object object : yaml.loadAll(yamlContent)) {
            if (object != null) {
                propsMap = convertToMap(object);
                propsMap = getFlattenedMap(propsMap);
            }
        }
        return propsMap;
    }

    private static Yaml createYaml() {
        return new Yaml(new Constructor());
    }

    public static String convertToProp(String yamlContent) {
        Map<String, Object> stringObjectMap = yamlToFlattenedMap(yamlContent);
        return mapToString(stringObjectMap);
    }

    public static String mapToString(Map<String, Object> map) {
        StringBuilder sb = new StringBuilder();

        map.forEach((key, value) -> sb.append(key)
                .append("=")
                .append(value)
                .append("\n"));
        return sb.toString();
    }


    @SuppressWarnings("unchecked")
    private static Map<String, Object> convertToMap(Object object) {
        Map<String, Object> result = new LinkedHashMap<>();
        if (!(object instanceof Map)) {
            result.put("document", object);
            return result;
        }

        Map<Object, Object> map = (Map<Object, Object>) object;

        for (Map.Entry<Object, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Map) {
                value = convertToMap(value);
            }
            Object key = entry.getKey();
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            } else {
                result.put("[" + key.toString() + "]", value);
            }
        }

        return result;
    }

    private static Map<String, Object> getFlattenedMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    private static void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
        for (Map.Entry<String, Object> entry : source.entrySet()) {
            String key = entry.getKey();
            if (!StringUtils.isBlank(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }
            Object value = entry.getValue();
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection) {
                @SuppressWarnings("unchecked")
                Collection<Object> collection = (Collection<Object>) value;
                int count = 0;
                for (Object object : collection) {
                    buildFlattenedMap(result, Collections.singletonMap("[" + (count++) + "]", object), key);
                }
            } else {
                result.put(key, (value != null ? value.toString() : ""));
            }
        }
    }


    @SuppressWarnings("unchecked")
    private static void buildMultilayerMap(Map<String, Object> parent, String path, Object value) {
        String[] keys = StringUtils.split(path, ".");
        String key = keys[0];
        if (key.endsWith("]")) {
            String listKey = key.substring(0, key.indexOf("["));
            String listPath = path.substring(key.indexOf("["));
            List<Object> child = buildChildList(parent, listKey);
            buildMultilayerList(child, listPath, value);
        } else {
            if (keys.length == 1) {
                parent.put(key, stringToObject(value.toString()));
            } else {
                String newPath = path.substring(path.indexOf(".") + 1);
                Map<String, Object> child = buildChildMap(parent, key);
                ;
                buildMultilayerMap(child, newPath, value);
            }
        }
    }


    @SuppressWarnings("unchecked")
    private static void buildMultilayerList(List<Object> parent, String path, Object value) {
        String[] keys = StringUtils.split(path, ".");
        String key = keys[0];
        int index = Integer.parseInt(key.replace("[", "").replace("]", ""));
        if (keys.length == 1) {
            parent.add(index, stringToObject(value.toString()));
        } else {
            String newPath = path.substring(path.indexOf(".") + 1);
            Map<String, Object> child = buildChildMap(parent, index);
            buildMultilayerMap(child, newPath, value);
        }
    }


    @SuppressWarnings("unchecked")
    private static Map<String, Object> buildChildMap(Map<String, Object> parent, String key) {
        if (parent.containsKey(key)) {
            return (Map<String, Object>) parent.get(key);
        } else {
            Map<String, Object> child = new LinkedHashMap<>(16);
            parent.put(key, child);
            return child;
        }
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> buildChildMap(List<Object> parent, int index) {
        Map<String, Object> child = null;
        try {
            Object obj = parent.get(index);
            if (null != obj) {
                child = (Map<String, Object>) obj;
            }
        } catch (Exception ignored) {
        }

        if (null == child) {
            child = new LinkedHashMap<>(16);
            parent.add(index, child);
        }
        return child;
    }

    @SuppressWarnings("unchecked")
    private static List<Object> buildChildList(Map<String, Object> parent, String key) {
        if (parent.containsKey(key)) {
            return (List<Object>) parent.get(key);
        } else {
            List<Object> child = new GrowthList(16);
            parent.put(key, child);
            return child;
        }
    }

    private static Object stringToObject(String object) {
        Object result;


        if (object.equals("true") || object.equals("false")) {
            result = Boolean.valueOf(object);
        } else if (isBigDecimal(object)) {
            if (!object.contains(".")) {
                result = Long.valueOf(object);
            } else {
                result = Double.valueOf(object);
            }
        } else {
            result = object;
        }
        return result;
    }


    public static boolean isBigDecimal(String str) {

        if (str == null || str.trim().length() == 0) {
            return false;
        }
        char[] chars = str.toCharArray();
        int sz = chars.length;
        int i = (chars[0] == '-') ? 1 : 0;
        if (i == sz) return false;

        if (chars[i] == '.') return false;//除了负号，第一位不能为'小数点'

        boolean radixPoint = false;
        for (; i < sz; i++) {
            if (chars[i] == '.') {
                if (radixPoint) return false;
                radixPoint = true;
            } else if (!(chars[i] >= '0' && chars[i] <= '9')) {
                return false;
            }
        }
        return true;
    }

}
