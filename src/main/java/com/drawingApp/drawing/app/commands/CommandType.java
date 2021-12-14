package com.drawingApp.drawing.app.commands;

import java.util.regex.Pattern;

/**
 * This enum defines all available commands with matching regex.
 */
public enum CommandType {
    CANVAS("(C|c)(\\s\\d+\\s\\d+)"),
    LINE("(L|l)(\\s\\d+\\s\\d+\\s\\d+\\s\\d+)"),
    RECTANGLE("(R|r)(\\s\\d+\\s\\d+\\s\\d+\\s\\d+)"),
    BUCKET_FILL("(B|b)(\\s\\d+\\s\\d+\\s[a-zA-z]{1})"),
    QUIT("(Q|q)");

    private final String regEx;
    private final Pattern pattern;

    CommandType(String regEx) {
        this.regEx = regEx;
        this.pattern = Pattern.compile(regEx);
    }

    public String getRegEx() {
        return regEx;
    }

    public Pattern getPattern() {
        return pattern;
    }
}
