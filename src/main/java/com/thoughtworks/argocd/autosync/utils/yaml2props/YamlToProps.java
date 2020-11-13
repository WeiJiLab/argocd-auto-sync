package com.thoughtworks.argocd.autosync.utils.yaml2props;

import org.yaml.snakeyaml.Yaml;

import java.util.HashMap;
import java.util.Map;

public class YamlToProps {

    HashMap<String, Map<String, Object>> config;

    public YamlToProps(String contents) {
        Yaml yaml = new Yaml();
        this.config = yaml.loadAs(contents, HashMap.class);
    }

    public static YamlToProps fromContent(String content) {
        return new YamlToProps(content);
    }

    public String convert() {
        return toProperties(this.config);
    }

    private static String toProperties(final HashMap<String, Map<String, Object>> config) {
        StringBuilder sb = new StringBuilder();
        for (final String key : config.keySet()) {
            sb.append(parseToString(key, config.get(key)));
        }
        return sb.toString();
    }

    private static String parseToString(String key, Object object) {

        StringBuilder sb = new StringBuilder();
        if (object instanceof Map) {
            Map<String, Object> map = (Map<String, Object>) object;
            for (final String mapKey : map.keySet()) {
                if (map.get(mapKey) instanceof Map) {
                    sb.append(parseToString(String.format("%s.%s", key, mapKey), map.get(mapKey)));
                } else {
                    sb.append(String.format("%s.%s=%s%n", key, mapKey, (null == map.get(mapKey)) ? null : map.get(mapKey).toString()));
                }
            }
        } else {
            sb.append(String.format("%s=%s%n", key, object));
        }
        return sb.toString();
    }
}
