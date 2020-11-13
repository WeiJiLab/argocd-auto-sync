package com.thoughtworks.argocd.autosync.utils;

import org.apache.http.HttpHeaders;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public class HttpUtil {

    public static HttpUriRequest getHttpMethod(String url, String authorization, Class<? extends HttpRequestBase> requestType) throws IllegalAccessException, InstantiationException {

        HttpRequestBase request = requestType.newInstance();
        request.setURI(URI.create(url));
        setHeader(authorization, request);
        return request;
    }

    public static HttpUriRequest getHttpMethod(String url, String authorization, String jsonParams, Class<? extends HttpEntityEnclosingRequestBase> requestType) throws IllegalAccessException, InstantiationException, UnsupportedEncodingException {
        HttpEntityEnclosingRequestBase request = requestType.newInstance();
        setHeader(authorization, request);
        request.setURI(URI.create(url));
        request.setEntity(new StringEntity(jsonParams));
        return request;
    }

    private static void setHeader(String authorization, HttpRequestBase request) {
        request.addHeader(new BasicHeader("Authorization", "token " + authorization));
        request.addHeader(new BasicHeader(HttpHeaders.ACCEPT, "application/json"));
        request.addHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
    }

}
