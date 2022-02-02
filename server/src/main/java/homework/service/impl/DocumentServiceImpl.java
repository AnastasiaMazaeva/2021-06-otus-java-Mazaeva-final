package homework.service.impl;

import homework.db.service.DbServiceDocument;
import homework.domain.model.Document;
import homework.service.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import storage.exception.StorageException;
import storage.service.Storage;

import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DbServiceDocument service;
    private final Storage storage;

    @Override
    @Transactional(rollbackFor = {StorageException.class})
    public void save(Document document, InputStream inputStream) {
        Document savedDocument = service.save(document);
        storage.upload(savedDocument.getId(), inputStream);
        log.debug("document saved in minio : {}", savedDocument.getId());
    }

    @Override
    public InputStream download(Long documentId) {
        return storage.download(documentId);
    }

    @Override
    public Document findById(Long documentId) {
        return service.findById(documentId);
    }

    @Override
    @Transactional(rollbackFor = StorageException.class)
    public void delete(Long documentId) {
        service.delete(documentId);
        storage.delete(documentId);
    }

}
