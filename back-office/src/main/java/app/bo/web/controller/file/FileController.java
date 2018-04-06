package app.bo.web.controller.file;

import core.framework.http.ContentType;
import core.framework.http.HTTPHeaders;
import core.framework.inject.Inject;
import core.framework.util.Maps;
import core.framework.web.Controller;
import core.framework.web.Request;
import core.framework.web.Response;

import java.io.File;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static core.framework.util.Files.lastModified;

/**
 * @author mort
 */
public class FileController implements Controller {
    private final Map<String, ContentType> contentTypes = Maps.newHashMapWithExpectedSize(20);
    @Inject
    FileService fileService;

    @Override
    public Response execute(Request request) throws Exception {
        String key = request.pathParam("key");

        File file = fileService.get(key);
        return Response.file(file.toPath())
            .contentType(this.contentTypes.get(extension(key)))
            .header(HTTPHeaders.LAST_MODIFIED, DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.ofInstant(lastModified(file.toPath()), ZoneId.of("GMT"))))
            .header(HTTPHeaders.CACHE_CONTROL, "max-age=2592000"); // cache for 90 days
    }


    String extension(String path) {
        int p = path.lastIndexOf('.');
        if (p < path.length() - 1 && p > 0) {
            return path.substring(p + 1, path.length());
        }
        return "";
    }

    public void contentTypes(Map<String, ContentType> contentTypes) {
        this.contentTypes.putAll(contentTypes);
    }
}
