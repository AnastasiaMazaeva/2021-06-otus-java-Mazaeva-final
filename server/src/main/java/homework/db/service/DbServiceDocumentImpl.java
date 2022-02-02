package homework.db.service;

import homework.db.sessionmanager.TransactionManager;
import homework.domain.model.Document;
import homework.domain.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DbServiceDocumentImpl implements DbServiceDocument {

    private final TransactionManager transactionManager;
    private final DocumentRepository documentRepository;

    @Override
    public Document save(Document document) {
        return transactionManager.doInTransaction(() -> {
            Document savedDocument = documentRepository.save(document);
            log.debug("saved document : {}", savedDocument);
            return savedDocument;
        });
    }

    @Override
    public Document findById(Long documentId) {
        return documentRepository.findById(documentId).orElseThrow();
    }

    @Override
    public void delete(Long documentId) {
        transactionManager.callInTransaction(() -> documentRepository.deleteById(documentId));
    }
}
