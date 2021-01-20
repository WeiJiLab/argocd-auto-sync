package com.thoughtworks.argocd.autosync.utils;

import com.thoughtworks.argocd.autosync.utils.props2yaml.Props2Yaml;
import com.thoughtworks.argocd.autosync.utils.yaml2props.YamlToProps;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class YamlFileTest {

    @Test
    public void should_convert_yaml_to_props() {
        Map<String, Object> stringObjectMap = YamlToProps.yamlToFlattenedMap(getYamlString());
        String mapToString = YamlToProps.mapToString(stringObjectMap);


        Props2Yaml props2Yaml = Props2Yaml.fromContent(mapToString);
        String result = props2Yaml.convert();

        Assert.assertEquals(getExpect(), result);
    }

    public String getExpect() {
        return "environments:\n" +
                "-   name: AA_BB_CC_DD\n" +
                "    value: default\n" +
                "image:\n" +
                "    pullPolicy: 111111111\n" +
                "    repository: xcdsfsdfdffdf.dkr.ecr.ap-northeast-1.amazonaws.com/scp-api\n" +
                "    tag: sdsfsdffs.15\n" +
                "test:\n" +
                "    earlybird:\n" +
                "        aaaaa:\n" +
                "            bbbbb:\n" +
                "            - cccc1\n" +
                "            - cccc2\n" +
                "            - cccc3\n" +
                "            ddddd:\n" +
                "                eeeee: eeeee\n" +
                "        test: aabbccdd\n" +
                "    system-roles:\n" +
                "        test-ids:\n" +
                "        -   id: 11111\n" +
                "            name: 11111\n" +
                "        -   id: 22222\n" +
                "            name: 22222\n" +
                "        -   id: 33333\n" +
                "            name: 33333\n" +
                "        -   id: 44444\n" +
                "            name: 44444\n" +
                "        -   id: 55555\n" +
                "            name: 55555\n" +
                "        -   id: 66666\n" +
                "            name: 66666\n" +
                "        -   id: 77777\n" +
                "            name: 77777\n" +
                "        -   id: 88888\n" +
                "            name: 88888\n";
    }

    public String getYamlString() {
        return "\n" +
                "image:\n" +
                "  pullPolicy: 111111111\n" +
                "  repository: xcdsfsdfdffdf.dkr.ecr.ap-northeast-1.amazonaws.com/scp-api\n" +
                "  tag: sdsfsdffs.15\n" +
                "\n" +
                "environments:\n" +
                "  - name: AA_BB_CC_DD\n" +
                "    value: default\n" +
                "\n" +
                "test:\n" +
                "  earlybird:\n" +
                "    aaaaa:\n" +
                "      bbbbb:\n" +
                "        - cccc1\n" +
                "        - cccc2\n" +
                "        - cccc3\n" +
                "      ddddd:\n" +
                "        eeeee: \"eeeee\"\n" +
                "    test: \"aabbccdd\"\n" +
                "  system-roles:\n" +
                "    test-ids:\n" +
                "      - id: 11111\n" +
                "        name: 11111\n" +
                "      - id: 22222\n" +
                "        name: 22222\n" +
                "      - id: 33333\n" +
                "        name: 33333\n" +
                "      - id: 44444\n" +
                "        name: 44444\n" +
                "      - id: 55555\n" +
                "        name: 55555\n" +
                "      - id: 66666\n" +
                "        name: 66666\n" +
                "      - id: 77777\n" +
                "        name: 77777\n" +
                "      - id: 88888\n" +
                "        name: 88888\n" +
                "\n";
    }

}
