package storage.service;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import storage.exception.StorageException;
import storage.properties.StorageProperties;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class StorageImpl implements Storage {

    private final StorageProperties properties;
    private final MinioClient client;

    /**
     * Сохранение в minio
     * @param id - uuid - имя объекта
     * @param inputStream - объект
     */
    @Override
    public void upload(Long id, InputStream inputStream) {
        try (inputStream) {
            client.putObject(PutObjectArgs.builder()
                    .bucket(properties.getBucketname())
                    .object(id.toString())
                    .stream(inputStream, inputStream.available(), -1)
                    .build());
        } catch (Exception e) {
            throw new StorageException("Ошибка сохранения объекта с id " + id, e);
        }
    }

    /**
     * Удаление объекта
     * @param id - имя (uuid)
     */
    @Override
    public void delete(Long id) {
        try {
            client.removeObject(RemoveObjectArgs.builder()
                    .bucket(properties.getBucketname())
                    .object(id.toString())
                    .build());
        } catch (Exception e) {
            throw new StorageException("Ошибка удаления объекта с id " + id, e);
        }
    }

    /**
     * Получение объекта
     * @param name - имя
     * @return - объект
     */
    @Override
    public InputStream download(Long name) {
        try {
            return client.getObject(GetObjectArgs.builder()
                    .bucket(properties.getBucketname())
                    .object(name.toString())
                    .build());
        } catch (Exception e) {
            throw new StorageException("Ошибка получения объекта с id " + name, e);
        }
    }

}
