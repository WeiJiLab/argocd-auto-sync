package com.thoughtworks.argocd.autosync.utils;

import java.time.Instant;
import java.util.Random;
import java.util.stream.Stream;

public class RandomUtil {

    private static final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generate() {
        return Instant.now().getEpochSecond() + "-" + getRandomString(8);
    }

    private static String getRandomString(Integer length) {

        Random random = new Random();
        return Stream.generate(() -> str.charAt(random.nextInt(str.length())))
                .limit(length)
                .reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append)
                .toString();
    }

}
