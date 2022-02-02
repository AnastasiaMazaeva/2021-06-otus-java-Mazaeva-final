package storage.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Data
@Component
@Primary
@ConfigurationProperties("minio")
public class StorageProperties {
    private String url;
    private String bucketname;
    private String key;
    private String secretkey;
}