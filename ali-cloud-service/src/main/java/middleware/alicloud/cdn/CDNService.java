package middleware.alicloud.cdn;

import core.framework.util.Encodings;
import core.framework.util.Strings;

/**
 * @author mort
 */
public class CDNService {
    private final String cdnHost;

    public CDNService(String cdnHost) {
        this.cdnHost = cdnHost;
    }

    public String resourceURL(String ossKey) {
        if (Strings.isEmpty(ossKey)) {
            return null;
        }
        return "http://"
            + cdnHost
            + "/" + encodeKey(ossKey);
    }

    // encode as uri component, except slash, to make url and file path pretty
    // this is due to legacy data in db contains non-url-friendly chars, once all data is updated, we will be able to remove this rule since vendor-service use clean key from now on
    String encodeKey(String key) {
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
