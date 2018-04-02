package middleware.payment.util;

import core.framework.util.Encodings;

/**
 * @author mort
 */
public class Encoders {
    // encode as uri component, except slash, to make url and file path pretty
    public static String encodeKey(String key) {
        StringBuilder builder = new StringBuilder();
        int currentIndex = 0;
        while (true) {
            int indexOfSlash = key.indexOf('/', currentIndex);
            if (indexOfSlash == -1) {
                builder.append(Encodings.uriComponent(key.substring(currentIndex)));
                return builder.toString();
            } else {
                builder.append(Encodings.uriComponent(key.substring(currentIndex, indexOfSlash)));
                builder.append('/');
                currentIndex = indexOfSlash + 1;
            }
        }
    }
}
