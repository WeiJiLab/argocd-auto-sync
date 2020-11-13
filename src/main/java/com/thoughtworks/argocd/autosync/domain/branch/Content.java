package com.thoughtworks.argocd.autosync.domain.branch;


import java.util.Base64;


public class Content {

    private String sha;
    private String name;
    private String path;
    private String content;

    public String getContentByString() {
        return new String(Base64.getMimeDecoder().decode(content));
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
