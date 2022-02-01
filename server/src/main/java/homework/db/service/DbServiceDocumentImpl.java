package homework.db.service;

import homework.db.sessionmanager.TransactionManager;
import homework.domain.model.Document;
import homework.domain.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DbServiceDocumentImpl implements DbServiceDocument {

    private final TransactionManager transactionManager;
    private final DocumentRepository documentRepository;

    @Override
    public Document findByPermission(Long permissionId) {
        return documentRepository.findByPermissionId(permissionId);
    }

    @Override
    public void save(Document document) {
        transactionManager.callInTransaction(() -> documentRepository.save(document));
    }
}
