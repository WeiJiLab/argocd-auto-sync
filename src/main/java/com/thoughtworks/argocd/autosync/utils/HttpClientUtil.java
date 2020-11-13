package com.thoughtworks.argocd.autosync.utils;

import com.google.gson.Gson;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientUtil {

    private static final CloseableHttpClient client = HttpClients.createDefault();

    private static final Gson gson = new Gson();

    private static final String CHARSET = "utf-8";

    public static <T> T get(String url, String authorization, Class<T> valueType) throws Exception {

        HttpUriRequest httpMethod = HttpUtil.getHttpMethod(url, authorization, HttpGet.class);
        return execute(httpMethod, valueType);
    }

    public static <T> T post(String url, String authorization, String jsonParams, Class<T> valueType) throws IOException, InstantiationException, IllegalAccessException {
        HttpUriRequest httpMethod = HttpUtil.getHttpMethod(url, authorization, jsonParams, HttpPost.class);
        return execute(httpMethod, valueType);
    }

    public static <T> T put(String url, String authorization, String jsonParams, Class<T> valueType) throws IOException, InstantiationException, IllegalAccessException {
        HttpUriRequest httpMethod = HttpUtil.getHttpMethod(url, authorization, jsonParams, HttpPut.class);
        return execute(httpMethod, valueType);
    }

    public static void delete(String url, String authorization) throws IOException, IllegalAccessException, InstantiationException {
        execute(HttpUtil.getHttpMethod(url, authorization, HttpDelete.class));
    }

    private static <T> T execute(HttpUriRequest request, Class<T> valueType) throws IOException {

        String content = EntityUtils.toString(execute(request).getEntity(), CHARSET);
        return gson.fromJson(content, valueType);
    }

    private static CloseableHttpResponse execute(HttpUriRequest request) throws IOException {
        return client.execute(request);
    }
}
