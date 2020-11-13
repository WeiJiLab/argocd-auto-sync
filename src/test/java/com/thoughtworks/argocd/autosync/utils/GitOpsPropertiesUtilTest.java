package com.thoughtworks.argocd.autosync.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


public class GitOpsPropertiesUtilTest {

    private final String content = "image.repository=test\n" +
            "image.pullPolicy=test\n" +
            "image.tag=abc.1\n" +
            "replicaCount=1\n" +
            "readinessProbe.tcpSocket.port=3000";

    @Test
    public void should_replace_props_string_when_given_one_content_and_replace_map() {

        Map<String ,String> replaceMap = new HashMap<>();
        replaceMap.put("image.tag", "aaa");
        replaceMap.put("replicaCount", "5");

        String expectContent = "image.repository=test\n" +
                "image.pullPolicy=test\n" +
                "image.tag=aaa\n" +
                "replicaCount=5\n" +
                "readinessProbe.tcpSocket.port=3000";

        String resultContent = GitOpsPropertiesUtil.replaceValue(content, replaceMap);

        Assert.assertEquals(expectContent, resultContent);
    }


    @Test
    public void should_convert_properties_to_yaml_when_given_props_string() {
        String expectContent = "image:\n" +
                "    pullPolicy: test\n" +
                "    repository: test\n" +
                "    tag: abc.1\n" +
                "readinessProbe:\n" +
                "    tcpSocket:\n" +
                "        port: 3000\n" +
                "replicaCount: 1\n";

        String resultContent = GitOpsPropertiesUtil.toYaml(content);

        Assert.assertEquals(expectContent, resultContent);
    }

}