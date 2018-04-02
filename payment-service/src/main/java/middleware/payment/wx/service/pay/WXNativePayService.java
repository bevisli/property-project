package middleware.payment.wx.service.pay;

import com.github.wxpay.sdk.WXPay;
import core.framework.impl.log.marker.ErrorCodeMarker;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import middleware.alicloud.oss.OSSService;
import middleware.payment.WXPayModule;
import middleware.payment.util.QRCodes;
import middleware.payment.wx.WXPayConfigImpl;
import middleware.payment.wx.exception.ErrorCodes;
import middleware.payment.wx.service.api.UnifiedOrderRequest;
import middleware.payment.wx.service.api.UnifiedOrderResponse;
import middleware.payment.api.wx.pay.WXNativePayResponse;
import middleware.payment.api.wx.pay.WXNativePayRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author mort
 */
public class WXNativePayService {
    private final Logger logger = LoggerFactory.getLogger(WXNativePayService.class);
    @Inject
    WXPayConfigImpl config;
    @Inject
    OSSService ossService;
    @Inject
    WXPay wxPay;

    public WXNativePayResponse pay(WXNativePayRequest request) {
        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.ourTradeNo = request.outTradeNo;
        unifiedOrderRequest.body = request.productName;
        unifiedOrderRequest.totalFee = BigDecimal.valueOf(request.totalAmount).multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
        unifiedOrderRequest.clientIP = request.clientIP;
        unifiedOrderRequest.attach = request.attachParam;
        unifiedOrderRequest.notifyURL = Strings.format("{}{}", config.serviceHost, WXPayModule.WX_PAY_NOTIFY_URL_PATH);

        WXNativePayResponse payResponse = new WXNativePayResponse();
        try {
            Map<String, String> resultMap = wxPay.unifiedOrder(unifiedOrderRequest.toMap());
            UnifiedOrderResponse response = UnifiedOrderResponse.fromMap(resultMap);
            payResponse = payResponse(request.outTradeNo, response);
        } catch (Exception e) {
            payResponse.error = new WXNativePayResponse.Error();
            payResponse.error.code = ErrorCodes.WX_PAY_API_FAILED;
            payResponse.error.message = e.getMessage();
            logger.error(new ErrorCodeMarker(ErrorCodes.WX_PAY_API_FAILED), e.getMessage(), e);
        }
        return payResponse;
    }

    private WXNativePayResponse payResponse(String outTradeNo, UnifiedOrderResponse response) {
        WXNativePayResponse payResponse = new WXNativePayResponse();
        if (response.returnOK() && response.resultOK()) {
            payResponse.prepayId = response.prepayId;
            payResponse.codeURL = qrCodeURL(outTradeNo, response.prepayId, response.codeURL);
        } else {
            payResponse.error = new WXNativePayResponse.Error();
            payResponse.error.code = response.returnOK() ? response.errorCode : ErrorCodes.WX_PAY_API_COMMUNICATION_FAILED;
            payResponse.error.message = response.returnOK() ? response.errorCodeDesc : response.returnMessage;
        }
        return payResponse;
    }

    private String qrCodeURL(String outTradeNo, String prepayId, String codeURL) {
        File file = QRCodes.weiPayCode(prepayId, "jpg", codeURL);
        String key = Strings.format("wx-pay/{}/{}.jpg", outTradeNo, prepayId);
        ossService.put(config.ossImageBucket, key, file);
        return ossService.objectURL(config.ossImageBucket, key);
    }
}
