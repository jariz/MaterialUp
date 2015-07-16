package io.jari.materialup.utils;

/**
 * Created by rsicarelli on 7/15/15.
 */
public class StringUtils {

    /**
     * Retrieves <code>true</code> in case the given text is empty (null or blank) or equals to "null" or "NULL".
     */
    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0 || text.trim().length() == 0 || "null".equalsIgnoreCase(text);
    }

}
