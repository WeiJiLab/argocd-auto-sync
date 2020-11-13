package com.thoughtworks.argocd.autosync.utils.props2yaml;

import java.io.*;
import java.util.Properties;

public class Props2Yaml {

    private final Properties properties = new Properties();

    public Props2Yaml(String source) {
        try {
            properties.load(new StringReader(source));
        } catch (IOException e) {
            reportError(e);
        }
    }


    public static Props2Yaml fromContent(String content) {
        return new Props2Yaml(content);
    }

    public String convert(boolean useNumericKeysAsArrayIndexes) {
        PropertyTree tree = new TreeBuilder(properties, useNumericKeysAsArrayIndexes).build();
        tree = new ArrayProcessor(tree).apply();
        return tree.toYaml();
    }

    public String convert() {
        return convert(true);
    }

    private void reportError(IOException e) {
        System.out.println(e.getMessage());
    }

}

