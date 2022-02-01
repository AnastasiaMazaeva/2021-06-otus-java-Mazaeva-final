package homework.service.impl;

import homework.db.service.DbServicePermission;
import homework.domain.model.Document;
import homework.domain.model.User;
import homework.domain.projection.PermissionRepresentation;
import homework.service.interfaces.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final DbServicePermission service;

    @Override
    public void permit(Long documentId, Long receiverId) {
        service.save(documentId, receiverId);
    }

    @Override
    public void retrievePermission(Document document, User receiver) {

    }

    @Override
    public void retrieveAllPermissions(User owner, User receiver) {

    }

    @Override
    public List<PermissionRepresentation> findByUser(Long userId) {
        return service.findByUser(userId);
    }

}
