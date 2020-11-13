package com.thoughtworks.argocd.autosync.domain;

import java.util.Map;

public class ParaObject {
    private String baseUrl;
    private String token;
    private String filePath;
    private Map<String, String> replaceMap;
    private String defaultBaseBranch;
    private String newBranchName;


    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map<String, String> getReplaceMap() {
        return replaceMap;
    }

    public void setReplaceMap(Map<String, String> replaceMap) {
        this.replaceMap = replaceMap;
    }

    public String getDefaultBaseBranch() {
        return defaultBaseBranch;
    }

    public void setDefaultBaseBranch(String defaultBaseBranch) {
        this.defaultBaseBranch = defaultBaseBranch;
    }


    public String getNewBranchName() {
        return newBranchName;
    }

    public void setNewBranchName(String newBranchName) {
        this.newBranchName = newBranchName;
    }

    public String getContentUrl() {
        return "/contents";
    }

    public String getPullUrl() {
        return baseUrl + "/pulls";
    }

    public String getRefsHeadUrl() {
        return "/git/refs/heads";
    }

    public String getCreatePullRequestTitle() {

        StringBuilder sb = new StringBuilder();
        sb.append("Update ").append(filePath).append(" ");
        replaceMap.forEach((key, value) -> sb.append(key).append(" to ").append(value).append(" "));
        return sb.toString();
    }


    public String getMasterBranchUrl() {
        return getBaseUrl() + getRefsHeadUrl() + "/" + getDefaultBaseBranch();
    }

    public String getCreateBranchByMasterUrl() {
        return baseUrl + "/git/refs";
    }


    public String getDeleteBranchUrl() {
        return baseUrl + getRefsHeadUrl() + "/" + getNewBranchName();
    }

    public String getUpdateDeploymentTagUrl() {
        return baseUrl + getContentUrl() + "/" + getFilePath();
    }


}
