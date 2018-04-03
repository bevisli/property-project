package app.payment.ali.web;

import core.framework.inject.Inject;
import core.framework.web.Request;
import core.framework.web.Response;
import app.payment.ali.service.pay.PayNotifyService;

import java.util.Map;

/**
 * @author mort
 */
public class PagePayNotifyController {
    @Inject
    PayNotifyService notificationService;

    public Response notify(Request request) {
        Map<String, String> params = request.formParams();
        notificationService.process(params);
        return Response.text("success");
    }
}
