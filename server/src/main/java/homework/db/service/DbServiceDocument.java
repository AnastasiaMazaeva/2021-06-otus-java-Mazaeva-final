package homework.db.service;

import homework.domain.model.Document;

public interface DbServiceDocument {

    Document save(Document document);

    Document findById(Long documentId);

    void delete(Long documentId);
}
