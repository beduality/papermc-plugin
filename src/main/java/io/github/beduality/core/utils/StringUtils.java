package io.github.beduality.core.utils;

import javax.annotation.Nonnull;

public class StringUtils {

    public static String padEnd(@Nonnull String string, int minLength, char padChar) {
        if (string.length() >= minLength) {
            return string;
        }

        StringBuilder sb = new StringBuilder(minLength);
        sb.append(string);

        for (int i = string.length(); i < minLength; i++) {
            sb.append(padChar);
        }

        return sb.toString();
    }

    public static String padEnd(String string, int minLength) {
        return padEnd(string, minLength, ' ');
    }

    public static String removeSuffix(String string, String suffix) {
        if (string.endsWith(suffix)) {
            return string.substring(0, string.length() - suffix.length());
        }
        
        return string;
    }
}
