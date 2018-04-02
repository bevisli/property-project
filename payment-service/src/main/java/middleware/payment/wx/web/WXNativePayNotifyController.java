package middleware.payment.wx.web;

import com.github.wxpay.sdk.WXPayUtil;
import core.framework.http.ContentType;
import core.framework.impl.log.marker.ErrorCodeMarker;
import core.framework.inject.Inject;
import core.framework.util.Charsets;
import core.framework.util.Maps;
import core.framework.web.Request;
import core.framework.web.Response;
import middleware.payment.wx.exception.ErrorCodes;
import middleware.payment.wx.exception.WXPayException;
import middleware.payment.wx.service.pay.WXPayNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author mort
 */
public class WXNativePayNotifyController {
    private final Logger logger = LoggerFactory.getLogger(WXNativePayNotifyController.class);
    @Inject
    WXPayNotificationService notificationService;

    public Response notify(Request request) {
        if (!request.body().isPresent()) {
            logger.error(new ErrorCodeMarker("WX_PAY_NOTIFY_EMPTY_REQUEST"), "request body is empty");
            String responseString = errorResponse();
            return Response.text(responseString).contentType(ContentType.TEXT_XML);
        }
        String xmlString = new String(request.body().get(), Charsets.UTF_8);
        Map<String, String> params = paramMap(xmlString);
        notificationService.process(params);
        String responseString = successResponse();
        return Response.text(responseString).contentType(ContentType.TEXT_XML);
    }

    private String successResponse() {
        Map<String, String> response = Maps.newHashMap();
        response.put("return_code", "SUCCESS");
        response.put("return_msg", "OK");
        try {
            return WXPayUtil.mapToXml(response);
        } catch (Exception e) {
            throw new WXPayException("map to xml failed", ErrorCodes.WX_PAY_MAP_TO_XML_FAILED, e);
        }
    }

    private String errorResponse() {
        Map<String, String> response = Maps.newHashMap();
        response.put("return_code", "FAIL");
        response.put("return_msg", "参数为空");
        try {
            return WXPayUtil.mapToXml(response);
        } catch (Exception e) {
            throw new WXPayException("map to xml failed", ErrorCodes.WX_PAY_MAP_TO_XML_FAILED, e);
        }
    }

    private Map<String, String> paramMap(String xmlString) {
        try {
            return WXPayUtil.xmlToMap(xmlString);
        } catch (Exception e) {
            throw new WXPayException("xml to map failed", ErrorCodes.WX_PAY_XML_TO_MAP_FAILED, e);
        }
    }
}
