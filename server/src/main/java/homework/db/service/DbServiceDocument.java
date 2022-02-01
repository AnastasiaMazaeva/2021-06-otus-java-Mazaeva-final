package homework.db.service;

import homework.domain.model.Document;

public interface DbServiceDocument {

    Document findByPermission(Long permissionId);

    void save(Document document);
}
