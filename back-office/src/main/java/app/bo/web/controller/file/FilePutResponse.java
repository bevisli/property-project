package app.bo.web.controller.file;

import core.framework.api.json.Property;

/**
 * @author mort
 */
public class FilePutResponse {
    @Property(name = "path")
    public String path;

    @Property(name = "previewURL")
    public String previewURL;
}
