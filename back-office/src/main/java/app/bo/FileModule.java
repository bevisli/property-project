package app.bo;

import app.bo.web.controller.file.FileController;
import app.bo.web.controller.file.FileService;
import app.bo.web.controller.file.FileUploadController;
import core.framework.http.ContentType;
import core.framework.module.Module;
import core.framework.util.Maps;
import middleware.alicloud.oss.OSSService;

import java.util.Map;

/**
 * @author mort
 */
public class FileModule extends Module {
    @Override
    protected void initialize() {
        OSSService ossService = new OSSService(requiredProperty("ali.cloud.ossEndpoint"), requiredProperty("ali.cloud.accessKey"), requiredProperty("ali.cloud.accessSecret"));
        bind(OSSService.class, ossService);
        bind(FileService.class).bucketName = requiredProperty("app.static.bucket");

        route().post("/file/example-uploader", fileUploadControllerSupplier("example"));
        FileUploadController fileUploadController = bind(FileUploadController.class);
        route().post("/file/uploader", fileUploadController);

        FileController fileController = bind(FileController.class);
        fileController.contentTypes(imageContentTypes());
        fileController.contentTypes(audioContentTypes());
        fileController.contentTypes(videoContentTypes());
        route().get("/file/:key(*)", fileController);
    }

    private FileUploadController fileUploadControllerSupplier(String modulePath) {
        FileUploadController controller = new FileUploadController();
        controller.fileService = bean(FileService.class);
        controller.modulePath = modulePath;
        return controller;
    }

    private Map<String, ContentType> imageContentTypes() {
        Map<String, ContentType> types = Maps.newHashMapWithExpectedSize(10);
        types.put("gif", ContentType.create("image/gif", null));
        types.put("jpg", ContentType.create("image/jpeg", null));
        types.put("jpeg", ContentType.create("image/jpeg", null));
        types.put("png", ContentType.create("image/png", null));
        types.put("tiff", ContentType.create("image/tiff", null));
        types.put("tif", ContentType.create("image/tiff", null));
        return types;
    }

    private Map<String, ContentType> audioContentTypes() {
        Map<String, ContentType> types = Maps.newHashMapWithExpectedSize(10);
        types.put("mp3", ContentType.create("audio/mp3", null));
        return types;
    }

    private Map<String, ContentType> videoContentTypes() {
        Map<String, ContentType> types = Maps.newHashMapWithExpectedSize(10);
        types.put("mp4", ContentType.create("video/mp4", null));
        return types;
    }
}
