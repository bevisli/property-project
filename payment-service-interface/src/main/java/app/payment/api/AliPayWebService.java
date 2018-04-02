package app.payment.api;

import app.payment.api.ali.pay.PagePayExecuteResponse;
import app.payment.api.ali.pay.PagePayRedirectResponse;
import app.payment.api.ali.pay.PagePayRequest;
import app.payment.api.ali.query.PayTransactionResponse;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;

/**
 * @author mort
 */
public interface AliPayWebService {
    @POST
    @Path("/ali-page-pay/execute")
    PagePayExecuteResponse executePayment(PagePayRequest request);

    @POST
    @Path("/ali-page-pay/redirect")
    PagePayRedirectResponse redirectPayment(PagePayRequest request);

    @GET
    @Path("/ali-pay/:transactionId")
    PayTransactionResponse query(@PathParam("transactionId") String transactionId);
}
