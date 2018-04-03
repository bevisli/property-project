package app.payment.wx.service.pay;

import app.payment.api.wx.pay.WXPayNotifyMessage;
import app.payment.wx.WXPayConfigImpl;
import app.payment.wx.domain.WXPayTransaction;
import app.payment.wx.exception.ErrorCodes;
import app.payment.wx.exception.WXPayException;
import app.payment.wx.service.SignatureService;
import app.payment.wx.service.api.APIConstants;
import app.payment.wx.service.api.NotifyResponse;
import app.payment.wx.service.pay.processor.WXPayNotifyProcessor;
import core.framework.impl.log.marker.ErrorCodeMarker;
import core.framework.inject.Inject;
import core.framework.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author mort
 */
public class PayNotifyService {
    private final Logger logger = LoggerFactory.getLogger(PayNotifyService.class);
    public WXPayNotifyProcessor processor;
    @Inject
    SignatureService signatureService;
    @Inject
    PayTransactionService transactionService;
    @Inject
    WXPayConfigImpl config;

    public void process(Map<String, String> params) {
        StringBuilder paramBuilder = new StringBuilder();
        params.forEach((name, value) -> paramBuilder.append("{}={};").append(System.lineSeparator()));
        logger.debug("params:" + paramBuilder.toString());
        if (signatureService.verifySignature(params)) {
            NotifyResponse notification = NotifyResponse.fromMap(params);
            verifyNotifyResponse(notification);
            WXPayTransaction transaction = transactionService.updateWXPayTransaction(notification, "wx-pay-notify");
            processor.process(notifyMessage(transaction));
        } else {
            logger.warn(new ErrorCodeMarker(ErrorCodes.WX_PAY_NOTIFY_VERIFY_SIGNATURE_FAILED), "failed to verified request signature");
        }
    }

    private void verifyNotifyResponse(NotifyResponse notification) {
        if (Strings.isEmpty(notification.outTradeNo)) {
            throw new WXPayException("missing paymentId", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (!config.appId.equals(notification.appId)) {
            throw new WXPayException("appId not match", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (!config.merchantId.equals(notification.merchantId)) {
            throw new WXPayException("appId not match", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (!APIConstants.FEE_TYP_CNY.equals(notification.feeType)) {
            throw new WXPayException("feeType not match", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
        if (notification.totalFee <= 0) {
            throw new WXPayException("missing totalFee", "WX_PAY_NOTIFICATION_PROCESS_FAILED");
        }
    }

    private WXPayNotifyMessage notifyMessage(WXPayTransaction transaction) {
        WXPayNotifyMessage message = new WXPayNotifyMessage();
        message.orderId = transaction.orderId;
        message.paymentId = transaction.paymentId;
        message.totalAmount = transaction.totalAmount;
        message.completed = "COMPLETED".equals(transaction.tradeStatus);
        return message;
    }
}
