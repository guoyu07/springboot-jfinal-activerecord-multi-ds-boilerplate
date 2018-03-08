package com.gaols.study.studyboot.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlKit {
    public static String build(String base, String... query) {
        StringBuilder sb = new StringBuilder();
        sb.append(base);

        int i = base.indexOf("?");
        if (i == -1) {
            sb.append("?");
        }

        for (int j = 0; j < query.length; j += 2) {
            if (j > 0 || i > 0) {
                sb.append("&");
            }
            sb.append(query[j]);
            sb.append("=");
            try {
                sb.append(URLEncoder.encode(query[j + 1], "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return sb.toString();
    }

    /**
     * 仅仅将恶心的Checked异常转化为运行时异常。
     */
    public static String encode(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
