package homework.service.interfaces;

import java.io.InputStream;
import java.util.UUID;

public interface Storage {

    void upload(UUID id, InputStream inputStream);
    InputStream download(UUID id);
}
