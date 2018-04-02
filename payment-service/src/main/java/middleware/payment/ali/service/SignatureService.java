package middleware.payment.ali.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayConstants;
import com.alipay.api.internal.util.AlipaySignature;
import core.framework.inject.Inject;
import middleware.payment.ali.AliPayConfig;
import middleware.payment.ali.exception.AliPayException;

import java.util.Map;

/**
 * @author mort
 */
public class SignatureService {
    @Inject
    AliPayConfig config;

    public boolean verifySignature(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(params, config.publicKey, AlipayConstants.CHARSET_UTF8, AlipayConstants.SIGN_TYPE_RSA2);
        } catch (AlipayApiException e) {
            throw new AliPayException("verify signature failed", "ALI_VERIFY_SIGNATURE_FAILED", e);
        }
    }
}
