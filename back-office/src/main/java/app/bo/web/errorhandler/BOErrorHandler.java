package app.bo.web.errorhandler;

import core.framework.api.http.HTTPStatus;
import core.framework.api.json.Property;
import core.framework.http.HTTPHeaders;
import core.framework.log.ErrorCode;
import core.framework.web.ErrorHandler;
import core.framework.web.Request;
import core.framework.web.Response;

import java.util.Optional;

/**
 * @author caine, mort
 */
public class BOErrorHandler implements ErrorHandler {
    @Override
    public Optional<Response> handle(Request request, Throwable e) {
        if (isAJAXRequest(request)) {
            Response response;
            if (ErrorCode.class.isAssignableFrom(e.getClass())) {
                response = Response.bean(ajaxErrorResponse(((ErrorCode) e).errorCode(), e.getMessage()));
            } else {
                response = Response.bean(ajaxErrorResponse("UNRECOGNIZED_ERROR", e.getMessage()));
            }
            response.status(HTTPStatus.BAD_REQUEST);
            return Optional.of(response);
        }
        return Optional.of(Response.html("/template/404.html", new ErrorPageModel()).status(HTTPStatus.NOT_FOUND));
    }

    private boolean isAJAXRequest(Request request) {
        String accept = request.header(HTTPHeaders.ACCEPT).orElse(null);
        return accept != null && accept.startsWith("application/json");
    }

    public AJAXErrorResponse ajaxErrorResponse(String errorCode, String errorMessage) {
        AJAXErrorResponse response = new AJAXErrorResponse();
        response.errorCode = errorCode;
        response.errorMessage = errorMessage;
        return response;
    }

    public static class AJAXErrorResponse {
        @Property(name = "error_code")
        public String errorCode;

        @Property(name = "error_message")
        public String errorMessage;
    }

    public static class ErrorPageModel {

    }
}
