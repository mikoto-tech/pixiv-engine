package net.mikoto.pixiv.engine.util;

import org.jetbrains.annotations.NotNull;

/**
 * @author mikoto2464
 */
public class CharUtil {
    /**
     * 字符串转unicode
     *
     * @param str String
     * @return Unicode
     */
    @NotNull
    public static String stringToUnicode(@NotNull String str) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] c = str.toCharArray();
        for (char value : c) {
            stringBuilder.append("\\u").append(Integer.toHexString(value));
        }
        return stringBuilder.toString();
    }

    /**
     * Unicode转String
     *
     * @param unicode Unicode
     * @return String
     */
    @NotNull
    public static String unicodeToString(@NotNull String unicode) {
        StringBuilder stringBuilder = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int index = Integer.parseInt(hex[i], 16);
            stringBuilder.append((char) index);
        }
        return stringBuilder.toString();
    }
}