package com.thoughtworks.argocd.autosync.utils.yaml2props;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class YamlToPropsTest {

    @Test
    public void should_convert_yaml_to_props() {

        String yaml =
                "image:\n" +
                        "  pullPolicy: IfNotPresent\n" +
                        "  repository: 729601114034.dkr.ecr.ap-northeast-1.amazonaws.com/scp-api\n" +
                        "  tag: 8edbacb.15\n" +
                        "\n" +
                        "environments:\n" +
                        "  - name: SPRING_PROFILES_ACTIVE\n" +
                        "    value: default";


        Map<String, Object> a = YamlToProps.yamlToFlattenedMap(yaml);
        String result = YamlToProps.mapToString(a);


        String expect =
                "image.pullPolicy=IfNotPresent\n" +
                        "image.repository=729601114034.dkr.ecr.ap-northeast-1.amazonaws.com/scp-api\n" +
                        "image.tag=8edbacb.15\n" +
                        "environments[0].name=SPRING_PROFILES_ACTIVE\n" +
                        "environments[0].value=default\n";

        Assert.assertEquals(expect, result);

    }

}