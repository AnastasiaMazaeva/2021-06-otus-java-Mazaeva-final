package homework.db.service;

import homework.domain.model.Document;

public interface DbServiceDocument {

    Document findById(Long documentId);

    void delete(Long documentId);

    Document save(Document document);
}
