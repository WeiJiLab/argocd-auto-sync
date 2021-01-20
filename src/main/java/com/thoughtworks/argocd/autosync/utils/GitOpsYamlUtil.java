package com.thoughtworks.argocd.autosync.utils;

import com.thoughtworks.argocd.autosync.utils.yaml2props.YamlToProps;

public class GitOpsYamlUtil {

    public static String toProperties(String content) {
        return YamlToProps.convertToProp(content);
    }

}
