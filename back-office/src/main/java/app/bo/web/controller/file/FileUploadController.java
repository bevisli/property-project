package app.bo.web.controller.file;


import core.framework.web.Controller;
import core.framework.web.MultipartFile;
import core.framework.web.Request;
import core.framework.web.Response;
import core.framework.web.exception.BadRequestException;

/**
 * @author mort
 */
public class FileUploadController implements Controller {
    public String modulePath;
    public FileService fileService;

    @Override
    public Response execute(Request request) throws Exception {
        if (request.queryParams().get("modulePath") != null) {
            modulePath = request.queryParams().get("modulePath");
        }
        MultipartFile file = request.file("file").orElseThrow(() -> new BadRequestException("file not found"));
        FilePutResponse response = fileService.put(modulePath, file.fileName, file.path.toFile());
        return Response.bean(response);
    }
}
