package app.payment.wx.service.pay;

import app.payment.WXPayModule;
import app.payment.api.wx.pay.NativePayRequest;
import app.payment.api.wx.pay.NativePayResponse;
import app.payment.util.QRCodes;
import app.payment.wx.WXPayConfigImpl;
import app.payment.wx.domain.WXPayTransaction;
import app.payment.wx.exception.ErrorCodes;
import app.payment.wx.service.api.UnifiedOrderRequest;
import app.payment.wx.service.api.UnifiedOrderResponse;
import com.github.wxpay.sdk.WXPay;
import core.framework.impl.log.marker.ErrorCodeMarker;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import middleware.alicloud.oss.OSSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * @author mort
 */
public class NativePayService {
    private final Logger logger = LoggerFactory.getLogger(NativePayService.class);
    @Inject
    WXPayConfigImpl config;
    @Inject
    OSSService ossService;
    @Inject
    WXPay wxPay;
    @Inject
    PayTransactionService transactionService;

    public NativePayResponse pay(NativePayRequest request) {
        String transactionId = UUID.randomUUID().toString().replace("-", "");
        WXPayTransaction transaction = transactionService.addWXPayTransaction(transactionId, request);

        UnifiedOrderRequest unifiedOrderRequest = new UnifiedOrderRequest();
        unifiedOrderRequest.ourTradeNo = transactionId;
        unifiedOrderRequest.body = request.productName;
        unifiedOrderRequest.totalFee = BigDecimal.valueOf(request.totalAmount).multiply(BigDecimal.valueOf(100)).setScale(0, BigDecimal.ROUND_DOWN).intValue();
        unifiedOrderRequest.clientIP = request.clientIP;
        unifiedOrderRequest.notifyURL = Strings.format("{}{}", config.serviceHost, WXPayModule.WX_PAY_NOTIFY_URL_PATH);

        NativePayResponse payResponse = new NativePayResponse();
        try {
            Map<String, String> resultMap = wxPay.unifiedOrder(unifiedOrderRequest.toMap());
            UnifiedOrderResponse response = UnifiedOrderResponse.fromMap(resultMap);
            transactionService.requestCompleted(transaction, response.prepayId);
            payResponse = payResponse(transactionId, response);
        } catch (Exception e) {
            payResponse.error = new NativePayResponse.Error();
            payResponse.error.code = ErrorCodes.WX_PAY_API_FAILED;
            payResponse.error.message = e.getMessage();
            transactionService.requestFailed(transaction, ErrorCodes.WX_PAY_API_FAILED);
            logger.error(new ErrorCodeMarker(ErrorCodes.WX_PAY_API_FAILED), e.getMessage(), e);
        }
        return payResponse;
    }

    private NativePayResponse payResponse(String transactionId, UnifiedOrderResponse response) {
        NativePayResponse payResponse = new NativePayResponse();
        if (response.returnOK() && response.resultOK()) {
            payResponse.prepayId = response.prepayId;
            payResponse.codeURL = qrCodeURL(transactionId, response.prepayId, response.codeURL);
        } else {
            payResponse.error = new NativePayResponse.Error();
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
