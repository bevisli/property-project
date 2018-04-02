package middleware.payment.wx;

import com.github.wxpay.sdk.WXPayConfig;
import com.github.wxpay.sdk.WXPayConstants;
import core.framework.util.Strings;
import core.framework.web.exception.NotFoundException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.readAllBytes;

/**
 * @author mort
 */
public class WXPayConfigImpl implements WXPayConfig {
    public String appId;
    public String appSecretKey;
    public String merchantId;
    public String apiPrivateKey;
    public String serviceHost;
    public Boolean sandbox;
    public WXPayConstants.SignType signType;
    public String certPath;
    public String ossImageBucket;
    private byte[] certBytes;

    @Override
    public String getAppID() {
        return appId;
    }

    @Override
    public String getMchID() {
        return merchantId;
    }

    @Override
    public String getKey() {
        return apiPrivateKey;
    }

    //.p12 cert, only refund/reverse api require this
    @Override
    public InputStream getCertStream() {
        if (Strings.isEmpty(certPath)) {
            throw new NotFoundException("cert path not configured");
        }
        if (certBytes == null) {
            Path path = Paths.get(certPath).toAbsolutePath();
            try {
                certBytes = readAllBytes(path);
            } catch (IOException e) {
                throw new UncheckedIOException("read cert failed", e);
            }
        }
        return new ByteArrayInputStream(certBytes);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 15000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 30000;
    }
}
