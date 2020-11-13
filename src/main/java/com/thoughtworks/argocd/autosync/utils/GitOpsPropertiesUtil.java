package com.thoughtworks.argocd.autosync.utils;


import com.thoughtworks.argocd.autosync.utils.props2yaml.Props2Yaml;

import java.util.Arrays;

import java.util.Map;
import java.util.stream.Collectors;


public class GitOpsPropertiesUtil {

    public static String replaceValue(String content, Map<String, String> map) {
        return Arrays.stream(content.split("\\n"))
                .map(line -> {
                            String[] split = line.split("=");
                            if (map.containsKey(split[0])) {
                                return split[0] + "=" + map.get(split[0]);
                            }
                            return line;
                        }
                ).collect(Collectors.joining("\n"));
    }

    public static String toYaml(String content) {
        return Props2Yaml.fromContent(content).convert();
    }

}
