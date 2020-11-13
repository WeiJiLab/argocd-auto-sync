package com.thoughtworks.argocd.autosync.utils;


import org.junit.Assert;
import org.junit.Test;

public class GitOpsYamlUtilTest {

    private final String content = "image:\n" +
            "    pullPolicy: test\n" +
            "    repository: test\n" +
            "    tag: abc.1\n" +
            "readinessProbe:\n" +
            "    tcpSocket:\n" +
            "        port: 3000\n" +
            "replicaCount: 1\n";

    @Test
    public void should_convert_yaml_to_properties_when_given_yaml_string() {

        String expectContent = "image.pullPolicy=test\n" +
                "image.repository=test\n" +
                "image.tag=abc.1\n" +
                "replicaCount=1\n" +
                "readinessProbe.tcpSocket.port=3000\n";

        String resultContent = GitOpsYamlUtil.toProperties(content);
        Assert.assertEquals(resultContent, expectContent);
    }

}