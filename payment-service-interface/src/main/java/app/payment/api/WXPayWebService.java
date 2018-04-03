package app.payment.api;

import app.payment.api.wx.pay.NativePayRequest;
import app.payment.api.wx.pay.NativePayResponse;
import app.payment.api.wx.query.PayTransactionResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author mort
 */
public interface WXPayWebService {
    @POST
    @Path("/wx-native-pay")
    NativePayResponse nativePay(NativePayRequest request);

    @GET
    @Path("/wx-pay/:transactionId")
    PayTransactionResponse query(@PathParam("transactionId") String transactionId);
}
