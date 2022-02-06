package storage.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import storage.properties.StorageProperties;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(StorageProperties.class)
public class StorageConfiguration {

    private final StorageProperties properties;

    @Bean
    public MinioClient getClient() {
        log.info("Создание подключения к хранилищу, url {}", properties.getUrl());
        HttpUrl httpUrl = HttpUrl.parse(properties.getUrl());
        assert httpUrl != null;
        return MinioClient.builder()
                .endpoint(httpUrl)
                .credentials(properties.getKey(), properties.getSecretkey())
                .build();
    }
}