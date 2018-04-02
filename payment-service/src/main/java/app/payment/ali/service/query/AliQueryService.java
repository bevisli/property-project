package app.payment.ali.service.query;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import core.framework.impl.log.marker.ErrorCodeMarker;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;
import app.payment.ali.service.api.QueryContent;
import app.payment.ali.exception.ErrorCodes;
import app.payment.ali.service.AliErrorService;
import app.payment.api.ali.query.AliQueryPaymentResponse;
import app.payment.util.Converters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mort
 */
public class AliQueryService {
    private final Logger logger = LoggerFactory.getLogger(AliQueryService.class);
    @Inject
    AlipayClient alipayClient;
    @Inject
    AliErrorService errorService;

    public AliQueryPaymentResponse queryWithTransactionId(String tradeNo) {
        return query(tradeNo, null);
    }

    public AliQueryPaymentResponse queryWithPaymentId(String paymentId) {
        return query(null, paymentId);
    }

    private AliQueryPaymentResponse query(String tradeNo, String outTradeNo) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        QueryContent content = new QueryContent();
        content.outTradeNo = outTradeNo;
        content.tradeNo = tradeNo;
        request.setBizContent(JSON.toJSON(content));

        AliQueryPaymentResponse response = new AliQueryPaymentResponse();
        try {
            AlipayTradeQueryResponse queryResponse = alipayClient.execute(request);
            if (queryResponse.isSuccess()) {
                response = response(queryResponse);
            } else {
                String message = Strings.format("call ali query order failed, code={}, subcode={}, message={}, submessage={}",
                    queryResponse.getCode(), queryResponse.getSubCode(), queryResponse.getMsg(), queryResponse.getSubMsg());
                logger.error(new ErrorCodeMarker(ErrorCodes.ALI_QUERY_ORDER_ERROR), message);
                response.error = errorService.resultError(queryResponse, message);
            }
        } catch (AlipayApiException e) {
            logger.error(new ErrorCodeMarker(ErrorCodes.ALI_QUERY_ORDER_ERROR), "call ali pay failed", e);
            response.error = errorService.apiException(e, ErrorCodes.ALI_QUERY_ORDER_ERROR);
        }
        return response;
    }

    private AliQueryPaymentResponse response(AlipayTradeQueryResponse aliResponse) {
        AliQueryPaymentResponse response = new AliQueryPaymentResponse();
        response.tradeNo = aliResponse.getTradeNo();
        response.outTradeNo = aliResponse.getOutTradeNo();
        response.buyerId = aliResponse.getBuyerUserId();
        response.buyerLogonId = aliResponse.getBuyerLogonId();
        response.storeId = aliResponse.getStoreId();
        response.storeName = aliResponse.getStoreName();
        response.tradeStatus = aliResponse.getTradeStatus();
        response.totalAmount = Converters.toDouble(aliResponse.getTotalAmount());
        response.buyerPayAmount = Converters.toDouble(aliResponse.getBuyerPayAmount());
        response.buyerUserId = aliResponse.getBuyerUserId();
        response.buyerUserType = aliResponse.getBuyerUserType();
        response.discountAmount = aliResponse.getDiscountAmount();
        response.sendPayDate = Converters.toLocalDateTime(aliResponse.getSendPayDate());
        response.openId = aliResponse.getOpenId();
        return response;
    }
}
