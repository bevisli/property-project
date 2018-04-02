package app.payment.ali.service.query;

import app.payment.ali.exception.AliPayException;
import app.payment.ali.exception.ErrorCodes;
import app.payment.ali.service.api.QueryContent;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import core.framework.inject.Inject;
import core.framework.json.JSON;
import core.framework.util.Strings;

/**
 * @author mort
 */
public class PayQueryService {
    @Inject
    AlipayClient alipayClient;

    public AlipayTradeQueryResponse query(String outTradeNo) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        QueryContent content = new QueryContent();
        content.outTradeNo = outTradeNo;
        request.setBizContent(JSON.toJSON(content));
        try {
            AlipayTradeQueryResponse queryResponse = alipayClient.execute(request);
            if (!queryResponse.isSuccess()) {
                String message = Strings.format("call ali query order failed, code={}, subcode={}, message={}, submessage={}",
                    queryResponse.getCode(), queryResponse.getSubCode(), queryResponse.getMsg(), queryResponse.getSubMsg());
                throw new AliPayException(message, ErrorCodes.ALI_QUERY_ORDER_ERROR);
            }
            return queryResponse;
        } catch (AlipayApiException e) {
            throw new AliPayException("call ali pay failed", ErrorCodes.ALI_QUERY_ORDER_ERROR, e);
        }
    }
}
