package app.payment.wx.service;

import com.github.wxpay.sdk.WXPayUtil;
import core.framework.inject.Inject;
import app.payment.wx.WXPayConfigImpl;
import app.payment.wx.exception.ErrorCodes;
import app.payment.wx.exception.WXPayException;

import java.util.Map;

/**
 * @author mort
 */
public class WXSignatureService {
    @Inject
    WXPayConfigImpl config;

    public Boolean verifySignature(Map<String, String> params) {
        try {
            return WXPayUtil.isSignatureValid(params, config.apiPrivateKey, config.signType);
        } catch (Exception e) {
            throw new WXPayException("verify signature failed", ErrorCodes.WX_VERIFY_SIGNATURE_FAILED, e);
        }
    }
}
