package middleware.alicloud.oss;

/**
 * @author mort
 */
public enum ImageStyle {
    THUMB("70x70"),
    SMALL("180x180"),
    MEDIUM("216x216"),
    LARGE("380x380"),
    HD("800x800");

    public final String size;

    ImageStyle(String size) {
        this.size = size;
    }
}
