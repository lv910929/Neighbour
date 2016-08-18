package com.hitotech.neighbour.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lv on 2016/5/24.
 */
public class UrlParseUtil {

    public static Map<String, String> parseUrl(String urlString) {

        Map<String, String> urlMap = new HashMap<>();
        if (!urlString.equals("") && urlString.contains("&")) {
            String[] urlStrings = urlString.split("&");
            for (int i = 0; i < urlStrings.length; i++) {
                String[] detailStrings = urlStrings[i].split("=");
                urlMap.put(StringUtil.decodeString(detailStrings[0]), StringUtil.decodeString(detailStrings[1]));
            }
        }
        return urlMap;
    }

    public static String spliceUrl(Map<String, String> urlMap) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(urlMap.get("url"));
        if (urlMap.size() > 2) {
            stringBuilder.append("?");
            for (Map.Entry<String, String> entry : urlMap.entrySet()) {
                if (entry.getKey().equals("title") || entry.getKey().equals("url")) {
                    continue;
                } else {
                    stringBuilder.append(entry.getKey());
                    stringBuilder.append("=");
                    stringBuilder.append(entry.getValue());
                    stringBuilder.append("&");
                }
            }
        }
        return stringBuilder.toString();
    }

    public static boolean checkNeedReload(String urlString) {
        boolean reult = true;
        Map<String, String> urlMap = new HashMap<>();
        urlMap = parseUrl(urlString);
        for (Map.Entry<String, String> entry : urlMap.entrySet()) {
            if (entry.getValue().equals("1")) {
                reult = false;
                break;
            }
        }
        return reult;
    }
}
