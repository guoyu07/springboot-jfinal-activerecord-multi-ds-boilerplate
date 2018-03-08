package com.gaols.study.studyboot.utils;

import org.apache.commons.lang3.StringUtils;

public class Strkit extends StringUtils {

    public static String trimNull(String val) {
        return val == null ? "" : val;
    }
}
