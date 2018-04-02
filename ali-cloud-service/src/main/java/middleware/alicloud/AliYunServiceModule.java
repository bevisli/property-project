package middleware.alicloud;

import core.framework.module.Module;
import middleware.alicloud.oss.OSSService;
import middleware.alicloud.sms.SMSService;

/**
 * @author mort
 */
public class AliYunServiceModule extends Module {
    @Override
    protected void initialize() {
        String accessId = requiredProperty("ali.cloud.accessKey");
        String secretKey = requiredProperty("ali.cloud.accessSecret");
        String endpoint = requiredProperty("ali.cloud.ossEndpoint");
        bind(OSSService.class, new OSSService(endpoint, accessId, secretKey));
        bind(SMSService.class, new SMSService(accessId, secretKey));
    }
}
