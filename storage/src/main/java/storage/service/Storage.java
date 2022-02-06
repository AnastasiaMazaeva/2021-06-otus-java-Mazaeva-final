package storage.service;

import java.io.InputStream;

public interface Storage {

    void upload(Long id, InputStream inputStream);
    InputStream download(Long id);
    void delete(Long id);
}
