package middleware.alicloud.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import core.framework.util.Encodings;
import core.framework.util.Files;
import middleware.alicloud.error.ErrorCodes;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;

/**
 * @author mort
 */
public class OSSService {
    private final String endpoint;
    private final String accessKey;
    private final String accessSecret;
    private final Path localFilePath = Files.tempDir();

    public OSSService(String endpoint, String accessKey, String accessSecret) {
        this.endpoint = endpoint;
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    public String copy(String sourceBucketName, String sourceKey, String destinationBucketName, String destinationKey) {
        OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);
        ossClient.copyObject(sourceBucketName, sourceKey, destinationBucketName, destinationKey);
        ossClient.setObjectAcl(destinationBucketName, destinationKey, CannedAccessControlList.PublicRead);
        return sourceKey;
    }

    public void put(String bucketName, String key, File file) {
        OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);
        ossClient.putObject(bucketName, key, file);
        ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        ossClient.shutdown();
    }

    public void put(String bucketName, String key, InputStream inputStream) {
        OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);
        ossClient.putObject(bucketName, key, inputStream);
        ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
        ossClient.shutdown();
    }

    public String objectURL(String bucketName, String key) {
        return "http://" + bucketName + ".oss-cn-shenzhen.aliyuncs.com/" + key;
    }

    public File get(String bucketName, String key) {
        Path filePath = localFilePath.resolve(encodeKey(key));

        if (!java.nio.file.Files.exists(filePath)) {
            Path originalFilePath = Files.tempFile();

            OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);
            OSSObject ossObject = ossClient.getObject(bucketName, key);
            try (InputStream stream = ossObject.getObjectContent()) {
                java.nio.file.Files.copy(stream, originalFilePath);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } catch (OSSException e) {
                throw new AliOSSException(e.getErrorMessage(), ErrorCodes.ALI_OSS_SERVICE_GET_OBJECT_FAILED, e);
            }

            // move file to target path and clean up
            Files.createDir(filePath.getParent());
            Files.moveFile(originalFilePath, filePath);
        }

        return filePath.toFile();
    }

    // encode as uri component, except slash, to make url and file path pretty
    // this is due to legacy data in db contains non-url-friendly chars, once all data is updated, we will be able to remove this rule since vendor-service use clean key from now on
    String encodeKey(String key) {
        StringBuilder builder = new StringBuilder();
        int currentIndex = 0;
        while (true) {
            int indexOfSlash = key.indexOf('/', currentIndex);
            if (indexOfSlash == -1) {
                builder.append(Encodings.uriComponent(key.substring(currentIndex)));
                return builder.toString();
            } else {
                builder.append(Encodings.uriComponent(key.substring(currentIndex, indexOfSlash)));
                builder.append('/');
                currentIndex = indexOfSlash + 1;
            }
        }
    }

    public void delete(String bucketName, String key) {
        OSSClient ossClient = new OSSClient(endpoint, accessKey, accessSecret);
        ossClient.deleteObject(bucketName, key);
        ossClient.shutdown();
    }
}
