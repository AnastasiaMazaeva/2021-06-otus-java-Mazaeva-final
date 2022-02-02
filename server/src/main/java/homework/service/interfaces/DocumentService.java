package homework.service.interfaces;

import homework.domain.model.Document;

import java.io.InputStream;

public interface DocumentService {

    InputStream download(Long documentId);

    Document findById(Long documentId);

    void delete(Long documentId);

    void save(Document document, InputStream inputStream);
}
