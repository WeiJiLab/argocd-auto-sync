package com.thoughtworks.argocd.autosync.utils;

import com.thoughtworks.argocd.autosync.domain.branch.Content;

import java.util.Base64;
import java.util.Map;

public class ContentUtil {
    public static String replaceData(Content content, Map<String, String> replaceMap) {

        String contentByString = content.getContentByString();
        String propsString = GitOpsYamlUtil.toProperties(contentByString);
        String strOfReplace = GitOpsPropertiesUtil.replaceValue(propsString, replaceMap);

        return GitOpsPropertiesUtil.toYaml(strOfReplace);
    }

    public static String replaceDataToBase64(Content content, Map<String, String> replaceMap) {
        String data = ContentUtil.replaceData(content, replaceMap);
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

}
