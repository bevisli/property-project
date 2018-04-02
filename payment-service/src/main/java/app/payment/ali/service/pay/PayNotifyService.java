package app.payment.ali.service.pay;

import app.payment.ali.AliPayConfig;
import app.payment.ali.domain.AliPayTransaction;
import app.payment.ali.exception.AliPayException;
import app.payment.ali.exception.ErrorCodes;
import app.payment.ali.service.SignatureService;
import app.payment.ali.service.api.NotifyResponse;
import app.payment.ali.service.api.TradeStatus;
import app.payment.ali.service.pay.processor.AliPayNotifyProcessor;
import app.payment.api.ali.pay.AliPayNotifyMessage;
import app.payment.util.Converters;
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
    public AliPayNotifyProcessor processor;
    @Inject
    AliPayConfig config;
    @Inject
    SignatureService signatureService;
    @Inject
    PayTransactionService transactionService;

    public void process(Map<String, String> params) {
        StringBuilder paramBuilder = new StringBuilder();
        params.forEach((name, value) -> paramBuilder.append("{}={};").append(System.lineSeparator()));
        logger.debug("params:" + paramBuilder.toString());
        if (signatureService.verifySignature(params)) {
            NotifyResponse notifyResponse = notifyResponse(params);
            verifyNotifyResponse(notifyResponse);
            AliPayTransaction transaction = transactionService.updateAliPayTransaction(notifyResponse, "ali-pay-notify");
            processor.process(payNotify(transaction));
        } else {
            logger.warn(new ErrorCodeMarker(ErrorCodes.ALI_PAY_NOTIFY_VERIFY_SIGNATURE_FAILED), "failed to verified request signature");
        }
    }

    private NotifyResponse notifyResponse(Map<String, String> params) {
        NotifyResponse response = new NotifyResponse();
        response.notifyId = params.get("notify_id");
        response.notifyType = params.get("notify_type");
        response.notifyTime = params.get("notify_time");
        response.tradeNo = params.get("trade_no");
        response.appId = params.get("app_id");
        response.outTradeNo = params.get("out_trade_no");
        response.buyerId = params.getOrDefault("buyer_id", null);
        response.sellerId = params.getOrDefault("seller_id", null);
        response.tradeStatus = params.getOrDefault("trade_status", null);
        response.totalAmount = Converters.toDouble(params.getOrDefault("total_amount", null));
        response.subject = params.getOrDefault("subject", null);
        response.body = params.getOrDefault("body", null);
        response.gmtCreate = Converters.toLocalDateTime(params.getOrDefault("gmt_create", null));
        response.gmtPayment = Converters.toLocalDateTime(params.getOrDefault("gmt_payment", null));
        response.gmtClose = Converters.toLocalDateTime(params.getOrDefault("gmt_close", null), Converters.TIME_WITH_NANO_FORMATTER);
        response.passBackParams = params.getOrDefault("passback_params", null);
        response.completed = paymentCompleted(response.tradeStatus);
        return response;
    }

    private boolean paymentCompleted(String tradeStatus) {
        return !TradeStatus.WAIT_BUYER_PAY.name().equals(tradeStatus) && !TradeStatus.TRADE_CLOSED.name().equals(tradeStatus);
    }

    private void verifyNotifyResponse(NotifyResponse notify) {
        if (Strings.isEmpty(notify.outTradeNo)) {
            throw new AliPayException("missing paymentId", ErrorCodes.ALI_PAY_NOTIFY_VERIFY_FAILED);
        }
        if (notify.totalAmount <= 0) {
            throw new AliPayException("missing totalAmount", ErrorCodes.ALI_PAY_NOTIFY_VERIFY_FAILED);
        }
        if (!config.appId.equals(notify.appId)) {
            throw new AliPayException("appId not match", ErrorCodes.ALI_PAY_NOTIFY_VERIFY_FAILED);
        }
        if (!Strings.isEmpty(notify.sellerId) && !Strings.isEmpty(config.sellerId) && !notify.sellerId.equals(config.sellerId)) {
            throw new AliPayException("sellerId not match", ErrorCodes.ALI_PAY_NOTIFY_VERIFY_FAILED);
        }
    }

    private AliPayNotifyMessage payNotify(AliPayTransaction transaction) {
        AliPayNotifyMessage notify = new AliPayNotifyMessage();
        notify.orderId = transaction.orderId;
        notify.paymentId = transaction.paymentId;
        notify.totalAmount = transaction.totalAmount;
        notify.completed = paymentCompleted(transaction.tradeStatus);
        return notify;
    }
}
