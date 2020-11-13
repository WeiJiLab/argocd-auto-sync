package com.thoughtworks.argocd.autosync.utils;

import com.thoughtworks.argocd.autosync.domain.ParaObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseParasUtil {

    private static final String baseUrl = "https://api.github.com/repos/";

    public static Map<String, String> parseToMap(String paras) {
        String[] split = paras.trim()
                .replace("[", "")
                .replace("]", "")
                .split(",");
        Map<String, String> map = new HashMap<>();

        Arrays.stream(split).forEach(
                it -> {
                    String[] split1 = it.split("=");
                    map.put(split1[0], split1[1]);
                }
        );
        return map;
    }


    public static ParaObject getParaObject(String[] paras, String master) {

        ParaObject paraObject = new ParaObject();
        paraObject.setBaseUrl(baseUrl + paras[0]);
        paraObject.setToken(paras[1]);
        paraObject.setFilePath(paras[2]);
        paraObject.setReplaceMap(parseToMap(paras[3]));
        paraObject.setDefaultBaseBranch(master);

        paraObject.setNewBranchName("branch-" + RandomUtil.generate());
        return paraObject;
    }
}
