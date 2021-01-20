package com.thoughtworks.argocd.autosync.utils.props2yaml;

import org.junit.Assert;
import org.junit.Test;

public class Props2YamlTest  {
    @Test
    public void should_convert_yaml_to_props() {


        String props = "image.pullPolicy=IfNotPresent\n" +
                "image.repository=729601114034.dkr.ecr.ap-northeast-1.amazonaws.com/scp-api\n" +
                "image.tag=8edbacb.15\n" +
                "environments[0].name=SPRING_PROFILES_ACTIVE\n" +
                "environments[0].value=default";

        Props2Yaml props2Yaml = Props2Yaml.fromContent(props);
        String result = props2Yaml.convert();

        String expect =
                "environments:\n" +
                        "-   name: SPRING_PROFILES_ACTIVE\n" +
                        "    value: default\n" +
                        "image:\n" +
                        "    pullPolicy: IfNotPresent\n" +
                        "    repository: 729601114034.dkr.ecr.ap-northeast-1.amazonaws.com/scp-api\n" +
                        "    tag: 8edbacb.15\n";

        Assert.assertEquals(expect, result);
    }
}