package app.bo.web.controller.file;

import core.framework.inject.Inject;
import core.framework.util.Strings;
import middleware.alicloud.oss.OSSService;

import java.io.File;
import java.util.UUID;

/**
 * @author mort
 */
public class FileService {
    public String bucketName;
    @Inject
    OSSService ossService;

    public FilePutResponse put(String modulePath, String fileName, File file) {
        String key = key(modulePath, fileName);
        ossService.put(bucketName, key, file);
        FilePutResponse filePutResponse = new FilePutResponse();
        filePutResponse.path = key;
        filePutResponse.previewURL = fileURL(key);
        return filePutResponse;
    }

    String key(String modulePath, String fileName) {
        return modulePath + "/" + UUID.randomUUID().toString() + "." + extension(fileName);
    }

    String extension(String path) {
        int p = path.lastIndexOf('.');
        if (p < path.length() - 1 && p > 0) {
            return path.substring(p + 1, path.length());
        }
        return "";
    }

    public File get(String key) {
        return ossService.get(bucketName, key);
    }

    public String fileURL(String key) {
        return Strings.format("/file/{}", key);
    }

    public void delete(String key) {
        ossService.delete(bucketName, key);
    }
}
