package homework.service.interfaces;

import homework.domain.model.Document;

public interface DocumentService {

    Document findByPermission(Long permissionId);

    void save(Document document);
}
