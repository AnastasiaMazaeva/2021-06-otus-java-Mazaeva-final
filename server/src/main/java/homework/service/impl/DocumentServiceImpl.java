package homework.service.impl;

import homework.db.service.DbServiceDocument;
import homework.domain.model.Document;
import homework.service.interfaces.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DbServiceDocument service;

    @Override
    public Document findByPermission(Long permissionId) {
        return service.findByPermission(permissionId);
    }

    @Override
    public void save(Document document) {
        service.save(document);
    }

}
